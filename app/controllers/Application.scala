package controllers

import play.api.mvc._
import securesocial.core.SecureSocial

object Application extends Controller with SecureSocial {

  // todo: this is why the login page always flashes the error message "you must be logged in"
  def index = SecuredAction {
    Redirect(routes.Projects.projects)
  }

}