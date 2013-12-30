package utils

import play.api.test.FakeRequest
import securesocial.core._
import models.User
import scala.Some
import securesocial.core.IdentityId
import securesocial.core.PasswordInfo
import scala.Some

/**
 * Created with IntelliJ IDEA.
 * User: paullawler
 * Date: 12/29/13
 * Time: 10:40 PM
 * To change this template use File | Settings | File Templates.
 */
object TestUtils {

  @inline implicit def loggedInFakeRequestWrapper[T](x: FakeRequest[T]) = new LoggedInFakeRequest(x)

  final class LoggedInFakeRequest[T](val self: FakeRequest[T]) extends AnyVal {
    def withLoggedInUser() = {
      val userToLogInAs:Identity = getUser //get this from your database using whatever you have in Global
      val cookie = Authenticator.create(userToLogInAs) match {
          case Right(authenticator) => authenticator.toCookie
        }
      self.withCookies(cookie)
    }
  }

  private def getUser:Identity = {
    User.findByIdentityId(IdentityId("testuser", "userpass")) match {
      case None => {
        val authMethod = AuthenticationMethod("userPassword")
        val passwordInfo = PasswordInfo("bcrypt", "password", None)
        new User(1, "Test", "User", "Test User", Some("test@user.com"), avatarUrl = None, passwordInfo = Some(passwordInfo),
          authMethod = authMethod, identityId = IdentityId("testuser", "userpass"), oAuth1Info = None, oAuth2Info = None)
      }
      case Some(x) => x
    }
  }

}
