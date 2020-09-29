package models

class TradeHistoryGetter extends XmlParser {
  override def get(xmlPath: String): List[TradeHistory] = {
    val xml = scala.xml.XML.loadFile(xmlPath)
    xml \\ "row" map { node =>
      TradeHistory(
        (node \\ "@BOARDID").text,
        (node \\ "@TRADEDATE").text,
        (node \\ "@SHORTNAME").text,
        (node \\ "@SECID").text,
        (node \\ "@NUMTRADES").text,
        (node \\ "@VALUE").text,
        (node \\ "@OPEN").text,
        (node \\ "@LOW").text,
        (node \\ "@HIGH").text,
        (node \\ "@LEGALCLOSEPRICE").text,
        (node \\ "@WAPRICE").text,
        (node \\ "@CLOSE").text,
        (node \\ "@VOLUME").text,
        (node \\ "@MARKETPRICE2").text,
        (node \\ "@MARKETPRICE3").text,
        (node \\ "@ADMITTEDQUOTE").text,
        (node \\ "@MP2VALTRD").text,
        (node \\ "@MARKETPRICE3TRADESVALUE").text,
        (node \\ "@ADMITTEDVALUE").text,
        (node \\ "@WAVAL").text)
    }
  }.toList
}
