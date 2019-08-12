package misc.prodigy

import scala.collection.mutable

// WORKS!!
object Prodigy2 extends App {
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
                if (xs != Nil)
                  compute(xs, accCount, accOrder, x)
                else
                  compute(xs, accCount, accOrder :+ (x, 1), x)
              case Some(count) =>
                accCount.put(x, count + 1)
                if (xs != Nil)
                  compute(xs, accCount, accOrder, x)
                else
                  compute(xs, accCount, accOrder :+ (x, count + 1), x)
            }
          } else {
            accCount.get(prevChar) match {
              case None =>
                accCount.get(x) match {
                  case None =>
                    accCount.put(x, 1)
                    if (xs != Nil)
                      compute(xs, accCount, accOrder, x)
                    else
                      compute(xs, accCount, accOrder :+ (x, 1), x)
                  case _ =>
                    accCount.put(x, 1) // reset
                    compute(xs, accCount, accOrder, x)
                }
              case Some(prevCount) =>
                accCount.get(x) match {
                  case None =>
                    accCount.put(x, 1)
                    if (xs != Nil)
                      compute(xs, accCount, accOrder :+ (prevChar, prevCount), x)
                    else
                      compute(xs, accCount, accOrder :+ (prevChar, prevCount) :+ (x, 1), x)
                  case Some(_) =>
                    accCount.put(x, 1) // reset
                    if (xs != Nil)
                      compute(xs, accCount, accOrder :+ (prevChar, prevCount), x)
                    else
                      compute(xs, accCount, accOrder :+ (prevChar, prevCount) :+ (x, 1), x)
                }
            }
          }
      }

    val accOrder = compute(string.toList, mutable.HashMap(), Nil, '\0')
    accOrder.map { case (c, count) => s"$c$count" }.mkString
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
