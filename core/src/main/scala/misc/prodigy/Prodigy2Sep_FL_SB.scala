package misc.prodigy

// WORKS!!
/*
You have a string aaabbdcccccf, transform it the following way => a3b2d1c5f1
ie: aabbaa -> a2b2a2 not a4b2

 */
object Prodigy2Sep_FL_SB extends App {
  def getCharCountFoldLeft(str: String) = {
    val rslt: (StringBuilder, Char, Int) = str.tail
      .foldLeft[(StringBuilder, Char, Int)]((new StringBuilder, str.head, 1)) {
      (tuple:(StringBuilder, Char, Int), currChar: Char) =>
        val (res: StringBuilder, prevChar, count) = tuple
        if (currChar == prevChar) // increment the count
          (res, currChar, count+1)
        else  // append the result and reset the count
          (res.append(prevChar).append(count), currChar, 1)
    }
    rslt._1.append(rslt._2).append(rslt._3).toString // append the last member
  }

  var string1 = "aaabbdcccccff"
  var string = getCharCountFoldLeft(string1)
  println(s"$string1 => $string")
  assert(string == "a3b2d1c5f2")

  string1 = "aaabbdcccccf"
  string = getCharCountFoldLeft(string1)
  println(s"$string1 => $string")
  assert(string == "a3b2d1c5f1")

  string1 = "aabbaa"
  string = getCharCountFoldLeft(string1)
  println(s"$string1 => $string")
  assert(string == "a2b2a2")

  string1 = "aabbaaa"
  string = getCharCountFoldLeft(string1)
  println(s"$string1 => $string")
  assert(string == "a2b2a3")

  string1 = "ccaabbaac"
  string = getCharCountFoldLeft(string1)
  println(s"$string1 => $string")
  assert(string == "c2a2b2a2c1")

  string1 = "caabbaaccc"
  string = getCharCountFoldLeft(string1)
  println(s"$string1 => $string")
  assert(string == "c1a2b2a2c3")
}
