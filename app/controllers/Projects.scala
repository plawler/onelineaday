package controllers

import play.api.mvc._
import play.api.data.Forms._
import play.api.data._
import models.{User, Project}
import java.util.Date
import securesocial.core.SecureSocial
import play.api.Play

/**
 * Created with IntelliJ IDEA.
 * User: paullawler
 * Date: 11/16/13
 * Time: 9:50 PM
 * To change this template use File | Settings | File Templates.
 */
object Projects extends Controller with SecureSocial {

  val GithubClientId = Play.current.configuration.getString("github.onelineaday.clientId")

  val projectForm = Form(
    mapping(
      "id" -> ignored(0L),
      "name" -> nonEmptyText,
      "description" -> nonEmptyText,
      "createdOn" -> ignored(new Date())
    )(Project.apply)(Project.unapply)
    // using tuples: https://groups.google.com/forum/#!topic/play-framework/RLjwgiGDYP4
  )

  //http://engineering.linkedin.com/play/play-framework-democratizing-functional-programming-modern-web-programmers
  def projects = SecuredAction { implicit request =>
    request.user match {
      case user: User => Ok(views.html.projects.list(Project.all(user.id)))
      //case _ => throw new RuntimeException("no user in context. no soup for you!!")
    }
  }

  def project(id: Long) = SecuredAction {
    implicit request =>
      val projectDailies = Project.findProjectDailies(id)
      val streak = Project.calculateStreak(projectDailies, new Date(), 0)
      Ok(views.html.projects.item(Project.find(id), projectDailies, streak))
  }

  def newProject = SecuredAction {
    Ok(views.html.projects.create(projectForm))
  }

  def add = SecuredAction { implicit request =>
    projectForm.bindFromRequest.fold(
      formWithErrors => BadRequest(views.html.projects.create(formWithErrors)),
      project => {
        request.user match {
          case user: User => {
            Project.create(project.name, project.description, new Date(), user.id)
            Redirect(routes.Projects.projects)
          }
        }
      }
    )
  }

  // https://groups.google.com/forum/#!topic/play-framework/d1hd_JamPW4
  def edit(id: Long) = SecuredAction { implicit request =>
    val project = Project.find(id)
    Ok(views.html.projects.edit(project, projectForm.fill(project), GithubClientId, s"http://${request.host}/github/callback?projectId=${id}"))
  }

  def update(id: Long) = SecuredAction { implicit request =>
    projectForm.bindFromRequest.fold(
      formWithErrors => BadRequest(views.html.projects.edit(Project.find(id), projectForm, GithubClientId, s"http://${request.host}/github/callback?projectId=${id}")),
      project => {
        Project.update(id, project.name, project.description)
        Redirect(routes.Projects.project(id))
      }
    )
  }

  def delete(id: Long) = SecuredAction {
    Project.delete(id)
    Redirect(routes.Projects.projects)
  }

}
