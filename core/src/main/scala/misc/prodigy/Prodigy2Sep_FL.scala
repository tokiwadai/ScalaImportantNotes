package misc.prodigy

// WORKS!!
/*
You have a string aaabbdcccccf, transform it the following way => a3b2d1c5f1
ie: aabbaa -> a2b2a2 not a4b2

 */
object Prodigy2Sep_FL extends App {
  def getCharCountFoldLeft(str: String) = {
    val rslt: (List[String], Char, Int) = str.tail
      .foldLeft[(List[String], Char, Int)]((Nil, str.head, 1)) {
      (tuple:(List[String], Char, Int), currChar: Char) =>
        val (res: List[String], prevChar, count) = tuple
        if (currChar == prevChar) // increment the count
          (res, currChar, count+1)
        else  // append the result and reset the count
          (res :+ s"${prevChar}${count}", currChar, 1)
    }
    val list = rslt._1 :+ s"${rslt._2}${rslt._3}" // append the last member
    list.mkString
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
