package controllers

import org.junit.runner.RunWith
import org.specs2.mutable._
import org.specs2.runner._

import play.api.test._

/**
 * Created with IntelliJ IDEA.
 * User: paullawler
 * Date: 11/16/13
 * Time: 10:03 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(classOf[JUnitRunner])
class ProjectsSpec extends Specification {

  "Projects" should {

    "default to the user's list of projects on the index page" in new WithBrowser {
      browser.goTo("http://localhost:" + port + "/projects")
      browser.$("h1").getText must equalTo("Projects")
    }

  }

}
