package services

import models._
import org.squeryl.PrimitiveTypeMode.{__thisDsl, select, transaction}
import org.squeryl.customtypes.CustomTypesMode.createConstantNodeOfScalarStringType
import org.squeryl.PrimitiveTypeMode._
import org.squeryl._

trait ArticleService {
  def list(): List[Article]
  def findArticle(id:String): Option[Article]
  def deleteArticle(id: String):Unit
  def updateArticle(id:String, shortName: String, fullName: String, text: String, upperChapterId: String):Unit
  def createArticle(newArticle: Article):Unit
  def deleteAllArticles(): Unit
}

class DbArticleService() extends ArticleService {

  override def list(): List[Article] = {
    transaction {
      from(AppSchema.articles)(a => select(a)).toList
    }
  }

  override def findArticle(id: String): Option[Article]= {
    transaction {
      AppSchema.articles.lookup(id)
    }
  }

  override def deleteArticle(id: String): Unit = {
    transaction {
      AppSchema.articles.lookup(id) match {
        case Some(value) => AppSchema.articles.delete(id)
        case None =>
      }
    }
  }

  override def deleteAllArticles(): Unit = {
    transaction {
      AppSchema.articles.deleteWhere(a => 1 === 1)
    }
  }

  override def updateArticle(id: String, shortName: String, fullName: String, text: String, upperChapterId: String): Unit = {
    transaction {
      AppSchema.articles.lookup(id) match {
        case Some(value) => AppSchema.articles.delete(id)
          AppSchema.articles.insert(Article(id, shortName, fullName, text, upperChapterId))
        case None => println("not")
      }
    }
  }

  override def createArticle(newArticle: Article): Unit = {
    transaction {
      AppSchema.articles.insert(newArticle)
    }
  }
}
