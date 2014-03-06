package controllers

import play.api.mvc.{Action, Controller}
import securesocial.core.SecureSocial
import play.api.libs.json._
import play.api.Play
import play.api.libs.ws.{WS, Response}
import play.api.libs.functional.syntax._
import scala.concurrent.Future
import play.api.libs.concurrent.Execution.Implicits._
import models.{GithubRepo}

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

  implicit val repositoryRead = (
      (__ \ "id").read[Long] and
      (__ \ "name").read[String] and
      (__ \ "owner" \ "login" ).read[String]
    )(GithubRepo)

//  http://stackoverflow.com/questions/20252970/play-framework-composing-actions-with-async-web-request
  def callback = SecuredAction.async { request =>
    request.getQueryString("code").map { code =>
      val sessionData = Json.obj(
              "client_id" -> GithubClientId,
              "client_secret" -> GithubClientSecret,
              "code" -> code
            )

      val projectId = request.getQueryString("projectId")

      WS.url("https://github.com/login/oauth/access_token")
        .withHeaders("Accept" -> "application/json").post(sessionData).map { result =>
          Redirect(routes.Repositories.repos(projectId.get.toLong)).withSession("token" -> (result.json \ "access_token").as[String])
        }
    }.getOrElse {
      Future.successful(InternalServerError) // possibly redirect to a nicer page explaining there was some difficulty connecting to github account
    }
  }

  def repos(projectId: Long) = SecuredAction.async { request =>
    val token = request.session("token")

    WS.url("https://api.github.com/user/repos")
      .withHeaders("Accept" -> "application/json", "Authorization" -> s"token ${token}")
      .get().map { result =>
        result.json.asInstanceOf[JsArray].value.map { repo =>
          val githubRepo = repo.as[GithubRepo]
          println(githubRepo.toString)
        }
      }

    Future.successful(InternalServerError)
  }

}