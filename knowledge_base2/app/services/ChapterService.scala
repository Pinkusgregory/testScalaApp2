package services

import models._
import org.squeryl.PrimitiveTypeMode.{__thisDsl, select, transaction, where}
import org.squeryl.customtypes.CustomTypesMode.createConstantNodeOfScalarStringType
import org.squeryl.PrimitiveTypeMode._

trait ChapterService {
  def list(): List[Chapter]
  def findChapter(id:String): Option[Chapter]
  def deleteChapter(id: String):Unit
  def updateChapter(id:String, shortName: String, fullName: String, upperChapterId: String):Unit
  def createChapter(newChapter: Chapter):Unit
  def deleteAllChapters(): Unit
}

class DbChapterService() extends ChapterService {

  override def list(): List[Chapter] = {
    transaction {
      from(AppSchema.chapters)(c => select(c)).toList
    }
  }

  override def findChapter(id: String): Option[Chapter] = {
    transaction {
      AppSchema.chapters.lookup(id)
    }
  }

  override def deleteChapter(id: String): Unit = {
    transaction {
      AppSchema.chapters.lookup(id) match {
        case Some(value) => AppSchema.chapters.delete(id)
        case None =>
      }
    }
  }

  override def deleteAllChapters(): Unit = {
    transaction {

      AppSchema.chapters.deleteWhere(c => 1 === 1)
    }
  }

  override def updateChapter(id: String, shortName: String, fullName: String, upperChapterId: String): Unit = {
    transaction {
      AppSchema.chapters.lookup(id) match {
        case Some(value) => AppSchema.chapters.delete(id)
                            AppSchema.chapters.insert(Chapter(id, shortName, fullName, upperChapterId))
        case None => println("not")
      }
    }
  }

  override def createChapter(newChapter: Chapter): Unit = {
    transaction {
      AppSchema.chapters.insert(newChapter)
    }
  }
}
