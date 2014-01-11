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
      "duration" -> optional(number(min = 0)), // see docs for min/max
      "createdOn" -> ignored(new Date),
      "completedOn" -> optional(date("yyyy-MM-dd"))
    )(Daily.apply)(Daily.unapply)
  )

  val completeDailyForm = Form ("duration" -> number(min = 0))


  def daily(id: Long) = SecuredAction {
    val daily = Daily.find(id)
    Ok(views.html.dailies.item(daily, Project.find(daily.projectId), Resource.findByDailyId(id)))
  }

  // todo: change to "create"
  def newDaily(projectId: Long) = SecuredAction {
    Ok(views.html.dailies.create(dailyForm, Project.find(projectId)))
  }

  // todo: change to "save"
  def create = SecuredAction { implicit request =>
    dailyForm.bindFromRequest.fold(
      formWithErrors =>
        BadRequest(views.html.dailies.create(formWithErrors, Project.find(formWithErrors.data("projectId").toLong))),
      daily => {
        Daily.create(daily.projectId, daily.description, new Date())
        Redirect(routes.Projects.project(daily.projectId))
      }
    )
  }

  def delete(id: Long) = SecuredAction {
    val projectId = Daily.find(id).projectId
    Daily.delete(id)
    Redirect(routes.Projects.project(projectId))
  }

  def editCompletion(id: Long) = SecuredAction {
    Ok(views.html.dailies.modal_complete(completeDailyForm.fill(0), Daily.find(id)))
  }

  def complete(id: Long) = SecuredAction { implicit request =>
    completeDailyForm.bindFromRequest.fold(
      formWithErrors =>
        BadRequest(views.html.dailies.modal_complete(formWithErrors, Daily.find(id))),
      duration => {
        Daily.complete(id, duration, new Date())
        Redirect(routes.Projects.project(Daily.find(id).projectId))
      }
    )
  }

}
