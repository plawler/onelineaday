package controllers

import play.api.mvc.{Action, Controller}
import securesocial.core.SecureSocial
import play.api.libs.json.Json
import play.api.Play
import play.api.libs.ws.{WS, Response}
import scala.concurrent.Future
import play.api.libs.concurrent.Execution.Implicits._
/**
 * Created with IntelliJ IDEA.
 * User: paullawler
 * Date: 2/23/14
 * Time: 10:01 PM
 * To change this template use File | Settings | File Templates.
 */
object Repositories extends Controller with SecureSocial {

  val GithubClientId = Play.current.configuration.getString("github.onelineaday.clientId")
  val GithubClientSecret = Play.current.configuration.getString("github.onelineaday.clientSecret")


//  http://stackoverflow.com/questions/20252970/play-framework-composing-actions-with-async-web-request
  def callback = Action.async { request =>

    request.getQueryString("code").map { code =>

      val sessionData = Json.obj(
              "client_id" -> GithubClientId,
              "client_secret" -> GithubClientSecret,
              "code" -> code
            )

      WS.url("https://github.com/login/oauth/access_token")
        .withHeaders("Accept" -> "application/json").post(sessionData).map { result =>
          Redirect(routes.Projects.projects).withSession("token" -> (result.json \ "access_token").as[String])
        }

    }.getOrElse {
      Future.successful(Redirect(routes.Projects.projects))
    }
  }

}