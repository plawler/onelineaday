package models

import java.util.Date

import play.api.Play.current
import play.api.db._
import anorm._
import anorm.SqlParser._
/**
 * Created with IntelliJ IDEA.
 * User: paullawler
 * Date: 11/26/13
 * Time: 12:38 AM
 * To change this template use File | Settings | File Templates.
 */
case class Daily(id: Long, projectId: Long, description: String, duration: Int, createdOn: Date, completedOn: Option[Date])

object Daily {

  val daily = {
    get[Long]("id") ~
    get[Long]("project_id") ~
    get[String]("description") ~
    get[Int]("duration") ~
    get[Date]("created_on") ~
    get[Option[Date]]("completed_on") map {
      case id~projectId~description~duration~createdOn~completedOn =>
        Daily(id, projectId, description, duration, createdOn, completedOn)
    }
  }

  def findByProjectId(projectId: Long): List[Daily] = DB.withConnection { implicit conn =>
    SQL("select * from dailies where project_id = {projectId} order by created_on desc, completed_on desc").on('projectId -> projectId).as(daily *)
  }

  def create(projectId: Long, description: String, duration: Int, createdOn: Date) =
    DB.withConnection { implicit conn =>
    SQL(
      """
      insert into dailies (project_id, description, duration, created_on)
      values ({project_id}, {description}, {duration}, {created_on})
      """
    ).on('project_id -> projectId, 'description -> description, 'duration -> duration, 'created_on -> createdOn
    ).executeUpdate()
  }

  def find(id: Long): Daily = DB.withConnection { implicit conn =>
    SQL("select * from dailies where id = {id}").on('id -> id).using(daily).single()
  }

  def complete(id: Long, completedOn: Date) = DB.withConnection { implicit conn =>
    SQL(
      """
      update dailies set completed_on = {completedOn} where id = {id}
      """
    ).on('completedOn -> completedOn, 'id -> id).executeUpdate()
  }

}
