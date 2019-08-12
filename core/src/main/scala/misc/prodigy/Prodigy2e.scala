package misc.prodigy

import scala.collection.mutable

// WORKS!!
object Prodigy2e extends App {
  def getCharCount(string: String) = {
    def compute(list: List[Char], accCount: mutable.HashMap[Int, String], prevChar: Char,
                idx: Int, count: Int): mutable.HashMap[Int, String] =
      list match {
        case Nil => accCount
        case x :: xs =>
          if (x == prevChar) {
            accCount.put(idx, s"$x${count+1}")
            compute(xs, accCount, x, idx, count + 1)
          } else { // if the current char is Not the same as the previous one
            accCount.put(idx+1, s"${x}1")
            // reset count
            compute(xs, accCount, x, idx + 1, 1)
          }
      }

    val accOrder = compute(string.toList, mutable.HashMap(), '\0', 0, 0)
    accOrder.toList.sortBy(_._1).map { case (_, count) => s"$count" }.mkString
  }

  var string1 = "aaabbdcccccf"
  var string = getCharCount(string1)
  println(s"$string1 => $string")
  assert(string == "a3b2d1c5f1")

  string1 = "aabbaa"
  string = getCharCount(string1)
  println(s"$string1 => $string")
  assert(string == "a2b2a2")

  string1 = "aabbaaa"
  string = getCharCount(string1)
  println(s"$string1 => $string")
  assert(string == "a2b2a3")

  string1 = "ccaabbaac"
  string = getCharCount(string1)
  println(s"$string1 => $string")
  assert(string == "c2a2b2a2c1")

  string1 = "caabbaaccc"
  string = getCharCount(string1)
  println(s"$string1 => $string")
  assert(string == "c1a2b2a2c3")
}
