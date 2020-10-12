package models

import scala.annotation.tailrec
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.jdbc.MySQLProfile.api._
import scala.concurrent.{ExecutionContext, Future}
import com.google.inject.Inject
import play.api.data.Form
import play.api.data.Forms._

object StockHolder {
  object StockForm {
    val form: Form[FullStock] = Form(
      mapping(
        "id" -> number,
        "secId" -> text,
        "shortName" -> text,
        "regNumber" -> optional(text),
        "name" -> text,
        "isin" -> optional(text),
        "isTraded" -> optional(number),
        "emitentId" -> optional(number),
        "emitentTitle" -> optional(text),
        "emitentInn" -> optional(text),
        "emitentOkpo" -> optional(text),
        "gosReg" -> optional(text),
        "stockType" -> text,
        "group" -> text,
        "primaryBoardId" -> text,
        "marketPriceBoardId" -> optional(text)

      )(FullStock.apply)(FullStock.unapply)
    )
  }

  class Stocks @Inject() (protected val dbConfigProvider: DatabaseConfigProvider)
                         (implicit executionContext: ExecutionContext) extends HasDatabaseConfigProvider[JdbcProfile] {

    val stocksFromDB = TableQuery[StockTableData]

    def add(stock:FullStock): Future[String] = {
      dbConfig.db.run(stocksFromDB += stock).map(_ => "stock added").recover {
        case ex: Exception => "bad stock"
      }
    }

    def delete(id: Int): Future[Int] = {
      dbConfig.db.run(stocksFromDB.filter(_.id === id).delete)
    }

    def get(id: Int): Future[Option[FullStock]] = {
      dbConfig.db.run(stocksFromDB.filter(_.id === id).result.headOption)
    }

    def all(): Future[Seq[FullStock]] = {
      dbConfig.db.run(stocksFromDB.result)
    }

    def addList(stocks: List[FullStock]): Seq[Future[String]] = {
      stocks.map(s => add(s))
    }
  }
}
