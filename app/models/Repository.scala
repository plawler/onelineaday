package models

import play.api.Play.current
import play.api.db._
import anorm._
import anorm.SqlParser._


/**
 * Created By: paullawler
 */

case class GithubRepo(id: Long, name: String, owner: String)

case class Repository(id: Long, userId: Long, projectId: Option[Long], githubId: Long, name: String, owner: String)

object Repository {

  val parser = {
    get[Long]("id") ~
    get[Long]("user_id") ~
    get[Option[Long]]("project_id") ~
    get[Long]("github_id") ~
    get[String]("name") ~
    get[String]("owner") map {
      case id~userId~projectId~githubId~name~owner =>
        Repository(id, userId, projectId, githubId, name, owner)
    }
  }

  def findById(id: Long): Option[Repository] = DB.withConnection { implicit conn =>
    SQL(
      """
      select * from repositories where id = {id}
      """
    ).on('id -> id).singleOpt(parser)
  }

  def findByName(name: String): Option[Repository] = DB.withConnection { implicit conn =>
    SQL(
      """
      select * from repositories where name = {name}
      """
    ).on('name -> name).singleOpt(parser)
  }

  def findByProjectId(projectId: Long): Option[Repository] = DB.withConnection { implicit conn =>
    SQL(
      """
      select * from repositories where project_id = {projectId}
      """
    ).on('projectId -> projectId).singleOpt(parser)
  }

  def create(userId: Long, githubId: Long, name: String, owner: String) = DB.withConnection { implicit conn =>
    SQL(
      """
      insert into repositories (user_id, github_id, name, owner)
      values ({userId}, {githubId}, {name}, {owner})
      """
    ).on('userId -> userId, 'githubId -> githubId, 'name -> name, 'owner -> owner).executeUpdate()
  }

  def findAll(userId: Long): Seq[Repository] = DB.withConnection { implicit conn =>
    SQL(
      """
      select * from repositories where user_id = {userId}
      """
    ).on('userId -> userId).as(parser *)
  }

  def linkProjectToRepo(projectId: Long, repoName: String) {
    link(findByName(repoName).get.id, Some(projectId))
  }

  def unlinkProjectFromRepo(id: Long) {
    link(id, Option.empty)
  }

  private def link(id: Long, projectId: Option[Long]) = DB.withConnection { implicit conn =>
    SQL(
      """
      update repositories set project_id = {projectId} where id = {id}
      """
    ).on('id -> id, 'projectId -> projectId).executeUpdate()
  }

}