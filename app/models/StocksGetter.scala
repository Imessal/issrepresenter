package models

class StocksGetter extends XmlParser {
  override def get(xmlPath: String): List[Stock] = {
    val xml = scala.xml.XML.loadFile(xmlPath)
    xml \\ "row" map { node =>
      Stock(
        (node \\ "@id").text,
        (node \\ "@secid").text,
        (node \\ "@shortname").text,
        (node \\ "@regnumber").text,
        (node \\ "@name").text,
        (node \\ "@isin").text,
        (node \\ "@is_traded").text,
        (node \\ "@emitent_id").text,
        (node \\ "@emitent_title").text,
        (node \\ "@emitent_inn").text,
        (node \\ "@emitent_okpo").text,
        (node \\ "@gosreg").text,
        (node \\ "@type").text,
        (node \\ "@group").text,
        (node \\ "@primary_boardid").text,
        (node \\ "@marketprice_boardid").text
      )
    }
  }.toList
}
