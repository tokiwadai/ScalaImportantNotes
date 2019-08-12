package misc.ctci.chap1.chap1_9

object StringRotationApp extends App {
  def isRotation(s1: String, s2: String): Boolean = {
    def isSubString(big: String, small: String) =
      big.indexOf(small) > 0

    if (s1.size != s2.size) false
    else if (s1.size == s2.size && s1.size > 0)
      isSubString(s"$s1$s1", s2)
    else false
  }

  var string1 = "waterbottle"
  var string2 = "terbottlewa"
  println(s"$string1 - $string2")
  assert(isRotation(string1, string2) == true)

  string1 = ""
  string2 = ""
  println(s"$string1 - $string2")
  assert(isRotation(string1, string2) == false)

  string1 = ""
  string2 = "terbottleaw"
  println(s"$string1 - $string2")
  assert(isRotation(string1, string2) == false)

  string1 = "waterbottle"
  string2 = "terbottleaw"
  println(s"$string1 - $string2")
  assert(isRotation(string1, string2) == false)
}
