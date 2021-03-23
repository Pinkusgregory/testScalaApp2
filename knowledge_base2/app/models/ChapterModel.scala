package models

import org.squeryl.KeyedEntity
import org.squeryl.annotations.Column
import play.api.libs.json.{JsArray, JsPath, JsValue, Json, OWrites, Reads, Writes}

case class Chapter(@Column("id_chapter") id: String,
                   @Column("short_name") shortName: String,
                   @Column("full_name") fullName: String,
                   @Column("id_upper_chapter") upperChapterId: String) extends KeyedEntity[String]

object Chapter {
  implicit val format = Json.format[Chapter]
  implicit val chaptersRead = Reads.list[Chapter]
}

case class ChapterReader(shortName: String,
                         fullName: String,
                         articles: Option[List[ArticleReader]],
                         children: Option[List[ChapterReader]])

object ChapterReader {
  implicit val format = Json.format[ChapterReader]
  implicit val chaptersRead = Reads.list[ChapterReader]
  implicit val chaptersWrite = Writes.list[ChapterReader]
}

case class ChapterWithoutId(shortName: String,
                            fullName: String)

class ChapterDTO() {

  implicit val ListChapterWriter: OWrites[List[(String, String, String, String)]] = (
    (JsPath \ "chapter").write[JsArray]
      .contramap[List[(String, String, String, String)]](list => JsArray(list.map(g => Json.obj("id" -> g._1,
        "short_name" ->g._2,
        "full_name" ->g._3,
        "chapter_upper_id" ->g._4))))
    )

  implicit val ChaptersWrite : Writes[List[ChapterReader]] =Writes.list[ChapterReader]
  
  implicit val ChaptersRead: Reads[List[ChapterReader]] = Reads.list[ChapterReader]

  def getListChapterJson(list: List[Chapter]): JsValue = {
    Json.toJson(list)
  }

  def getRecursiveListChapterJson(list: List[ChapterReader]): JsValue = {
    Json.toJson(list)
  }

}