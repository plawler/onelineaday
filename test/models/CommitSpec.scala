package models


import org.specs2.runner._
import org.specs2.mutable._
import org.specs2.runner.JUnitRunner
import org.junit.runner.RunWith
import utils.TestUtils

/**
 * Created By: paullawler
 */
@RunWith(classOf[JUnitRunner])
class CommitSpec extends Specification {

  "Commit" should {

    "CRUD a commit" in TestUtils.memDB {
      Commit.create(10, "fadioulksjadfij309234908923490jl", "plawler", "plawler",
        "https://github.com/commit/fadioulksjadfij309234908923490jl", "this is a test commit") should_!= None
    }

  }

}
