package services

import com.google.inject.Inject
import models.{FullStock, TradeHistory}
import models.HistoryHolder.Histories

import scala.concurrent.Future

class HistoryService @Inject() (histories: Histories) {
  def addStock(history: TradeHistory): Future[String] = {
    histories.add(history)
  }

  def deleteHistory(secId: String): Future[Int] = {
    histories.delete(secId)
  }

  def findHistoryBySecId(secId: String): Future[Seq[TradeHistory]] = {
    histories.get(secId)
  }

  def findHistoryByOption(stock: Option[FullStock]): Future[Seq[TradeHistory]] = {
    histories.getFromOption(stock)
  }

  def all(): Future[Seq[TradeHistory]] = {
    histories.all()
  }

  def addList(historiesToAdd: List[TradeHistory]): Seq[Future[String]] = {
    histories.addList(historiesToAdd)
  }
}
