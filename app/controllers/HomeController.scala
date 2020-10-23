package controllers

import java.io.File
import java.nio.file.{Files => JavaFiles}
import java.nio.file.Paths

import javax.inject._
import play.api.mvc._
import models.StockHolder._
import models.HistoryHolder._
import models.{FullStock, IssObject, SearchRequestSender, StocksGetter, TradeHistory, TradeHistoryGetter}
import play.api.libs.Files
import services.StockService
import services.HistoryService

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class HomeController @Inject()(val controllerComponents: ControllerComponents,
                              ss: StockService, hs: HistoryService) extends BaseController {
  val req = new SearchRequestSender()

  def index(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    Redirect(routes.HomeController.synchronise())
  }

  def synchronise(): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    val ownedStocksSecIds = for {
      stocks <- ss.all()
    } yield stocks.map(s => s.secId)

    val ownedHistories = for {
      ids <- ownedStocksSecIds
      histories <- hs.all()
    } yield histories.filter(th => !ids.contains(th.getSecId)).map(th => th.getSecId)

    val wantedStocks = req.get(ownedHistories)

    wantedStocks map {
      res => {
        ss.addList(res)
        Redirect(routes.HomeController.stocks())
      }
    }
  }

  def stocks: Action[AnyContent] =  Action.async { implicit request: Request[AnyContent] =>
    ss.all() map { stocks =>
      Ok(views.html.index(stocks))
    }
  }

  def clearHistory(secId: String): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    val fHistories = hs.findHistoryBySecId(secId)
    fHistories.map {
      histories => histories.map(_ => hs.deleteHistory(secId))
        Redirect(routes.HomeController.index())
    }
  }

  def deleteStock(id: Int): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    ss.deleteStock(id) map { _ =>
      Redirect(routes.HomeController.index())
    }
  }

  def updateStock(id: Int): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    for {
      stock <- ss.findStock(id)
    } yield Ok(views.html.update(StockForm.form, stock.get))
  }


  def addManyHistories(path: String): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    val allHistoriesList = new TradeHistoryGetter(path).get()
    hs.addList(allHistoriesList)
    new File(path).delete()
    Ok(views.html.success())
  }

  def addManyStocksFromFile(path: String): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    val content = new String(JavaFiles.readAllBytes(Paths.get(path)))
    val stocksToAdd = new StocksGetter(content).get()
    ss.addList(stocksToAdd)
    new File(path).delete()
    Ok(views.html.success())
  }

  def getStockInfo(id: Int): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    val res = for {
      stock <- ss.findStock(id)
      history <- hs.findHistoryByOption(stock)
    } yield (stock, history)

    res.map { tuple =>
      if (tuple._1.isDefined) Ok(views.html.stock(StockForm.form, tuple._1, tuple._2)) else {
        Redirect(routes.HomeController.index())
      }
    }
  }

  def getTableView: Action[AnyContent] = Action.async {
    for {
      stocks <- ss.all()
      histories <- hs.all()
    } yield Ok(views.html.tableView(stocks, histories))
  }

  def sendStock(isNew: Boolean): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    StockForm.form.bindFromRequest.fold(
      errorForm => {
        println(errorForm)
        Future.successful(Ok(views.html.index(Seq.empty[FullStock])))
      },
      data => {
        val newStock = FullStock(data.id, data.secId, data.shortName, data.regNumber, data.name, data.isin,
        data.isTraded, data.emitentId, data.emitentTitle, data.emitentInn, data.emitentOkpo, data.gosReg,
        data.stockType, data.group, data.primaryBoardId, data.marketPriceBoardId)

        if (isNew) {
          ss.addStock(newStock).map { _ =>
            Redirect(routes.HomeController.index())
          }
        } else {
          ss.update(newStock).map { _ =>
            Redirect(routes.HomeController.index())
          }
        }
      }
    )
  }

  def newStock: Action[AnyContent] = Action {
    Ok(views.html.register(StockForm.form))
  }

  def uploadForm: Action[AnyContent] = Action {
    Ok(views.html.uploadform())
  }

  def uploadHistory: Action[AnyContent] = Action {
    Ok(views.html.uploadform())
  }

  def upload(): Action[MultipartFormData[Files.TemporaryFile]] = Action(parse.multipartFormData) { request =>
    request.body
      .file("xml")
      .map { xml =>
        val filename    = Paths.get(xml.filename).getFileName

        xml.ref.copyTo(Paths.get(s"./tmp/$filename"), replace = true)
        val dataType    = getType("./tmp/" + xml.filename)
        val file = s"./tmp/$filename"

        dataType match {
          case "securities" => Redirect(routes.HomeController.addManyStocksFromFile(file))
          case "history" => Redirect(routes.HomeController.addManyHistories(file))
        }
      }
      .getOrElse {
        Redirect(routes.HomeController.upload()).flashing("error" -> "Missing file")
      }
  }

  def getType(path: String): String = {
    val openedXml = scala.xml.XML.loadFile(path)
    openedXml \\ "data" map { node =>
      node \\ "@id"
    }
  }.head.head.text
}
