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

    "create and read a commit" in TestUtils.memDB {
      val id = Commit.create(1, 54, "fadioulksjadfij309234908923490jl", "plawler", "plawler",
        "https://github.com/commit/fadioulksjadfij309234908923490jl", "this is a test commit")

      id should_!= None

      val commit = Commit.findById(id)
      commit should_!= None
    }

    "retrieve a daily's commits" in TestUtils.memDB {
      val commits = Commit.findByDailyId(54)
      commits.size shouldEqual 1
    }

    "find a commit by sha value" in TestUtils.memDB {
      Commit.create(1, 54, "fadioulksjadfij309234908923490jl", "plawler", "plawler",
        "https://github.com/commit/fadioulksjadfij309234908923490jl", "this is a test commit")
      Commit.findBySha("fadioulksjadfij309234908923490jl") shouldNotEqual(None)
      Commit.findBySha("thisisnotavalidsha") shouldEqual(None)
    }

  }

}
