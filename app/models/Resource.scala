package models

import java.util.Date


import play.api.Play.current
import play.api.db._
import anorm._
import anorm.SqlParser._
/**
 * Created with IntelliJ IDEA.
 * User: paullawler
 * Date: 12/5/13
 * Time: 7:07 PM
 * To change this template use File | Settings | File Templates.
 */

case class Resource(id: Long, dailyId: Long, url: String, title: String, comment: Option[String],
                    tags: Option[String], createdOn: Date)

object Resource {

  val resource = {
    get[Long]("id") ~
    get[Long]("daily_id") ~
    get[String]("url") ~
    get[String]("title") ~
    get[Option[String]]("comment") ~
    get[Option[String]]("tags") ~
    get[Date]("created_on") map {
      case id~dailyId~url~title~comment~tags~createdOn =>
        Resource(id, dailyId, url, title, comment, tags, createdOn)
    }
  }

  def create(dailyId: Long, url: String, title: String, comment: Option[String], tags: Option[String], createdOn: Date) =
    DB.withConnection { implicit conn =>
    SQL(
      """
      insert into resources (daily_id, url, title, comment, tags, created_on)
      values ({dailyId}, {url}, {title}, {comment}, {tags}, {createdOn})
      """
    ).on('dailyId -> dailyId, 'url -> url, 'title -> title, 'comment -> comment, 'tags -> tags,
      'createdOn -> createdOn).executeUpdate()
  }

  def findByDailyId(dailyId: Long): List[Resource] = DB.withConnection { implicit conn =>
    SQL("select * from resources where daily_id = {dailyId}").on('dailyId -> dailyId).as(resource *)
  }

}
