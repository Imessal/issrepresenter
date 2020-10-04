package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import models.StockHolder
import play.api.libs.json.{Json, OFormat}
/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {
  val holder = new StockHolder

  def index(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    Redirect(routes.HomeController.stocks())
  }
  def stocks: Action[AnyContent] =  Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index(holder.getData(stockIsShort = true, historyIsShort = true)))
  }

  def newStock = TODO

  def getHistory = TODO
}
