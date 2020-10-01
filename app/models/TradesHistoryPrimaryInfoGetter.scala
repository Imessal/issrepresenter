package models

class TradesHistoryPrimaryInfoGetter(fullTradesHistoryList: List[TradeHistory]) {
  def getPrimaryTradesHistoryInfo: List[ShortTradeHistory] = {
    fullTradesHistoryList.map{
      case TradeHistory(boardId, tradeDate, shortName, secId, numTrades, value,
      _, _, _, _, _, _, volume, _, _, _, mp2valTrd, marketPrice3TradesValue, admittedValue, _) =>
        ShortTradeHistory(boardId, tradeDate, shortName, secId, numTrades, value, volume, mp2valTrd, marketPrice3TradesValue, admittedValue)
    }
  }

}
