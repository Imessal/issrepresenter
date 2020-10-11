package models
import com.google.inject.Inject
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.{ExecutionContext, Future}

object HistoryHolder {
  private val tradeHistoryGetter = new TradeHistoryGetter

  val fullTradeHistories: List[TradeHistory] =
    tradeHistoryGetter.get("./stonks/history_1.xml") ++
      tradeHistoryGetter.get("./stonks/history_2.xml") ++
      tradeHistoryGetter.get("./stonks/history_3.xml") ++
      tradeHistoryGetter.get("./stonks/history_4.xml")

  def getFullStockInfo(stock: Option[AbstractStock]): List[TradeHistory] = {
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

    def delete(secId: String): Future[Int] = {
      dbConfig.db.run(historiesFromDB.filter(_.secId === secId).delete)
    }

    def get(secId: String): Future[Option[TradeHistory]] = {
      dbConfig.db.run(historiesFromDB.filter(_.secId === secId).result.headOption)
    }

    def all(): Future[Seq[TradeHistory]] = {
      dbConfig.db.run(historiesFromDB.result)
    }

    def addList(histories: List[TradeHistory]): Seq[Future[String]] = {
      histories.map(s => add(s))
    }
  }
}
