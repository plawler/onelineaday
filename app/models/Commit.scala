package models

import play.api.Play.current
import play.api.db._
import anorm._
import anorm.SqlParser._
import java.util.Date

/**
 * Created By: paullawler
 */

case class Commit(id: Long, repositoryId: Long, dailyId: Long, sha: String, author: String, committer: String, html_url: String, message: String)

//case class GithubCommit(sha: String, date: Date, author: String, email: String, message: String, url: String)
case class GithubCommit(sha: String, date: Date, author: String, email: String, message: String, url: String)

object Commit {

  val parser = {
    get[Long]("id") ~
    get[Long]("repository_id") ~
    get[Long]("daily_id") ~
    get[String]("sha") ~
    get[String]("author") ~
    get[String]("committer") ~
    get[String]("url") ~
    get[String]("message") map {
      case id~repositoryId~dailyId~sha~author~committer~htmlUrl~message =>
        Commit(id, repositoryId, dailyId, sha, author, committer, htmlUrl, message)
    }
  }

  def create(dailyId: Long, repositoryId: Long, sha: String, author: String, committer: String, html_url: String, message: String)
    = DB.withConnection { implicit conn =>
    SQL(
      """
      insert into commits(repository_id, daily_id, sha, author, committer, url, message)
      values ({repositoryId}, {dailyId}, {sha}, {author}, {committer}, {url}, {message})
      """
    ).on('repositoryId -> repositoryId, 'dailyId -> dailyId, 'sha -> sha, 'author -> author, 'committer -> committer, 'url -> html_url, 'message -> message).executeUpdate()
  }

  def findById(id: Long): Option[Commit] = DB.withConnection { implicit conn =>
    SQL(
      """
      select * from commits where id = {id}
      """
    ).on('id -> id).as(parser.singleOpt)
  }

  def findByDailyId(dailyId: Long): Seq[Commit] = DB.withConnection { implicit conn =>
    SQL(
      """
      select * from commits where daily_id = {dailyId}
      """
    ).on('dailyId -> dailyId).as(parser *)
  }

  def findBySha(sha: String): Option[Commit] = DB.withConnection { implicit conn =>
    SQL(
      """
      select * from commits where sha = {sha}
      """
    ).on('sha -> sha).as(parser.singleOpt)
  }

}
