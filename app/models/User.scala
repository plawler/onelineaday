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
    get[String]("identity_id") ~
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

  def create(identity: Identity) = DB.withConnection { implicit conn =>
    SQL(
      """
      insert into users (first_name, last_name, full_name, email, identity_id, provider_id, authentication_method, hasher, password, salt)
      values ({firstName}, {lastName}, {fullName}, {email}, {userIdentityId}, {providerId}, {authenticationMethod}, {hasher}, {password}, {salt})
      """
    ).on('firstName -> identity.firstName, 'lastName -> identity.lastName, 'fullName -> identity.fullName, 'email -> identity.email,
          'userIdentityId -> identity.identityId.userId, 'providerId -> identity.identityId.providerId,
          'authenticationMethod -> identity.authMethod.method, 'hasher -> identity.passwordInfo.get.hasher,
          'password -> identity.passwordInfo.get.password, 'salt -> identity.passwordInfo.get.salt
    ).execute()
  }

  def update(identity: Identity) = DB.withConnection { implicit conn =>
    SQL(
      """
      update users
      set first_name = {firstName}, last_name = {lastName}, full_name = {fullName}, email = {email}, hasher = {hasher}, password = {password}, salt = {salt}
      where identity_id = {identityId} and provider_id = {providerId}
      """
    ).on('firstName -> identity.firstName, 'lastName -> identity.lastName, 'fullName -> identity.fullName, 'email -> identity.email,
      'hasher -> identity.passwordInfo.get.hasher, 'password -> identity.passwordInfo.get.password, 'salt -> identity.passwordInfo.get.salt,
      'identityId -> identity.identityId.userId, 'providerId -> identity.identityId.providerId
    ).executeUpdate()
  }

  def findByIdentityId(identityId: IdentityId): Option[Identity] = DB.withConnection { implicit conn =>
    SQL(
    """
    select * from users where identity_id = {identityId} and provider_id = {providerId}
    """
    ).on('identityId -> identityId.userId, 'providerId -> identityId.providerId).as(User.userParser.singleOpt)
  }

  def findByEmailAndProvider(emailAddress: String, provider: String): Option[Identity] = DB.withConnection { implicit conn =>
    SQL(
      """
      select * from users where email = {email} and provider_id = {provider}
      """
    ).on('email -> emailAddress, 'provider -> provider).as(User.userParser.singleOpt)
  }

}
