package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import models.StockHolder._
import models.HistoryHolder._
import models.TradeHistory
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

  def addMany(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    val stocksToAdd = fullStocks
    val historiesToAdd = fullTradeHistories
    ss.addList(stocksToAdd)
    hs.addList(historiesToAdd)
    Redirect(routes.HomeController.stocks())
  }

  def getStockInfo(id: Int): Action[AnyContent] = Action.async {
    ss.findStock(id) map { stock =>
    Ok(views.html.stock(stock, getFullStockInfo(stock)))
    }
  }

  def newStock = TODO

}
