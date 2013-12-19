package models

import play.api.Play.current
import play.api.db._
import anorm._
import anorm.SqlParser._

import securesocial.core._
import securesocial.core.OAuth2Info
import securesocial.core.PasswordInfo
import securesocial.core.OAuth1Info

/**
 * Created with IntelliJ IDEA.
 * User: paullawler
 * Date: 12/16/13
 * Time: 9:00 PM
 * To change this template use File | Settings | File Templates.
 */

case class User(id: Long, firstName: String, lastName: String, fullName: String, email: Option[String],
                avatarUrl: Option[String], identityId: IdentityId, authMethod: AuthenticationMethod,
                passwordInfo: Option[PasswordInfo], oAuth1Info: Option[OAuth1Info],
                oAuth2Info: Option[OAuth2Info]) extends Identity

//http://blog.lunatech.com/2013/07/04/play-securesocial-slick
//https://markatta.com/codemonkey/blog/2012/06/16/parsing-results-with-anorm-in-play-framework-2/
//https://markatta.com/codemonkey/blog/2012/08/10/unparsing-with-anorm-in-play-framework-2/

object User {

  val identityParser = {
    get[String]("user_identity_id") ~
    get[String]("provider_id") map {
      case userIdentityId~providerId => IdentityId(userIdentityId, providerId)
    }
  }

  val authenticationMethodParser = {
    get[String]("authentication_method") map { case method => AuthenticationMethod(method)}
  }

  val passwordInfoParser = {
    get[String]("hasher") ~
      get[String]("password") ~
      get[Option[String]]("salt") map {
      case hasher~password~salt => PasswordInfo(hasher, password, salt)
    }
  }

  val oauth1Parser = {
    get[String]("token") ~
    get[String]("secret") map {
      case token~secret => OAuth1Info(token, secret)
    }
  }

  val oauth2parser = {
    get[String]("access_token") ~
    get[Option[String]]("token_type") ~
    get[Option[Int]]("expires_in") ~
    get[Option[String]]("refresh_token") map {
      case accessToken~tokenType~expiresIn~refreshToken => OAuth2Info(accessToken, tokenType, expiresIn, refreshToken)
    }
  }

  val userParser = {
    get[Long]("id") ~
    get[String]("first_name") ~
    get[String]("last_name") ~
    get[String]("full_name") ~
    get[Option[String]]("email") ~
    get[Option[String]]("avatar_url") ~
    identityParser ~
    authenticationMethodParser ~
    (passwordInfoParser?) ~
    (oauth1Parser?) ~
    (oauth2parser?) map {
      case id~firstName~lastName~fullName~email~avatarUrl~identityId~authenticationMethod~passwordInfo~oauth1Info~oauth2Info =>
        User(id, firstName, lastName, fullName, email, avatarUrl, identityId, authenticationMethod, passwordInfo, oauth1Info, oauth2Info)
    }
  }

}
