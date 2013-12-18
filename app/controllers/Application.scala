package controllers

import play.api._
import play.api.mvc._
import securesocial.core.SecureSocial

object Application extends Controller with SecureSocial {

  def index = Action {
    Redirect(routes.Projects.projects)
  }

}