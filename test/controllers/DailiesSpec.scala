package controllers

import org.specs2.mutable.Specification
import org.specs2.mock.Mockito
import play.api.test.Helpers._
import utils.TestUtils
import play.api.test.{FakeApplication, WithApplication, FakeRequest}
import play.api.http.Status

/**
 * Created By: paullawler
 */
class DailiesSpec extends Specification with Mockito {

  "Dailies controller" should {

    "fetch commits from Github for the Daily's date" in new WithApplication(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
      val daily = route(TestUtils.loggedInFakeRequestWrapper(
        FakeRequest(POST, "/dailies/54/commits")
          .withSession(("github_access_token", "8938b44b6c911e2ebcbd454cc2b1dc50d6c10b04"))
      ).withLoggedInUser()).get
      status(daily) must equalTo(Status.SEE_OTHER)
//      contentType(daily) must beSome.which(_ == "text/html")
//      contentAsString(home) must contain ("Your projects")
    }

  }


}
