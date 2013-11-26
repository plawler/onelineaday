package controllers

import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import views.dailies.DailyData
import java.util.Date

/**
 * Created with IntelliJ IDEA.
 * User: paullawler
 * Date: 11/26/13
 * Time: 7:06 AM
 * To change this template use File | Settings | File Templates.
 */
object Dailies extends Controller {

  val dailyForm = Form(
    mapping (
      "id" -> ignored(0L),
      "projectId" -> longNumber,
      "description" -> nonEmptyText,
      "duration" -> number(min = 0), // see docs for min/max
      "createdOn" -> ignored(new Date)
    )(DailyData.apply)(DailyData.unapply)
  )

  def dailies = TODO

  def daily(id: Long) = TODO

  def newDaily = TODO

  def create = TODO

  def edit(id: Long) = TODO

  def update(id: Long) = TODO

  def delete(id: Long) = TODO

}
