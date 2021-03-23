package controllers

import akka.shapeless.HList
import akka.shapeless.HList.ListCompat
import com.typesafe.config.ConfigFactory
import javax.inject.Inject
import play.api.mvc.{AbstractController, ControllerComponents}
import play.api.data.Form
import play.api.data.Forms.{mapping, nonEmptyText}
import play.api.i18n.MessagesApi
import play.api.libs.json.Json
import services._
import models._

import scala.io.Source

class MainController @Inject() (cc: ControllerComponents)(messages: MessagesApi) extends AbstractController(cc) {

  val articleService = new DbArticleService
  val chapterService = new DbChapterService
  val articleDTO = new ArticleDTO
  val chapterDTO = new ChapterDTO

  val articleForm: Form[ArticleWithoutId] = Form {
    mapping(
      "short_name" -> nonEmptyText,
      "full_name" -> nonEmptyText,
      "text" -> nonEmptyText
    )(ArticleWithoutId.apply)(ArticleWithoutId.unapply)
  }

  val chapterForm: Form[ChapterWithoutId] = Form {
    mapping(
      "short_name" -> nonEmptyText,
      "full_name" -> nonEmptyText
    )(ChapterWithoutId.apply)(ChapterWithoutId.unapply)
  }

  def listChapter() = Action { implicit request =>
    Ok(chapterDTO.getListChapterJson(chapterService.list()))
  }

  def listArticle() = Action { implicit request =>
    val name = "Stefan"
    var str=
      """
        [
          {
            "shortName": """
    str += """"""" + name + """""""
    str += """},"""
    str += """]"""
    val r = Json.parse(str.dropRight(2) + "]").toString()
    println(r)
    println(Json.parse(r))

    Ok(articleDTO.getListArticleJson(articleService.list()))
  }

  def list() = Action { implicit request =>

    def recursiveWriter(upperChapterId: String): List[ChapterReader] ={
      val chaptersList: List[ChapterReader] = List[ChapterReader]
      chapterService.list().foreach( chapter => {
        if (chapter.upperChapterId == upperChapterId) {
          val articlesList : Seq[ArticleReader] => List[ArticleReader] = List[ArticleReader]
          articlesList.->()
          articleService.list().foreach(article =>
            if (article.upperChapterId == chapter.id) {
              ArticleReader(article.shortName, article.fullName, article.text) :: articlesList
            }
          )
          ChapterReader(
            chapter.shortName,
            chapter.fullName,
            Option(articlesList),
            Option(recursiveWriter(chapter.id))
          ) :: chaptersList
        }
      })
      chaptersList
    }

    println(chapterDTO.getRecursiveListChapterJson(recursiveWriter("0")))

    Ok(chapterDTO.getRecursiveListChapterJson(recursiveWriter("0")))

  }

  def loadFromJson = Action { implicit request =>

    val config = ConfigFactory.load("application.conf").getConfig("importJson")
    val fileName = config.getString("path")

    val source = Source.fromFile(fileName).getLines.mkString
    val json = Json.parse(source)

    val jsResult = json.validate[List[ChapterReader]](chapterDTO.ChaptersRead)

    articleService.deleteAllArticles()
    chapterService.deleteAllChapters()

    def recursiveReader(upperChapterId: String, chapterReader: ChapterReader): Unit ={
      val newChapterId = java.util.UUID.randomUUID().toString
      chapterService.createChapter(Chapter(newChapterId, chapterReader.shortName, chapterReader.fullName, upperChapterId))
      chapterReader.articles match {
        case Some(value) => value.foreach(article =>
          articleService.createArticle(Article(java.util.UUID.randomUUID().toString,
            article.shortName,
            article.fullName,
            article.text,
            newChapterId)))
        case None =>
      }
      chapterReader.children match {
        case Some(value) =>
          value.foreach( children =>
            recursiveReader(newChapterId, children))
        case None =>
      }
    }

    jsResult.get.foreach( chapter => recursiveReader("0", chapter))

    Redirect(routes.MainController.index())
  }

  def index = Action { implicit request =>
    Ok(views.html.index(articleForm, chapterForm)(request, msg = messagesApi.preferred(request)))
  }
}
