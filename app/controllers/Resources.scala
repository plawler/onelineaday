package controllers

import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

/**
 * Created with IntelliJ IDEA.
 * User: paullawler
 * Date: 12/6/13
 * Time: 10:42 PM
 * To change this template use File | Settings | File Templates.
 */
object Resources extends Controller {

  def get(id: Long) = TODO

  def newResource(dailyId: Long) = Action {
    Ok(views.html.resources.modal_new())
  }

}
