package myTests

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.specs2.mutable._

@RunWith(classOf[JUnitRunner])
object ArithmeticSpecTest extends Specification {
  "Arithmetic" should {
    "add two numbers" in {
      1 + 1 mustEqual 2
    }
    "add three numbers" in {
      1 + 1 + 1 mustEqual 3
    }
  }
}