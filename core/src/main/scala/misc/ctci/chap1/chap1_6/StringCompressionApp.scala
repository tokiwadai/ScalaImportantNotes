package misc.ctci.chap1.chap1_6

object StringCompressionApp extends App {
  def compress(string: String) = {
    val rslt = string.tail.foldLeft(Array[String](), string.head, 1) {
      (tuple, currChar) =>
        val (list, prevChar, count) = tuple
        if (prevChar == currChar) (list, currChar, count+1)
        else (list :+ s"$prevChar$count", currChar, 1)
    }

    (rslt._1 :+ s"${rslt._2}${rslt._3}").mkString
  }

  var string1 = "aaabbdccff"
  var stringR = compress(string1)
  println(s"$string1 - $stringR")
  assert(stringR == "a3b2d1c2f2")

  string1 = "abcfd"
  stringR = compress(string1)
  println(s"$string1 - $stringR")
  assert(stringR == "a1b1c1f1d1")
}
