package models

import java.util.Date

/**
 * Created with IntelliJ IDEA.
 * User: paullawler
 * Date: 11/17/13
 * Time: 9:30 AM
 * To change this template use File | Settings | File Templates.
 */
case class Project(id: Long, name: String, description: String, createdOn: Date)

object Project {

  def all(): List[Project] = Nil

  def create(name: String, description: String, createdOn: Date) {}

  def delete(id: Long) {}

}
