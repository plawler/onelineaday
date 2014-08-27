package controllers

import play.api.mvc.{Controller}
import securesocial.core.{Identity, SecureSocial}
import play.api.libs.json._
import play.api.Play.current
import play.api.libs.ws.WS
import play.api.libs.functional.syntax._
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

  implicit val repositoryRead = (
      (__ \ "id").read[Long] and
      (__ \ "name").read[String] and
      (__ \ "owner" \ "login" ).read[String] and
      (__ \ "html_url").read[String]
    )(GithubRepo)

  val selectRepoForm = Form(
    tuple(
      "projectId" -> longNumber,
      "repoName" -> nonEmptyText
    )
  )

  def repos(projectId: Long) = SecuredAction.async { request =>
    val token = request.session("github_access_token")
    val response = WS.url("https://api.github.com/user/repos")
      .withHeaders("Accept" -> "application/json", "Authorization" -> s"token $token").get()
    response.map { result =>
      asGithubRepos(result.json).foreach {repo => create(repo, request.user)}
      Ok(views.html.repositories.link(selectRepoForm, getRepos(request.user), projectId))
    }
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

  def unlink(id: Long) = SecuredAction { implicit request =>
    Repository.findById(id) match {
      case Some(repository) =>
        Repository.unlinkProjectFromRepo(repository.id)
        repository.projectId match {
          case Some(projectId) => Redirect(routes.Projects.project(projectId))
          case None => Redirect(routes.Projects.projects)
        }
      case None => Redirect(routes.Projects.projects)
    }
  }

  private def asGithubRepos(json: JsValue): Seq[GithubRepo] = {
    json.asInstanceOf[JsArray].value.map { v =>
      v.as[GithubRepo]
    }
  }

  private def create(repo: GithubRepo, user: Identity) = {
    if (Repository.findByName(repo.name) == None) {
      user match {
        case user: User => Repository.create(user.id, repo.id, repo.name, repo.owner, repo.url)
      }
    }
  }

  private def getRepos(identity: Identity): Seq[Repository] = {
    identity match { case user: User => Repository.findAll(user.id)}
  }

}