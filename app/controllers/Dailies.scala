package controllers

import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import java.util.Date
import securesocial.core.SecureSocial
import play.api.libs.ws.WS
import play.api.libs.json._
import models._
import scala.Some
import play.api.libs.functional.syntax._
import play.api.libs.concurrent.Execution.Implicits._
import play.api.libs.json
import org.joda.time.{DateTimeZone, DateTime}
import play.api.Play
import java.net.URLEncoder
import scala.concurrent.Future

/**
 * Created with IntelliJ IDEA.
 * User: paullawler
 * Date: 11/26/13
 * Time: 7:06 AM
 * To change this template use File | Settings | File Templates.
 */
object Dailies extends Controller with SecureSocial {

  val GithubClientId = Play.current.configuration.getString("github.onelineaday.clientId")
  val EST_OFFSET_HACK = 5

  implicit val commitReader = (
      (__ \ "sha").read[String] ~
      (__ \ "commit" \ "author" \ "date" ).read[Date] ~
      (__ \ "commit" \ "author" \ "name").read[String] ~
      (__ \ "commit" \ "author" \ "email" ).read[String] ~
      (__ \ "commit" \ "message").read[String] ~
      (__ \ "html_url").read[String]
    )(GithubCommit)

  val dailyForm = Form(
    mapping (
      "id" -> ignored(0L),
      "projectId" -> longNumber,
      "description" -> nonEmptyText,
      "duration" -> optional(number(min = 0)), // see docs for min/max
      "createdOn" -> ignored(new Date),
      "completedOn" -> optional(date("yyyy-MM-dd"))
    )(Daily.apply)(Daily.unapply)
  )

  val completeDailyForm = Form ("duration" -> number(min = 0))

  def daily(id: Long) = SecuredAction {
    val daily = Daily.find(id)
    Ok(views.html.dailies.item(daily, Project.find(daily.projectId), Resource.findByDailyId(id)))
  }

  def newDaily(projectId: Long) = SecuredAction {
    Ok(views.html.dailies.create(dailyForm, Project.find(projectId)))
  }

  def create = SecuredAction { implicit request =>
    dailyForm.bindFromRequest.fold(
      formWithErrors =>
        BadRequest(views.html.dailies.create(formWithErrors, Project.find(formWithErrors.data("projectId").toLong))),
      daily => {
        Daily.create(daily.projectId, daily.description, new Date())
        Redirect(routes.Projects.project(daily.projectId))
      }
    )
  }

  def delete(id: Long) = SecuredAction {
    val projectId = Daily.find(id).projectId
    Daily.delete(id)
    Redirect(routes.Projects.project(projectId))
  }

  def editCompletion(id: Long) = SecuredAction {
    Ok(views.html.dailies.modal_complete(completeDailyForm.fill(0), Daily.find(id)))
  }

  def complete(id: Long) = SecuredAction { implicit request =>
    completeDailyForm.bindFromRequest.fold(
      formWithErrors =>
        BadRequest(views.html.dailies.modal_complete(formWithErrors, Daily.find(id))),
      duration => {
        Daily.complete(id, duration, new Date())
        Redirect(routes.Projects.project(Daily.find(id).projectId))
      }
    )
  }

  def commits(dailyId: Long) = SecuredAction.async { implicit request =>
    val daily = Daily.find(dailyId)
    request.session.get("github_access_token") match {
      case Some(token) => {
        val repo = Repository.findByProjectId(daily.projectId).get
        val response = WS.url(commitsUrl(repo, daily)).withHeaders("Accept" -> "application/json", "Authorization" -> s"token $token").get()
        response.map { result =>
          asGithubCommit(result.json).foreach(commit => saveCommits(commit, repo, daily))
          Redirect(routes.Dailies.daily(dailyId))
        }
      }
      case None => {
        val redirectUrl = routes.Dailies.commits(dailyId).absoluteURL()
        val encodedUrl = URLEncoder.encode(redirectUrl, "UTF-8")
        val oauthUrl = s"https://github.com/login/oauth/authorize?scope=repo&client_id=$GithubClientId&redirect_uri=$encodedUrl"
        Future.successful(Redirect(new Call("GET", oauthUrl)))
      }
    }
//    Future.failed(throw new IllegalStateException())
  }

  private def asGithubCommit(json: JsValue): Seq[GithubCommit] = {
    json.asInstanceOf[JsArray].value.map { v =>
      v.validate[GithubCommit] match {
        case commit: JsSuccess[GithubCommit] => commit.get
      }
    }
  }

  private def commitsUrl(repo: Repository, daily: Daily): String = {
    val since = new DateTime(daily.completedOn.get, DateTimeZone.UTC).minusHours(EST_OFFSET_HACK)
    val until = since.plusHours(23).plusMinutes(59)
    s"https://api.github.com/repos/${repo.owner}/${repo.name}/commits?since=$since&until=$until"
  }

  private def saveCommits(commit: GithubCommit, repo: Repository, daily: Daily) = {
    Commit.create(daily.id, repo.id, commit.sha, commit.author, commit.author, commit.url, commit.message)
  }

//  json.validate[Place] match {
//    case s: JsSuccess[Place] => {
//      val place: Place = s.get
//      // do something with place
//    }
//    case e: JsError => {
//      // error handling flow
//    }
//  }



}
