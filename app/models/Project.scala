package models

import play.api.Play.current

import anorm._
import anorm.SqlParser._

import java.util.Date
import play.api.db._

/**
 * Created with IntelliJ IDEA.
 * User: paullawler
 * Date: 11/17/13
 * Time: 9:30 AM
 * To change this template use File | Settings | File Templates.
 */
case class Project(id: Long, name: String, description: String, createdOn: Date)

object Project {

  val project = {
    get[Long]("id") ~
    get[String]("name") ~
    get[String]("description") ~
    get[Date]("created_on") map {
      case id~name~description~created_on => Project(id, name, description, created_on)
    }
  }

  def all(): List[Project] = DB.withConnection { implicit c =>
    SQL("select * from projects").as(project *)
  }

  def create(name: String, description: String, createdOn: Date) {
    DB.withConnection { implicit c =>
      SQL("insert into projects (name, description, created_on) values ({name}, {description}, {created_on})").on(
        'name -> name, 'description -> description, 'created_on -> createdOn
      ).executeUpdate()
    }
  }

  def delete(id: Long) {}

}
