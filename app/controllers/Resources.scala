package controllers

import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import models.{Daily, Resource}
import java.util.Date

/**
 * Created with IntelliJ IDEA.
 * User: paullawler
 * Date: 12/6/13
 * Time: 10:42 PM
 * To change this template use File | Settings | File Templates.
 */
object Resources extends Controller {

  val resourceForm = Form(
    mapping (
      "id" -> ignored(0L),
      "dailyId" -> longNumber,
      "url" -> nonEmptyText,
      "title" -> nonEmptyText,
      "comment" -> optional(text),
      "tags" -> optional(text),
      "createdOn" -> ignored(new Date)
    )(Resource.apply)(Resource.unapply)
  )

  def get(id: Long) = TODO

  def newResource(dailyId: Long) = Action {
    Ok(views.html.resources.modal_new(resourceForm, Daily.find(dailyId)))
  }

  def create = Action { implicit request =>
    resourceForm.bindFromRequest.fold(
      formWithErrors =>
        BadRequest(views.html.resources.modal_new(formWithErrors, Daily.find(formWithErrors.data("dailyId").toLong))),
      resource => {
        Resource.create(resource.dailyId, resource.url, resource.title, resource.comment, resource.tags, new Date())
        Ok(views.html.dailies.resource_rows(Resource.findByDailyId(resource.dailyId)))
      }
    )
  }

}
