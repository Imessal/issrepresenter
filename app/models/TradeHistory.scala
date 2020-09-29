package models

case class TradeHistory(
                         boardId: String,
                         tradeDate: String,
                         shortName: String,
                         secId: String,
                         numTrades: String,
                         value: String,
                         open: String,
                         low: String,
                         high: String,
                         legalClosePrice: String,
                         waPrice: String,
                         close: String,
                         volume: String,
                         marketPrice2: String,
                         marketPrice3: String,
                         admittedQuote: String,
                         mp2valTrd: String,
                         marketPrice3TradesValue: String,
                         admittedValue: String,
                         waVal: String
                       ) extends IssObject {
  override def getSecId: String = this.secId
}
