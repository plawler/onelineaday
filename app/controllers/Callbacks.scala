package controllers

import securesocial.core.SecureSocial
import play.api.mvc.{Call, Controller}
import play.api.libs.json.Json
import play.api.libs.ws.WS
import play.api.Play
import play.api.libs.concurrent.Execution.Implicits._

import java.net.URLDecoder

/**
 * Created By: paullawler
 */
object Callbacks extends Controller with SecureSocial {

  val GithubClientId = Play.current.configuration.getString("github.onelineaday.clientId")
  val GithubClientSecret = Play.current.configuration.getString("github.onelineaday.clientSecret")

  //  http://stackoverflow.com/questions/20252970/play-framework-composing-actions-with-async-web-request
  def github = SecuredAction.async { request =>
    val code = request.getQueryString("code")
    val sessionData = Json.obj(
      "client_id" -> GithubClientId,
      "client_secret" -> GithubClientSecret,
      "code" -> code
    )
    val redirectUrl = URLDecoder.decode(request.getQueryString("redirectUrl").get, "US-ASCII")
    val response = WS.url("https://github.com/login/oauth/access_token").withHeaders("Accept" -> "application/json").post(sessionData)
    response.map { result =>
      Redirect(new Call("GET", redirectUrl))
        .withSession("github_access_token" -> (result.json \ "access_token").as[String])
    }
  }

}
