package controllers

import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import java.util.Date
import models.{Resource, Project, Daily}
import securesocial.core.SecureSocial

/**
 * Created with IntelliJ IDEA.
 * User: paullawler
 * Date: 11/26/13
 * Time: 7:06 AM
 * To change this template use File | Settings | File Templates.
 */
object Dailies extends Controller with SecureSocial {

  val dailyForm = Form(
    mapping (
      "id" -> ignored(0L),
      "projectId" -> longNumber,
      "description" -> nonEmptyText,
      "duration" -> number(min = 0), // see docs for min/max
      "createdOn" -> ignored(new Date),
      "completedOn" -> optional(date("yyyy-MM-dd"))
    )(Daily.apply)(Daily.unapply)
  )

  def dailies(projectId: Long) = TODO

  def daily(id: Long) = SecuredAction {
    val daily = Daily.find(id)
    Ok(views.html.dailies.item(daily, Project.find(daily.projectId), Resource.findByDailyId(id)))
  }

  def newDaily(projectId: Long) = Action {
    Ok(views.html.dailies.create(dailyForm, Project.find(projectId)))
  }

  def create = SecuredAction { implicit request =>
    dailyForm.bindFromRequest.fold(
      formWithErrors =>
        BadRequest(views.html.dailies.create(formWithErrors, Project.find(formWithErrors.data("projectId").toLong))),
      daily => {
        Daily.create(daily.projectId, daily.description, daily.duration, new Date())
        Redirect(routes.Projects.project(daily.projectId))
      }
    )
  }

  def delete(id: Long) = SecuredAction {
    val projectId = Daily.find(id).projectId
    Daily.delete(id)
    Redirect(routes.Projects.project(projectId))
  }

  def complete(id: Long) = SecuredAction {
    Daily.complete(id, new Date())
    val daily = Daily.find(id)
    Redirect(routes.Projects.project(daily.projectId))
  }

}
