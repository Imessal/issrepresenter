package models

case class TradeHistory(
                         boardId: String,
                         tradeDate: String,
                         shortName: String,
                         secId: String,
                         numTrades: Double,
                         value: Double,
                         open: Option[Double],
                         low: Option[Double],
                         high: Option[Double],
                         legalClosePrice: Option[Double],
                         waPrice: Option[Double],
                         close: Option[Double],
                         volume: Double,
                         marketPrice2: Option[Double],
                         marketPrice3: Option[Double],
                         admittedQuote: Option[Double],
                         mp2valTrd: Double,
                         marketPrice3TradesValue: Double,
                         admittedValue: Double,
                         waVal: Option[Double]
                       ) extends AbstractTradeHistory {
  override def getSecId: String = this.secId

  override def getInfo: String = {
    "Board ID: " + boardId + " | " +
      "Trade Date: " + tradeDate + " | " +
      "ShortName: " + shortName + " | " +
      "SecID: " + secId + " | " +
      "NumTrades: " + numTrades + " | " +
      "Value: " + value + " | " +
      "Open: " + open.getOrElse(None) + " | " +
      "Low: " + low.getOrElse(None) + " | " +
      "High: " + high.getOrElse(None) + " | " +
      "Legal Close Price: " + legalClosePrice.getOrElse(None) + " | " +
      "WaPrice: " + waPrice.getOrElse(None) + " | " +
      "Close: " + close.getOrElse(None) + " | " +
      "Volume: " + volume + " | " +
      "Market Price 2: " + marketPrice2.getOrElse(None) + " | " +
      "Market Price 3: " + marketPrice3.getOrElse(None) + " | " +
      "Admitted Quote: " + admittedQuote.getOrElse(None) + " | " +
      "MpToVal Trd: " + mp2valTrd + " | " +
      "Market Price 3 Trades Value: " + mp2valTrd + " | " +
      "Admitted Value: " + admittedValue + " | " +
      "Waval: " + waVal.getOrElse(None)
  }
}
