package models

import org.specs2.runner._
import org.junit.runner.RunWith
import org.specs2.mutable._
import org.specs2.mock.Mockito
import utils.TestUtils

/**
 * Created with IntelliJ IDEA.
 * User: paullawler
 * Date: 12/31/13
 * Time: 10:24 AM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(classOf[JUnitRunner])
class DailySpec extends Specification with Mockito {

  "Daily" should {
    "return a page of dailies" in TestUtils.memDB {
      Daily.list(pageSize = 7, projectId = 1).items.size mustEqual 7
    }
  }

}
