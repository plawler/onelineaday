package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

import models._
import views._
/**
 * Created with IntelliJ IDEA.
 * User: paullawler
 * Date: 11/16/13
 * Time: 9:31 PM
 * To change this template use File | Settings | File Templates.
 */
object Authentication extends Controller {

  val loginForm = Form (
    tuple (
      "email" -> text,
      "password" -> text
    ) verifying ("Invalid email or password", result => result match {
      case (email, password) => email.equals("paul.lawler@onelineaday.me") && password.equals("secret") //User.authenticate(email, password).isDefined
    })
  )

  /**
   * Login page.
   */
  def login = Action { implicit request =>
    Ok(html.login(loginForm))
  }

  /**
   * Handle login form submission.
   */
  def authenticate = Action { implicit request =>
    loginForm.bindFromRequest.fold(
      formWithErrors => BadRequest(html.login(formWithErrors)),
      user => Redirect(routes.Projects.projects).withSession("email" -> user._1)
    )
  }

  /**
   * Logout and clean the session.
   */
  def logout = Action {
    Redirect(routes.Authentication.logout).withNewSession.flashing(
      "success" -> "You've been logged out"
    )
  }

}
