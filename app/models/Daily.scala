package models

import java.sql.Timestamp
import java.util.Date

/**
 * Created with IntelliJ IDEA.
 * User: paullawler
 * Date: 11/26/13
 * Time: 12:38 AM
 * To change this template use File | Settings | File Templates.
 */
case class Daily(id: Long, projectId: Long, description: String, duration: Int, createdOn: Date)

object Daily {

}
