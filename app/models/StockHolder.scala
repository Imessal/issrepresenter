package models
import models.{StocksGetter, TradeHistoryGetter}

class StockHolder(stocks: List[Stock], histories: List[TradeHistory]) {
  val stocksGetter = new StocksGetter
  val tradeHistoryGetter = new TradeHistoryGetter
}
