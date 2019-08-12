package misc.prodigy

// WORKS!!
/*
You have a string aaabbdcccccf, transform it the following way => a3b2d1c5f1
ie: aabbaa -> a4b2
 */
object Prodigy2Merge_FL extends App {
  def getCharCountFoldLeft(str: String): String = {
    val rslt: (List[Char], Map[Char, Int]) =
      str.foldLeft[(List[Char], Map[Char, Int])](Nil, Map()) {
      (tuple: (List[Char], Map[Char, Int]), currChar: Char) =>
        val count = tuple._2.get(currChar).getOrElse(0)
        (if (count == 0) tuple._1 :+ currChar else tuple._1,
          tuple._2 + (currChar -> (count + 1)))
    }
    rslt._1.map(el => s"${el}${rslt._2.get(el).get}").mkString
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
  assert(string == "a4b2")

  string1 = "aabbaaa"
  string = getCharCountFoldLeft(string1)
  println(s"$string1 => $string")
  assert(string == "a5b2")

  string1 = "ccaabbaac"
  string = getCharCountFoldLeft(string1)
  println(s"$string1 => $string")
  assert(string == "c3a4b2")

  string1 = "caabbaaccc"
  string = getCharCountFoldLeft(string1)
  println(s"$string1 => $string")
  assert(string == "c4a4b2")
}
