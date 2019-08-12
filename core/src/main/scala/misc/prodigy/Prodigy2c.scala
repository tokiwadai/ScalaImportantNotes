package misc.prodigy

import scala.collection.mutable

object Prodigy2c extends App {
  def getCharCount(string: String) = {
    def compute(list: List[Char], accCount: mutable.HashMap[Char, Int],
                accOrder: List[(Char, Int)], prevChar: Char): List[(Char, Int)] =
      list match {
        case Nil => accOrder
        case x :: xs =>
          if (x == prevChar) {
            accCount.get(x) match {
              case None =>
                accCount.put(x, 1)
                compute(xs, accCount, accOrder :+ (x, 1), x)
              case Some(count) =>
                accCount.put(x, count + 1)
                compute(xs, accCount, accOrder, x)
            }
          } else {
            accCount.get(prevChar) match {
              case None =>
                accCount.get(x) match {
                  case None =>
                    accCount.put(x, 1)
                    compute(xs, accCount, accOrder, x)
                  case Some(count) =>
                    accCount.put(x, count + 1)
                    compute(xs, accCount, accOrder, x)
                }
              case Some(count) =>
                accCount.get(x) match {
                  case None =>
                    accCount.put(x, 1)
                    compute(xs, accCount, accOrder :+ (prevChar, count) :+ (x, 1), x)
                  case Some(count) =>
                    accCount.put(x, count + 1)
                    compute(xs, accCount, accOrder :+ (prevChar, count), x)
                }
            }
          }
      }

    val accOrder = compute(string.toList, mutable.HashMap(), Nil, '\0')
    //accOrder.map(x => s"$x${accCount.get(x).get}").mkString
    accOrder.map { case (c, count) => s"$c$count" }.mkString
  }

  var string1 = "aaabbdcccccf"
  var string = getCharCount(string1)
  println(s"$string1 => $string")
  //assert(string == "a3b2d1c5f1")

  string1 = "aabbaa"
  string = getCharCount(string1)
  println(s"$string1 => $string")
  assert(string == "a4b2")

  string1 = "ccaabbaac"
  string = getCharCount(string1)
  println(s"$string1 => $string")
  assert(string == "c3a4b2")
}
