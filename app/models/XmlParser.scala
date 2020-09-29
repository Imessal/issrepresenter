package models

trait XmlParser {
  def get(xml: String): List[IssObject]
}
