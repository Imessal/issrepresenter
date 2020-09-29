package models

class StocksGetter extends XmlParser {
  override def get(xmlPath: String): List[Stock] = {
    val xml = scala.xml.XML.loadFile(xmlPath)
    xml map {

    }
  }
}
