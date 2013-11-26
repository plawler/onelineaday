package views.dailies

import java.util.Date

/**
 * Created with IntelliJ IDEA.
 * User: paullawler
 * Date: 11/26/13
 * Time: 7:05 AM
 * To change this template use File | Settings | File Templates.
 */
case class DailyData(id: Long, projectId: Long, description: String, duration: Int, createdOn: Date)
