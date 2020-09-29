package models

case class Stock(
                  id: String,
                  secId: String,
                  shortName: String,
                  regNumber: String,
                  name: String,
                  isIn: String,
                  isTraded: String,
                  emitentId: String,
                  emitentTitle: String,
                  emitentInn: String,
                  emitentOkpo: String,
                  gosReg: String,
                  stockType: String,
                  group: String,
                  primaryBoardId: String,
                  marketPriceBoardId: String
                ) extends IssObject {
  override def getSecId: String = this.secId
}
