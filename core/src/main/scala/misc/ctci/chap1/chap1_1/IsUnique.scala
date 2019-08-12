package misc.ctci.chap1.chap1_1

import scala.annotation.tailrec

object IsUnique extends App {
  def isUnique(string: String): Boolean = {
    @tailrec
    def checkUnique(list: List[Char], chars: Array[Boolean], result: Boolean): Boolean =
      list match {
        case Nil => result
        case x :: xs =>
          println(s"x: $x")
          val value = x.toInt
          if (chars(value)) false
          else {
            chars(value) = true
            checkUnique(xs, chars, true)
          }
      }

    if (string.size > 128) false
    else
      checkUnique(string.toList, new Array[Boolean](128), true)
  }

  var word = "abcde"
  println(s"$word ==> ${isUnique(word)}")

  word = "abcdec"
  println(s"$word ==> ${isUnique(word)}")

  word = "abcdcefg"
  println(s"$word ==> ${isUnique(word)}")
}
