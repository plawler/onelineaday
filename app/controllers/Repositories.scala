package controllers

import play.api.mvc.Controller
import securesocial.core.{Identity, SecureSocial}
import play.api.libs.json._
import play.api.Play
import play.api.libs.ws.WS
import play.api.libs.functional.syntax._
import scala.concurrent.Future
import play.api.libs.concurrent.Execution.Implicits._
import models.{GithubRepo, User, Repository}
import play.api.data._
import play.api.data.Forms._


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

  val selectRepoForm = Form(
    tuple(
      "projectId" -> longNumber,
      "repoName" -> nonEmptyText
    )
  )

  val repoForm = Form(
    mapping (
      "id" -> longNumber,
      "userId" -> longNumber,
      "projectId" -> optional(longNumber),
      "githubId" -> longNumber,
      "name" -> nonEmptyText,
      "owner" -> nonEmptyText
    )(Repository.apply)(Repository.unapply)
  )

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
          Redirect(routes.Repositories.repos(projectId.get.toLong))
            .withSession("github_access_token" -> (result.json \ "access_token").as[String])
        }
    }.getOrElse {
      Future.successful(Ok) // not sure what to do here
    }
  }

  def repos(projectId: Long) = SecuredAction.async { request =>
    val token = request.session("github_access_token")
    WS.url("https://api.github.com/user/repos")
      .withHeaders("Accept" -> "application/json", "Authorization" -> s"token ${token}")
      .get().map { result =>
        asGithubRepos(result.json).foreach {repo =>
          create(repo, request.user)
        }
      }
    Future.successful(Ok(views.html.repositories.link(selectRepoForm, getRepos(request.user), projectId)))
  }

  def link = SecuredAction { implicit request =>
    selectRepoForm.bindFromRequest.fold(
      formWithErrors => BadRequest(views.html.repositories.link(formWithErrors, getRepos(request.user), formWithErrors.data("projectId").toLong)),
      selection => {
        val projectId = selection._1; val repoName = selection._2
        Repository.linkProjectToRepo(projectId, repoName)
        Redirect(routes.Projects.project(projectId))
      }
    )
  }

  private def asGithubRepos(json: JsValue): Seq[GithubRepo] = {
    json.asInstanceOf[JsArray].value.map { v =>
      v.as[GithubRepo]
    }
  }

  private def create(repo: GithubRepo, user: Identity) = {
    if (Repository.findByName(repo.name) == None) {
      user match {
        case user: User => Repository.create(user.id, repo.id, repo.name, repo.owner)
      }
    }
  }

  private def getRepos(identity: Identity): Seq[Repository] = {
    identity match {
      case user: User => Repository.findAll(user.id)
    }
  }

}