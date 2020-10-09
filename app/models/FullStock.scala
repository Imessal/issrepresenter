package models

case class FullStock(
                  id: Int,
                  secId: String,
                  shortName: String,
                  regNumber: Option[String],
                  name: String,
                  isin: Option[String],
                  isTraded: Option[Int],
                  emitentId: Option[Int],
                  emitentTitle: Option[String],
                  emitentInn: Option[String],
                  emitentOkpo: Option[String],
                  gosReg: Option[String],
                  stockType: String,
                  group: String,
                  primaryBoardId: String,
                  marketPriceBoardId: Option[String]
                ) extends AbstractStock {
  override def getSecId: String = this.secId


  override def getInfo: String = {
    "ID: " + id + " | " +
      "SecID: " + secId + " | " +
      "ShortName: " + shortName + " | " +
      "RegNumber: " + regNumber.getOrElse("None") + " | " +
      "Name: " + name + " | " +
      "Isin: " + isin.getOrElse("None") + " | " +
      "IsTraded: " + (isTraded.getOrElse(None) == 1) + " | " +
      "Emitent ID: " + emitentId.getOrElse("None") + " | " +
      "Emitent Title: " + emitentTitle.getOrElse("None") + " | " +
      "Emitent Inn: " + emitentInn.getOrElse("None") + " | " +
      "Emitent Okpo: " + emitentOkpo.getOrElse("None") + " | " +
      "GosReg: " + gosReg.getOrElse("None") + " | " +
      "Stock Type: " + stockType + " | " +
      "Group: " + group + " | " +
      "Primary Board ID: " + primaryBoardId + " | " +
      "Market Price Board ID: " + marketPriceBoardId.getOrElse("None")
  }

}
