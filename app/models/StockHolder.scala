package models

import scala.annotation.tailrec
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.jdbc.MySQLProfile.api._
import scala.concurrent.{ExecutionContext, Future}
import com.google.inject.Inject
import play.api.data.Form
import play.api.data.Forms._

object StockHolder {
  private val stocksGetter = new StocksGetter
  private val tradeHistoryGetter = new TradeHistoryGetter

  private val fullStocks: List[FullStock] = stocksGetter.get("/Users/ck0rp/IdeaProjects/issrepresenter/stonks/securities_1.xml")
  private val fullTradeHistories: List[TradeHistory] =
    tradeHistoryGetter.get("/Users/ck0rp/IdeaProjects/issrepresenter/stonks/history_1.xml") ++
      tradeHistoryGetter.get("/Users/ck0rp/IdeaProjects/issrepresenter/stonks/history_2.xml") ++
      tradeHistoryGetter.get("/Users/ck0rp/IdeaProjects/issrepresenter/stonks/history_3.xml") ++
      tradeHistoryGetter.get("/Users/ck0rp/IdeaProjects/issrepresenter/stonks/history_4.xml")
  private val stockPrimaryInfoGetter = new StockPrimaryInfoGetter(fullStocks)
  private val tradesHistoryPrimaryInfoGetter = new TradesHistoryPrimaryInfoGetter(fullTradeHistories)
  private val shortStockList: List[ShortStock] = stockPrimaryInfoGetter.getPrimaryInfoStockList
  private val shortTradeHistories: List[ShortTradeHistory] = tradesHistoryPrimaryInfoGetter.getPrimaryTradesHistoryInfo

  private def getAvailableStockHistory(stock: AbstractStock, histories: List[AbstractTradeHistory]): List[AbstractTradeHistory] = {
    val id = stock.getSecId
    histories.filter(x => x.getSecId == id)
  }

  def getData(stockIsShort: Boolean, historyIsShort: Boolean): Map[String, List[String]] = {
    val res = Map[String, List[String]]()
    val conditions = (stockIsShort, historyIsShort)

    @tailrec
    def go(stocks: List[AbstractStock], histories: List[AbstractTradeHistory], acc: Map[String, List[String]]): Map[String, List[String]]= {
      if (stocks.isEmpty) acc
      else go(stocks.tail, histories, acc + (stocks.head.getInfo -> getAvailableStockHistory(stocks.head, histories).map(h => h.getInfo)))
    }

    conditions match {
      case (true, true) => go(shortStockList, shortTradeHistories, res)
      case (true, false) => go(shortStockList, fullTradeHistories, res)
      case (false, true) => go(fullStocks, shortTradeHistories, res)
      case _ => go(fullStocks, fullTradeHistories, res)
    }
  }

  class StockTableData(tag: Tag) extends Table[FullStock](tag, "stocks") {
    def id = column[Int]("id", O.PrimaryKey)
    def secId = column[String]("secId")
    def shortName = column[String]("shortName")
    def regNumber = column[Option[String]]("regNumber")
    def name = column[String]("name")
    def isin =  column[Option[String]]("isin")
    def isTraded = column[Option[Int]]("isTraded")
    def emitentId =  column[Option[Int]]("emitentId")
    def emitentTitle = column[Option[String]]("emitentTitle")
    def emitentInn = column[Option[String]]("emitentInn")
    def emitentOkpo = column[Option[String]]("emitentOkpo")
    def gosReg = column[Option[String]]("gosReg")
    def stockType = column[String]("stockType")
    def group = column[String]("group")
    def primaryBoardId = column[String]("primaryBoardId")
    def marketPriceBoardId = column[Option[String]]("marketPriceBoardId")

    override def * =
      (id, secId, shortName, regNumber, name, isin, isTraded, emitentId, emitentTitle, emitentInn,
      emitentOkpo, gosReg, stockType, group, primaryBoardId, marketPriceBoardId) <>
        (FullStock.tupled, FullStock.unapply)
  }

  object StockForm {
    val form: Form[FullStock] = Form(
      mapping(
        "id" -> number,
        "secId" -> text,
        "shortName" -> text,
        "regNumber" -> optional(text),
        "name" -> text,
        "isin" -> optional(text),
        "isTraded" -> optional(number),
        "emitentId" -> optional(number),
        "emitentTitle" -> optional(text),
        "emitentInn" -> optional(text),
        "emitentOkpo" -> optional(text),
        "gosReg" -> optional(text),
        "stockType" -> text,
        "group" -> text,
        "primaryBoardId" -> text,
        "marketPriceBoardId" -> optional(text)

      )(FullStock.apply)(FullStock.unapply)
    )
  }

  class Stocks @Inject() (protected val dbConfigProvider: DatabaseConfigProvider)
                         (implicit executionContext: ExecutionContext) extends HasDatabaseConfigProvider[JdbcProfile] {

    val stocksFromDB = TableQuery[StockTableData]

    def add (stock:FullStock): Future[String] = {
      dbConfig.db.run(stocksFromDB += stock).map(res => "stock added").recover {
        case ex: Exception => "bad stock"
      }
    }

  }
}
