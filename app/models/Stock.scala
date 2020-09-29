package models

case class Stock(
                  id: Int,
                  secId: String,
                  shortName: String,
                  regNumber: String,
                  name: String,
                  isIn: String,
                  isTraded: Boolean,
                  emitentId: Int,
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
