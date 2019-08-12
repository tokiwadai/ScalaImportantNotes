package misc.prodigy

// WORKS!!
// Not using mutable.HashMap
object Prodigy2f extends App {
  def getCharCount(string: String) = {
    def compute(list: List[Char], acc: List[String], prevChar: Char, idx: Int, count: Int): List[String] =
      list match {
        case Nil => acc
        case x :: xs =>
          if (x == prevChar)
            if (xs == Nil)
              // the last member in the list
              compute(xs, acc :+ s"${x}${count+1}", x, idx, 0)
            else
              compute(xs, acc, x, idx, count + 1)
          else
            if (prevChar == '\0')
              // the first member in the list
              compute(xs, acc, x, idx, 1)
            else if (xs == Nil)
              // the last member in the list
              compute(xs, acc :+ s"$prevChar$count" :+ s"${x}1", x, idx + 1, 0)
            else
              compute(xs, acc :+ s"$prevChar$count", x, idx + 1, 1)
      }

    val accOrder = compute(string.toList, Nil, '\0', 0, 0)
    accOrder.mkString
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
