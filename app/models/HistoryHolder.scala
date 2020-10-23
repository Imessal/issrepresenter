package models
import com.google.inject.Inject
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.{ExecutionContext, Future}

object HistoryHolder {
  def getFullStockInfo(stock: Option[AbstractStock], fullTradeHistories: List[TradeHistory]): List[TradeHistory] = {
    stock match {
      case Some(s) => fullTradeHistories.filter (h => h.getSecId == s.getSecId)
      case None => List[TradeHistory]()
    }
  }

  class Histories @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
                           (implicit executionContext: ExecutionContext) extends HasDatabaseConfigProvider[JdbcProfile] {

    val historiesFromDB = TableQuery[TradeHistoryTableData]

    def add(history: TradeHistory): Future[String] = {
      dbConfig.db.run(historiesFromDB += history).map(_ => "history added").recover {
        case ex: Exception => "bad history"
      }
    }

    def getFromOption(stock: Option[FullStock]): Future[Seq[TradeHistory]] = {
      if(stock.isDefined) {
        get(stock.get.getSecId)
      } else all()
    }

    def delete(secId: String): Future[Int] = {
      dbConfig.db.run(historiesFromDB.filter(_.secId === secId).delete)
    }

    def get(secId: String): Future[Seq[TradeHistory]] = {
      dbConfig.db.run(historiesFromDB.filter(_.secId === secId).result)
    }

    def all(): Future[Seq[TradeHistory]] = {
      dbConfig.db.run(historiesFromDB.result)
    }

    def addList(histories: List[TradeHistory]): Seq[Future[String]] = {
      histories.map(s => add(s))
    }
  }
}
