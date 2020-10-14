package controllers

import java.io.File
import java.nio.file.Paths

import javax.inject._
import play.api.mvc._
import models.StockHolder._
import models.HistoryHolder._
import models.{FullStock, StocksGetter, TradeHistory, TradeHistoryGetter}
import play.api.libs.Files
import services.StockService
import services.HistoryService

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class HomeController @Inject()(val controllerComponents: ControllerComponents,
                              ss: StockService, hs: HistoryService) extends BaseController {

  def index(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    Redirect(routes.HomeController.stocks())
  }
  def stocks: Action[AnyContent] =  Action.async  { implicit request: Request[AnyContent] =>
    ss.all() map { stocks =>
      Ok(views.html.index(stocks))
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
    val historiesToAdd = new TradeHistoryGetter(path).get()
    hs.addList(historiesToAdd)
    Ok(views.html.success())
  }

  def addManyStocks(path: String): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    val stocksToAdd = new StocksGetter(path).get()
    ss.addList(stocksToAdd)
    Ok(views.html.success())
  }

  def getStockInfo(id: Int): Action[AnyContent] = Action.async {
    for {
      stock <- ss.findStock(id)
      history <- hs.findHistory(stock.get.secId)
    } yield Ok(views.html.stock(StockForm.form, stock, history))
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
          case "securities" => Redirect(routes.HomeController.addManyStocks(file))
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
