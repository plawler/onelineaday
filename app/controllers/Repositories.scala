package controllers

import play.api.mvc.{Action, Controller}
import securesocial.core.SecureSocial

/**
 * Created with IntelliJ IDEA.
 * User: paullawler
 * Date: 2/23/14
 * Time: 10:01 PM
 * To change this template use File | Settings | File Templates.
 */
object Repositories extends Controller with SecureSocial {

  def callback = Action { implicit request =>
    val sessionCode = request.getQueryString("code")
    Ok
  }

}
