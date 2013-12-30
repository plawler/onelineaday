package models

import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

import play.api.test._
import play.api.test.Helpers._
import org.specs2.mock.Mockito
import securesocial.core._
import securesocial.core.IdentityId
import play.api.test.FakeApplication
import scala.Some


/**
 * Created with IntelliJ IDEA.
 * User: paullawler
 * Date: 12/17/13
 * Time: 9:33 AM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(classOf[JUnitRunner])
class UserSpec extends Specification with Mockito {
//  http://stackoverflow.com/questions/12421976/fixtures-in-play-2-for-scala/12445815#12445815

  implicit def memDB[T](code: => T) =
    running ( FakeApplication( additionalConfiguration = Map(
      "db.default.driver" -> "org.h2.Driver",
      "db.default.url"    -> "jdbc:h2:mem:test;MODE=PostgreSQL"
    ) ) )(code)

//  "My app" should {
//    "integrate nicely" in memDB {
//      1 mustEqual 1
//    }
//  }

  "User" should {
    "crud a user" in memDB {
      // create an Identity
      val identityId = IdentityId("unittestuser", "userpass")
      val authMethod = AuthenticationMethod("userPassword")
      val passwordInfo = PasswordInfo("bcrypt", "password", None)
      val identity = new SocialUser(identityId, "Test", "User", "Test User", Some("test@user.com"), avatarUrl = None,
                                    passwordInfo = Some(passwordInfo), authMethod = authMethod)

      // send to User.create(identity)
      User.create(identity)
      // test that user exists
      User.findByIdentityId(identityId) must not be None

      // new identity for our user
      val updatedIdentity = new SocialUser(identityId, "Bob", "User", "Bob User", Some("test@user.com"), avatarUrl = None,
        passwordInfo = Some(passwordInfo), authMethod = authMethod)
      // update
      User.update(updatedIdentity)
      // test that changes have stuck
      User.findByIdentityId(identityId).get.fullName mustEqual "Bob User"
    }
  }

}
