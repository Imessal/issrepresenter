package services
import com.google.inject.Inject
import models.FullStock
import models.StockHolder._

import scala.concurrent.Future

class StockService @Inject() (stocks: Stocks){
  def addStock(stock: FullStock): Future[String] = {
    stocks.add(stock)
  }

  def deleteStock(id: Int): Future[Int] = {
    stocks.delete(id)
  }

  def findStock(id: Int): Future[Option[FullStock]] = {
    stocks.get(id)
  }

  def update(stock:FullStock): Future[Int] = {
    stocks.update(stock)
  }

  def all(): Future[Seq[FullStock]] = {
    stocks.all()
  }

  def addList(stocksToAdd: List[FullStock]): Seq[Future[String]] = {
    stocks.addList(stocksToAdd)
  }
}
