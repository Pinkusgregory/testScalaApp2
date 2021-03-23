package models

import org.squeryl.Schema

object AppSchema extends Schema {

  implicit val chapters = table[Chapter]("chapter")
  implicit val articles = table[Article]("article")

}