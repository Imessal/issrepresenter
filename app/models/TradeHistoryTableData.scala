package models
import slick.jdbc.MySQLProfile.api._

class TradeHistoryTableData(tag: Tag) extends Table[TradeHistory](tag, "histories") {
  def boardId = column[String]("boardId")
  def tradeDate = column[String]("tradeDate")
  def shortName = column[String]("shortName")
  def secId = column[String]("secId")
  def numTrades = column[Double]("numTrades")
  def value =  column[Double]("value")
  def open = column[Option[Double]]("open")
  def low =  column[Option[Double]]("low")
  def high = column[Option[Double]]("high")
  def legalClosePrice = column[Option[Double]]("legalClosePrice")
  def waPrice = column[Option[Double]]("waPrice")
  def close = column[Option[Double]]("close")
  def volume = column[Double]("volume")
  def marketPrice2 = column[Option[Double]]("marketPrice2")
  def marketPrice3 = column[Option[Double]]("marketPrice3")
  def admittedQuote = column[Option[Double]]("admittedQuote")
  def mp2valTrd = column[Double]("mp2valTrd")
  def marketPrice3TradesValue = column[Double]("marketPrice3TradesValue")
  def admittedValue = column[Double]("admittedValue")
  def waVal = column[Option[Double]]("waVal")


  override def * =
    (boardId, tradeDate, shortName, secId, numTrades, value, open, low, high, legalClosePrice,
      waPrice, close, volume, marketPrice2, marketPrice3, admittedQuote, mp2valTrd, marketPrice3TradesValue,
      admittedValue, waVal) <>
      (TradeHistory.tupled, TradeHistory.unapply)
}
