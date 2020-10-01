package models

import Implicits._
import play.api.libs.json._

class StockHolder() {
  private val stocksGetter = new StocksGetter
  private val tradeHistoryGetter = new TradeHistoryGetter

  private val availableStocks: List[FullStock] = stocksGetter.get("/Users/ck0rp/IdeaProjects/issrepresenter/stonks/securities_2.xml")
  private val availableHistories: List[TradeHistory] = tradeHistoryGetter.get("/Users/ck0rp/IdeaProjects/issrepresenter/stonks/history_1.xml")
  private val stockPrimaryInfoGetter = new StockPrimaryInfoGetter(availableStocks)
  private val tradesHistoryPrimaryInfoGetter = new TradesHistoryPrimaryInfoGetter(availableHistories)
  val stocksAsStrings: List[String] = availableStocks.map(s => s.toString)
  val shortStockList: List[String] = stockPrimaryInfoGetter.getPrimaryInfoStockList.map(s => s.getInfo)
  val fullTradeHistories: List[String] = availableHistories.map(s => s.getInfo)
  val tradePrimaryHistories: List[String] = tradesHistoryPrimaryInfoGetter.getPrimaryTradesHistoryInfo.map(s => s.getInfo)

//  def getAvailableStockHistory(stock: AbstractStock): List[TradeHistory] = {
//    val id = stock.getSecId
//  }
}
