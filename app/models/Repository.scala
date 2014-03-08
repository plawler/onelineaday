package models

import play.api.Play.current
import play.api.db._
import anorm._
import anorm.SqlParser._


/**
 * Created By: paullawler
 */

case class Repository(id: Long, userId: Long, projectId: Long, githubId: Long, name: String, owner: String)

object Repository {

  val parser = {
    get[Long]("id") ~
    get[Long]("user_id") ~
    get[Long]("project_id") ~
    get[Long]("github_id") ~
    get[String]("name") ~
    get[String]("owner") map {
      case id~userId~projectId~githubId~name~owner =>
        Repository(id, userId, projectId, githubId, name, owner)
    }
  }

  def findByName(name: String): Option[Repository] = DB.withConnection { implicit conn =>
    SQL(
      """
      select * from repositories where name = {name}
      """
    ).on('name -> name).as(Repository.parser.singleOpt)
  }

  def create(userId: Long, githubId: Long, name: String, owner: String) = DB.withConnection { implicit conn =>
    SQL(
      """
      insert into repositories (github_id, name, owner)
      values ({githubId}, {name}, {owner})
      """
    ).on('githubId -> githubId, 'name -> name, 'owner -> owner).executeUpdate()
  }

}