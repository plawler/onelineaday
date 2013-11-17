package controllers

import play.api.mvc._
import play.api.data.Forms._
import play.api.data._
import models.Project
import java.util.Date

/**
 * Created with IntelliJ IDEA.
 * User: paullawler
 * Date: 11/16/13
 * Time: 9:50 PM
 * To change this template use File | Settings | File Templates.
 */
object Projects extends Controller {

  val projectForm = Form(
    tuple (
    "name" -> nonEmptyText,
    "description" -> nonEmptyText
    )
    // using tuples: https://groups.google.com/forum/#!topic/play-framework/RLjwgiGDYP4
  )

  def projects = Action { implicit request =>
    Ok(views.html.projects.list(Project.all()))
  }

  def project(id: Long) = TODO

  def newProject = Action { implicit request =>
    Ok(views.html.projects.create(projectForm))
  }

  def add = Action { implicit request =>
    projectForm.bindFromRequest.fold(
      formWithErrors => BadRequest(views.html.projects.create(formWithErrors)),
      data => {
          Project.create(data._1, data._2, new Date())
          Redirect(routes.Projects.projects)
      }
    )
  }

  def delete(id: Long) = TODO

}
