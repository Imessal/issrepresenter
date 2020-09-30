package models

import Implicits._
import play.api.libs.json._

class StockHolder() {
  private val stocksGetter = new StocksGetter
  private val tradeHistoryGetter = new TradeHistoryGetter

  private val availableStocks: List[FullStock] = stocksGetter.get("/Users/ck0rp/IdeaProjects/issrepresenter/stonks/securities_1.xml")
  private val stockPrimaryInfoGetter = new StockPrimaryInfoGetter(availableStocks)
  val stocksAsStrings: List[String] = availableStocks.map(s => s.toString)
  val shortStockList: List[String] = stockPrimaryInfoGetter.getPrimaryInfoStockList.map(s => s.toString)
}
