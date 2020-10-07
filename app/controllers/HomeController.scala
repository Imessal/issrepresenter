package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import models.StockHolder._
import play.api.libs.json.{Json, OFormat}
import services.StockService

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(val controllerComponents: ControllerComponents,
                              ss: StockService) extends BaseController {

  def index(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    Redirect(routes.HomeController.stocks())
  }
  def stocks: Action[AnyContent] =  Action.async  { implicit request: Request[AnyContent] =>
    ss.all() map { stocks =>
      Ok(views.html.index(getData(stocks, stockIsShort = true, historyIsShort = true)))
    }
  }

  def newStock = TODO

  def getHistory = TODO
}
