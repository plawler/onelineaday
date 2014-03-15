package controllers

import play.api.test.{FakeRequest, WithApplication}

import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._
import play.api.test.Helpers._

/**
 * Created with IntelliJ IDEA.
 * User: paullawler
 * Date: 2/23/14
 * Time: 10:15 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(classOf[JUnitRunner])
class RepositoriesSpec extends Specification {

  "Repositories" should {

    "acquire a Github token" in new WithApplication {
      route(FakeRequest(GET, "/github/callback?code=123456")) must not beNone
    }

  }

}
