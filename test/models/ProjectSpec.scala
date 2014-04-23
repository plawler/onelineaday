package models

import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner.RunWith

import org.joda.time.DateTime
import org.specs2.mock.Mockito
import utils.TestUtils
import securesocial.core.{IdentityId}
import java.util.Date


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
      val daily1 = mock[ProjectDaily]
      val daily2 = mock[ProjectDaily]
      val daily3 = mock[ProjectDaily]

      val today = DateTime.now
      daily1.completedOn returns Some(today.toDate)
      daily2.completedOn returns Some(today.minusDays(1).toDate)
      daily3.completedOn returns Some(today.minusDays(2).toDate)

      val streak = Project.calculateStreak(List(daily1, daily2, daily3), today.toDate, 0)
      streak must be equalTo 3
    }

    "compute a streak of non-consecutive dailies" in {
      val daily1 = mock[ProjectDaily]
      val daily2 = mock[ProjectDaily]
      val daily3 = mock[ProjectDaily]

      val today = DateTime.now
      daily1.completedOn returns Some(today.toDate)
      daily2.completedOn returns Some(today.minusDays(1).toDate)
      daily3.completedOn returns Some(today.minusDays(3).toDate)

      val streak = Project.calculateStreak(List(daily1, daily2, daily3), today.toDate, 0)
      streak must be equalTo 2
    }

    "compute a streak of multiple dailies on the same day" in {
      val daily1 = mock[ProjectDaily]
      val daily2 = mock[ProjectDaily]
      val daily3 = mock[ProjectDaily]

      val today = DateTime.now
      daily1.completedOn returns Some(today.toDate)
      daily2.completedOn returns Some(today.toDate)
      daily3.completedOn returns Some(today.minusDays(1).toDate)

      val streak = Project.calculateStreak(List(daily1, daily2, daily3), today.toDate, 0)
      streak must be equalTo 2
    }

    "compute a streak of None dailies" in {
      val daily1 = mock[ProjectDaily]
      val daily2 = mock[ProjectDaily]

      val today = DateTime.now
      daily1.completedOn returns Some(today.toDate)
      daily2.completedOn returns None

      val streak = Project.calculateStreak(List(daily1, daily2), today.toDate, 0)
      streak must be equalTo 1
    }

    "create a project assigned to a specific user" in TestUtils.memDB {
      val user = User.findByIdentityId(IdentityId("paullawler", "userpass")) match {
        case Some(u: User) => u
        case _ => throw new IllegalArgumentException("no user with that identity")
      }
      Project.all(user.id).size shouldEqual 2
      Project.create("A Test Project", "This is a test project", new Date(), user.id)
      Project.all(user.id).size shouldEqual 3
    }

    "retire a project" in TestUtils.memDB {
      val user = User.findByIdentityId(IdentityId("paullawler", "userpass")) match {
        case Some(u: User) => u
      }
      val id = Project.create("A Test Project", "This is a test project", new Date(), user.id)
      Project.retire(id)
      Project.find(id).retiredOn should not be None
    }

  }

}
