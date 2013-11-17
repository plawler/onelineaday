package controllers

import play.api._
import play.api.mvc._

object Application extends Controller {

  def index = Action {
//    Ok(views.html.index("Your form application is ready."))
    Redirect(routes.Projects.projects)
  }

}