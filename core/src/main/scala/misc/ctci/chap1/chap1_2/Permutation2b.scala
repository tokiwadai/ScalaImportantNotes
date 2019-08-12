package misc.ctci.chap1.chap1_2

// Not functional!!
object Permutation2b extends App {
  def checkPermutation(stringA: String, stringB: String): Boolean = {
    if (stringA.size != stringB.size) return false

    val letters = new Array[Int](128)
    val charsA = stringA.toCharArray
    for (i <- charsA)
      letters(i) += 1

    for (i <- 0 until stringB.length) {
      val c = stringB.charAt(i).toInt
      letters(c) -=1
      if (letters(c) < 0) return false
    }
    return true
  }

  var string1 = "abc"
  var string2 = "abc"
  println(s"$string1 vs $string2: ${checkPermutation(string1,string2)}")

  string1 = "abc"
  string2 = "cab"
  println(s"$string1 vs $string2: ${checkPermutation(string1,string2)}")

  string1 = "abce"
  string2 = "abcd"
  println(s"$string1 vs $string2: ${checkPermutation(string1,string2)}")

  string1 = "abce"
  string2 = "abcdf"
  println(s"$string1 vs $string2: ${checkPermutation(string1,string2)}")

  string1 = "aabce"
  string2 = "abbce"
  println(s"$string1 vs $string2: ${checkPermutation(string1,string2)}")
}
