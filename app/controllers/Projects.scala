package controllers

import play.api.mvc.{Action, Controller}
import play.api.data.Forms._
import play.api.data._

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
  )

  def projects = Action { implicit request =>
    Ok(views.html.projects.list(List()))
  }

  def project(id: Long) = TODO

  def newProject = Action { implicit request =>
    Ok(views.html.projects.create(projectForm))
  }

  def add = TODO

  def delete(id: Long) = TODO

}
