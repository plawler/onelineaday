package models

import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

import play.api.test._
import play.api.test.Helpers._
import org.joda.time.DateTime
import org.specs2.mock.Mockito


/**
 * Created with IntelliJ IDEA.
 * User: paullawler
 * Date: 11/29/13
 * Time: 4:48 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(classOf[JUnitRunner])
class ProjectSpec extends Specification with Mockito {

//  https://code.google.com/p/specs/wiki/UsingMockito

  "Project model" should {
    "compute a streak of consecutive dailies" in {
      val daily1 = mock[Daily]
      val daily2 = mock[Daily]
      val daily3 = mock[Daily]

      val today = DateTime.now
      daily1.completedOn returns Some(today.toDate)
      daily2.completedOn returns Some(today.minusDays(1).toDate)
      daily3.completedOn returns Some(today.minusDays(2).toDate)

      val streak = Project.calculateStreak(List(daily1, daily2, daily3), today.toDate, 0)
      streak must be equalTo 2
    }

    "compute a streak of non-consecutive dailies" in {
      val daily1 = mock[Daily]
      val daily2 = mock[Daily]
      val daily3 = mock[Daily]

      val today = DateTime.now
      daily1.completedOn returns Some(today.toDate)
      daily2.completedOn returns Some(today.minusDays(1).toDate)
      daily3.completedOn returns Some(today.minusDays(3).toDate)

      val streak = Project.calculateStreak(List(daily1, daily2, daily3), today.toDate, 0)
      streak must be equalTo 1
    }

    "compute a streak of None dailies" in {
      val daily1 = mock[Daily]
      val daily2 = mock[Daily]

      val today = DateTime.now
      daily1.completedOn returns Some(today.toDate)
      daily2.completedOn returns None

      val streak = Project.calculateStreak(List(daily1, daily2), today.toDate, 0)
      streak must be equalTo 0
    }
  }

}
