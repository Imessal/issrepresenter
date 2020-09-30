package models

case class ShortStock(id: Int,
                      secId: String,
                      shortName: String,
                      name: String,
                      stockType: String,
                      group: String,
                      primaryBoardId: String) extends IssObject {
  override def getSecId: String = this.secId
  override def toString: String = {
    "ID: " + id + " | " +
      "SecID: " + secId + " | " +
      "ShortName: " + shortName + " | " +
      "Name: " + name + " | " +
      "Stock Type: " + stockType + " | " +
      "Group: " + group + " | " +
      "Primary Board ID: " + primaryBoardId
  }
}