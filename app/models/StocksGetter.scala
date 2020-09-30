package models

class StocksGetter extends XmlParser {
  override def get(xmlPath: String): List[FullStock] = {
    val xml = scala.xml.XML.loadFile(xmlPath)
    xml \\ "row" map { node =>
      FullStock(
        (node \\ "@id").text.toInt,
        (node \\ "@secid").text,
        (node \\ "@shortname").text,
        tryGetString((node \\ "@regnumber").text),
        (node \\ "@name").text,
        tryGetString((node \\ "@isin").text),
        tryGetInt((node \\ "@is_traded").text),
        tryGetInt((node \\ "@emitent_id").text),
        tryGetString((node \\ "@emitent_title").text),
        tryGetString((node \\ "@emitent_inn").text),
        tryGetString((node \\ "@emitent_okpo").text),
        tryGetString((node \\ "@gosreg").text),
        (node \\ "@type").text,
        (node \\ "@group").text,
        (node \\ "@primary_boardid").text,
        tryGetString((node \\ "@marketprice_boardid").text)
      )
    }
  }.toList

  def tryGetInt(s: String): Option[Int] = {
    s match {
      case "" => None
      case _ => Some(s.toInt)
    }
  }

  def tryGetString(s: String): Option[String] = {
    s match {
      case "" => None
      case _ => Some(s)
    }
  }
}