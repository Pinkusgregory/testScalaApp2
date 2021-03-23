package controllers

import javax.inject.Inject
import play.api.mvc.{AbstractController, ControllerComponents}

import models._
import play.api.data.Form
import play.api.data.Forms.{mapping, nonEmptyText}
import play.api.i18n.MessagesApi
import services._



class ChapterController @Inject() (cc: ControllerComponents)(messages: MessagesApi) extends AbstractController(cc) {

  val chapterService: ChapterService = new DbChapterService()
  val chapterDTO: ChapterDTO = new ChapterDTO

  val chapterForm: Form[ChapterWithoutId] = Form {
    mapping(
      "short_name" -> nonEmptyText,
      "full_name" -> nonEmptyText
    )(ChapterWithoutId.apply)(ChapterWithoutId.unapply)
  }

  val articleForm: Form[ArticleWithoutId] = Form {
    mapping(
      "short_name" -> nonEmptyText,
      "full_name" -> nonEmptyText,
      "text" -> nonEmptyText
    )(ArticleWithoutId.apply)(ArticleWithoutId.unapply)
  }

  def get(id: String) = Action { implicit request =>

    chapterService.findChapter(id) match {
      case Some(value) => Ok(views.html.chapterPage(value, chapterForm, articleForm)(request, msg = messagesApi.preferred(request)))
      case None => NotFound
    }
  }

  def delete(id: String) = Action { implicit request =>
    chapterService.deleteChapter(id)
    Redirect(routes.MainController.index())
  }

  def create(id: String) = Action { implicit request =>

    chapterForm.bindFromRequest.fold(
      formWithErrors => BadRequest("Not good"),
      formData => {
        chapterService.createChapter(Chapter(
          java.util.UUID.randomUUID().toString,
          formData.shortName,
          formData.fullName,
          id))
        Redirect(routes.MainController.index())
      }
    )
  }

  def update(id: String, upperChapterId: String) = Action { implicit request =>

    chapterForm.bindFromRequest.fold(
      formWithErrors => BadRequest("Not good"),
      formData => {
        chapterService.updateChapter(id, formData.shortName, formData.fullName, upperChapterId)
        Redirect(routes.MainController.index())
      }
    )
  }

//  def list() = Action { implicit request =>
//
//    Ok(chapterDTO.getListChapterJson(chapterService.list()))
//  }
}
