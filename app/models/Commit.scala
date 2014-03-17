package models

import play.api.Play.current
import play.api.db._
import anorm._
import anorm.SqlParser._

/**
 * Created By: paullawler
 */

case class Commit(id: Long, dailyId: Long, sha: String, author: String, committer: String, html_url: String, message: String)

object Commit {

  val parser = {
    get[Long]("id") ~
    get[Long]("daily_id") ~
    get[String]("sha") ~
    get[String]("author") ~
    get[String]("committer") ~
    get[String]("html_url") ~
    get[String]("message") map {
      case id~dailyId~sha~author~committer~htmlUrl~message =>
        Commit(id, dailyId, sha, author, committer, htmlUrl, message)
    }
  }

  def create(dailyId: Long, sha: String, author: String, committer: String, html_url: String, message: String)
    = DB.withConnection { implicit conn =>
    SQL(
      """
      insert into commits(daily_id, sha, author, committer, url, message)
      values ({dailyId}, {sha}, {author}, {committer}, {url}, {message})
      """
    ).on('dailyId -> dailyId, 'sha -> sha, 'author -> author, 'committer -> committer, 'url -> html_url, 'message -> message).executeUpdate()
  }

}
