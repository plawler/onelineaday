package models

import play.api.Play.current

import anorm._
import anorm.SqlParser._

import java.util.Date
import play.api.db._
import org.joda.time.{Days, DateTime}
import securesocial.core.Identity

/**
 * Created with IntelliJ IDEA.
 * User: paullawler
 * Date: 11/17/13
 * Time: 9:30 AM
 * To change this template use File | Settings | File Templates.
 */
case class Project(id: Long, name: String, description: String, createdOn: Date)

case class ProjectDaily(projectId: Long, dailyId: Long, description: String, createdOn: Date, completedOn: Option[Date],
                        duration: Int, resourceCount: Option[Long])

object Project {

  // https://github.com/Diego81/workwithplay/tree/master/20130508
  // http://janhelwich.wordpress.com/tag/anorm/
  // http://danielwesthe ide.com/blog/2012/12/19/the-neophytes-guide-to-scala-part-5-the-option-type.html

  val projectParser = {
    get[Long]("id") ~
    get[String]("name") ~
    get[String]("description") ~
    get[Date]("created_on") map {
      case id ~ name ~ description ~ created_on => Project(id, name, description, created_on)
    }
  }

  val projectDailyParser = {
    get[Long]("project_id") ~
    get[Long]("id") ~
    get[String]("description") ~
    get[Date]("created_on") ~
    get[Option[Date]]("completed_on") ~
    get[Int]("duration") ~
    get[Option[Long]]("cnt") map {
      case projectId~dailyId~description~createdOn~completedOn~duration~resourceCount =>
        ProjectDaily(projectId, dailyId, description, createdOn, completedOn, duration, resourceCount)
    }
  }

  def calculateStreak(dailies: List[ProjectDaily], referenceDate: Date, total: Int): Int = {
    if (dailies.isEmpty) total
    else {
      val daily = dailies.head
      if (daily.completedOn == None) total
      else {
        val completedOn = daily.completedOn.get
        val days = daysBetween(completedOn, referenceDate)
        if (days > 1) total
        else calculateStreak(dailies.tail, completedOn, total + days)
      }
    }
  }

  private def daysBetween(start: Date, end: Date): Int = {
    Days.daysBetween(new DateTime(start).toLocalDate, new DateTime(end).toLocalDate).getDays
  }

  def find(id: Long): Project = DB.withConnection {
    implicit conn =>
      SQL("select * from projects where id = {id}").on(
        'id -> id
      ).using(projectParser).single()
  }

  def all(userId: Long): List[Project] = DB.withConnection {
    implicit conn =>
      SQL("select * from projects where user_id = {userId} order by created_on desc"
      ).on('userId -> userId).as(projectParser *)
  }

  def create(name: String, description: String, createdOn: Date, userId: Long) {
    DB.withConnection {
      implicit conn =>
        SQL(
          """
          insert into projects (name, description, created_on, user_id)
          values ({name}, {description}, {created_on}, {userId})
          """
        ).on(
          'name -> name, 'description -> description, 'created_on -> createdOn, 'userId -> userId
        ).executeUpdate
    }
  }

  def update(id: Long, name: String, description: String) {
    DB.withConnection {
      implicit conn =>
        SQL("update projects set name = {name}, description = {description} where id = {id}").on(
          'name -> name, 'description -> description, 'id -> id
        ).executeUpdate
    }
  }

  def delete(id: Long) {
    DB.withConnection {
      implicit conn => SQL("delete from projects where id = {id}").on('id -> id).executeUpdate
    }
  }

  def findProjectDailies(projectId: Long): List[ProjectDaily] = DB.withConnection { implicit conn =>
    SQL(
      """
      select d.project_id, d.id, d.description, d.created_on, d.completed_on, d.duration, r.cnt
      from dailies d
      left join (select daily_id, count(id) as cnt
            from resources
            group by daily_id) as r
        on r.daily_id = d.id
      where d.project_id = {projectId}
      order by d.created_on desc, d.completed_on desc
      """
    ).on('projectId -> projectId).as(projectDailyParser *)
  }
  /*
  
   */

}
