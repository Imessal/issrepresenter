package models

trait XmlParser {
  def get(): List[IssObject]

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

  def tryGetDouble(s: String): Option[Double] = {
    s match {
      case "" => None
      case _ => Some(s.toDouble)
    }
  }
}
