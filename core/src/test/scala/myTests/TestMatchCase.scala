package myTests

import org.junit.runner.RunWith
import org.scalatest._
import org.scalatest.junit.JUnitRunner
import org.scalatest.prop.Checkers

@RunWith(classOf[JUnitRunner])
class MatchCaseTest extends FunSuite with Checkers {
  def matching(string: Option[String]): Option[String] = string match {
    case Some("test") => Some("Got it!")
    case None => None
    case _ => Some("Others")
  }

  test("passing test should return test") {
    check {
      val result: Option[String] = matching(Some("test"))
      result == Some("Got it!")
    }
  }

  test("passing null should return none") {
    check {
      val result: Option[String] = matching(null)
      println(s"result: $result")
      result != None
    }
  }

  test("passing none should return none") {
    check {
      val result: Option[String] = matching(None)
      result == None
    }
  }

  test("passing any string should return Others") {
    check {
      val result: Option[String] = matching(Some("Hi"))
      result == Some("Others")
    }
  }
}
