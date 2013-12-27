package controllers

import play.api.mvc._
import securesocial.core.SecureSocial

object Application extends Controller with SecureSocial {

  def index = SecuredAction {
    Redirect(routes.Projects.projects)
  }

}