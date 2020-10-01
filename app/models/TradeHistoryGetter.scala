package models

class TradeHistoryGetter extends XmlParser {
  override def get(xmlPath: String): List[TradeHistory] = {
    val xml = scala.xml.XML.loadFile(xmlPath)
    (xml \\ "row").dropRight(1).map{node =>
      TradeHistory(
        (node \\ "@BOARDID").text,
        (node \\ "@TRADEDATE").text,
        (node \\ "@SHORTNAME").text,
        (node \\ "@SECID").text,
        (node \\ "@NUMTRADES").text.toDouble,
        (node \\ "@VALUE").text.toDouble,
        tryGetDouble((node \\ "@OPEN").text),
        tryGetDouble((node \\ "@LOW").text),
        tryGetDouble((node \\ "@HIGH").text),
        tryGetDouble((node \\ "@LEGALCLOSEPRICE").text),
        tryGetDouble((node \\ "@WAPRICE").text),
        tryGetDouble((node \\ "@CLOSE").text),
        (node \\ "@VOLUME").text.toDouble,
        tryGetDouble((node \\ "@MARKETPRICE2").text),
        tryGetDouble((node \\ "@MARKETPRICE3").text),
        tryGetDouble((node \\ "@ADMITTEDQUOTE").text),
        (node \\ "@MP2VALTRD").text.toDouble,
        (node \\ "@MARKETPRICE3TRADESVALUE").text.toDouble,
        (node \\ "@ADMITTEDVALUE").text.toDouble,
        tryGetDouble((node \\ "@WAVAL").text))
    }
  }.toList
}
