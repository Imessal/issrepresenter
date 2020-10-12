package controllers

import java.io.File
import java.nio.file.Paths

import javax.inject._
import play.api.mvc._
import models.StockHolder._
import models.HistoryHolder._
import models.{StocksGetter, TradeHistoryGetter}
import play.api.libs.Files
import services.StockService
import services.HistoryService

import scala.concurrent.ExecutionContext.Implicits.global

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
    } yield Ok(views.html.stock(stock, history))
  }

  def newStock = TODO

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
