package controllers

import org.junit.runner.RunWith
import org.specs2.mutable._
import org.specs2.runner._

import play.api.test._
import org.junit.Ignore
import play.api.test.Helpers._
import utils.TestUtils

/**
 * Created with IntelliJ IDEA.
 * User: paullawler
 * Date: 11/16/13
 * Time: 10:03 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(classOf[JUnitRunner])
@Ignore
class ProjectsSpec extends Specification {

  "Projects" should {

//    "default to the user's list of list on the index page" in new WithBrowser {
//      browser.goTo("http://localhost:" + port + "/")
//      browser.$("h1").getText must equalTo("Your projects")
//    }

    "retire a project" in new WithApplication {
      val retired = route(TestUtils.loggedInFakeRequestWrapper(FakeRequest(POST, "/projects/1/retire")).withLoggedInUser()).get
      status(retired) must equalTo(SEE_OTHER)
    }

  }



}
