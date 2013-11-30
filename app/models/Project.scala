package models

import play.api.Play.current

import anorm._
import anorm.SqlParser._

import java.util.Date
import play.api.db._
import org.joda.time.{Days, DateTime}

/**
 * Created with IntelliJ IDEA.
 * User: paullawler
 * Date: 11/17/13
 * Time: 9:30 AM
 * To change this template use File | Settings | File Templates.
 */
case class Project(id: Long, name: String, description: String, createdOn: Date)

object Project {

  // https://github.com/Diego81/workwithplay/tree/master/20130508
  // http://janhelwich.wordpress.com/tag/anorm/
  // http://danielwesthe ide.com/blog/2012/12/19/the-neophytes-guide-to-scala-part-5-the-option-type.html

  val project = {
    get[Long]("id") ~
      get[String]("name") ~
      get[String]("description") ~
      get[Date]("created_on") map {
      case id ~ name ~ description ~ created_on => Project(id, name, description, created_on)
    }
  }

  def streak(projectId: Long): Int = {
    calculateStreak(Daily.findByProjectId(projectId), new Date(), 0)
  }

  def calculateStreak(dailies: List[Daily], referenceDate: Date, total: Int): Int = {
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
    return Days.daysBetween(new DateTime(start).toLocalDate, new DateTime(end).toLocalDate).getDays
  }

  def find(id: Long): Project = DB.withConnection {
    implicit conn =>
      SQL("select * from projects where id = {id} order by created_on").on(
        'id -> id
      ).using(project).single()
  }

  def all(): List[Project] = DB.withConnection {
    implicit conn =>
      SQL("select * from projects").as(project *)
  }

  def create(name: String, description: String, createdOn: Date) {
    DB.withConnection {
      implicit conn =>
        SQL("insert into projects (name, description, created_on) values ({name}, {description}, {created_on})").on(
          'name -> name, 'description -> description, 'created_on -> createdOn
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

}
