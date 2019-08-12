package misc.prodigy

import scala.collection.mutable

object Prodigy2b extends App {
  def getCharCount(string: String) = {
    def compute(list: List[Char], accCount: mutable.HashMap[Char, Int],
                accOrder: List[Char]): (mutable.HashMap[Char, Int], List[Char]) =
      list match {
        case Nil => (accCount, accOrder)
        case x :: xs =>
          accCount.get(x) match {
            case None =>
              accCount.put(x, 1)
              compute(xs, accCount, accOrder :+ x)
            case Some(count) =>
              accCount.put(x, count + 1)
              compute(xs, accCount, accOrder)
          }
      }
    val (accCount, accOrder) = compute(string.toList, mutable.HashMap(), Nil)
    accOrder.map(x => s"$x${accCount.get(x).get}").mkString
  }

  var string1 = "aaabbdcccccf"
  var string = getCharCount(string1)
  println(s"$string1 => $string")
  assert(string == "a3b2d1c5f1")

  string1 = "aabbaa"
  string = getCharCount(string1)
  println(s"$string1 => $string")
  assert(string == "a4b2")

  string1 = "ccaabbaac"
  string = getCharCount(string1)
  println(s"$string1 => $string")
  assert(string == "c3a4b2")
}
