package models

import Implicits._
import play.api.libs.json._

import scala.annotation.tailrec

class StockHolder() {
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

}
