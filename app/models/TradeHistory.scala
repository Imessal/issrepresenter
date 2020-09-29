package models

case class TradeHistory(
                         boardId: String,
                         tradeDate: String,
                         shortName: String,
                         secId: String,
                         numTrades: Double,
                         value: Double,
                         open: Double,
                         low: Double,
                         high: Double,
                         legalClosePrice: Double,
                         waPrice: Double,
                         close: Double,
                         volume: Double,
                         marketPrice2: Double,
                         marketPrice3: Double,
                         admittedQuote: Double,
                         mp2valTrd: Double,
                         marketPrice3TradesValue: Double,
                         admittedValue: Double,
                         waVal: Double
                       ) extends IssObject {
  override def getSecId: String = this.secId
}
