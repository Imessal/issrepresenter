package models

case class ShortTradeHistory(
                             boardId: String,
                             tradeDate: String,
                             shortName: String,
                             secId: String,
                             numTrades: Double,
                             value: Double,
                             volume: Double,
                             mp2valTrd: Double,
                             marketPrice3TradesValue: Double,
                             admittedValue: Double
                           ) extends AbstractTradeHistory {
  override def getSecId: String = this.secId

  override def getInfo: String = {
      "Board ID: " + boardId + " | " +
      "Trade Date: " + tradeDate + " | " +
      "ShortName: " + shortName + " | " +
      "SecID: " + secId + " | " +
      "NumTrades: " + numTrades + " | " +
      "Volume: " + volume + " | " +
      "Value: " + value + " | " +
      "MpToVal Trd: " + mp2valTrd + " | " +
      "Market Price 3 Trades Value: " + marketPrice3TradesValue + " | " +
      "Admitted Value: " + admittedValue
  }
}