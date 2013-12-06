package models

import java.util.Date

/**
 * Created with IntelliJ IDEA.
 * User: paullawler
 * Date: 12/5/13
 * Time: 7:07 PM
 * To change this template use File | Settings | File Templates.
 */

case class Resource(id: Long, url: String, title: String, comment: String, tags: String, createdOn: Date)

object Resource {

}
