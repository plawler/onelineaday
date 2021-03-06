package models

import org.specs2.mutable._
import org.specs2.runner._
import org.specs2.specification._
import org.specs2.mock.Mockito
import org.junit.runner.RunWith
import utils.TestUtils
import securesocial.core.providers.UsernamePasswordProvider

/**
 * Created By: paullawler
 */
@RunWith(classOf[JUnitRunner])
class RepositorySpec extends Specification {

  "Repository" should {

    "create a repository" in TestUtils.memDB {
      val user = User.findByEmailAndProvider("paul.lawler@gmail.com", UsernamePasswordProvider.UsernamePassword) match {
        case Some(user: User) => user
      }
      Repository.create(user.id, 12544738, "practice", "plawler", "https://github.com/repos/plawler") should_!= None
    }

    "find a repository by name" in TestUtils.memDB {
      Repository.findByName("onelineaday") must not be None
    }

    "find all repos for a user" in TestUtils.memDB {
      val user = User.findByEmailAndProvider("paul.lawler@gmail.com", UsernamePasswordProvider.UsernamePassword) match {
        case Some(user: User) => user
      }
      Repository.findAll(user.id).size must beEqualTo(1)
    }

  }

}
