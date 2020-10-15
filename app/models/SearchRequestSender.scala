package models
import scalaj.http.Http

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class SearchRequestSender() {
  def get(keywords: Future[Seq[String]]): Future[List[FullStock]] = {
    for {
      secIds <- keywords
    } yield secIds.map(s => parseResponse(sendRequest(s), s)).toList.distinct
  }

  private def sendRequest(keyword: String): String = {
    Http("http://iss.moex.com/iss/securities.xml").param("q", keyword).asString.body
  }

  private def parseResponse(response: String, secId: String) = {
    val res = new StocksGetter(response).get().filter(s => s.getSecId == secId)
//    println(res.head)
    res.head
  }
}
