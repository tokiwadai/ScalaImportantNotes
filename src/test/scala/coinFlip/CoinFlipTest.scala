package coinFlip

import org.scalacheck.Gen
import org.scalacheck.Prop.forAll
import org.scalacheck.Prop.propBoolean

object CoinFlipTest extends App {
  val propFlip = forAll { (n: Int) =>
    val res = CoinFlipApp.flipCoin
    res == "H" || res == "T"
  }
  propFlip.check

  val input = Gen.oneOf("H", "T", "q", "h", "t", "", "123")
  val propWin = forAll(input) { (n: String) =>
    {
      val str = n.toUpperCase()
      val flipResult = CoinFlipApp.flipCoin
      val res = CoinFlipApp.win(str, flipResult)
//      println(s"input: $str, flipResult: $flipResult, flip: $res")
      if (str == flipResult)
        res == (true, true)
      else {
        if ((str == "H" || str == "T") && str != flipResult)
          res == (false, true)
        else
          res == (false, false)
      }
    }
  }
  propWin.check

}