package models

class StockPrimaryInfoGetter(fullStockList: List[FullStock]) {
  def getPrimaryInfoStockList: List[ShortStock]= {
    fullStockList.map {
      case FullStock(id, secId, shortName, _, name, _, _, _, _, _, _ ,_ ,stockType, group, primaryBoardId,
      _) => ShortStock(id, secId, shortName, name, stockType, group, primaryBoardId)
    }
  }
}
