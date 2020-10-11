package models
import slick.jdbc.MySQLProfile.api._

class StockTableData(tag: Tag) extends Table[FullStock](tag, "stocks") {
  def id = column[Int]("id", O.PrimaryKey)
  def secId = column[String]("secId")
  def shortName = column[String]("shortName")
  def regNumber = column[Option[String]]("regNumber")
  def name = column[String]("name")
  def isin =  column[Option[String]]("isin")
  def isTraded = column[Option[Int]]("isTraded")
  def emitentId =  column[Option[Int]]("emitentId")
  def emitentTitle = column[Option[String]]("emitentTitle")
  def emitentInn = column[Option[String]]("emitentInn")
  def emitentOkpo = column[Option[String]]("emitentOkpo")
  def gosReg = column[Option[String]]("gosReg")
  def stockType = column[String]("stockType")
  def group = column[String]("group")
  def primaryBoardId = column[String]("primaryBoardId")
  def marketPriceBoardId = column[Option[String]]("marketPriceBoardId")

  override def * =
    (id, secId, shortName, regNumber, name, isin, isTraded, emitentId, emitentTitle, emitentInn,
      emitentOkpo, gosReg, stockType, group, primaryBoardId, marketPriceBoardId) <>
      (FullStock.tupled, FullStock.unapply)
}
