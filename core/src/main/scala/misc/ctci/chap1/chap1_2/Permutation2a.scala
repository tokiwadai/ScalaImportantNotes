package misc.ctci.chap1.chap1_2

object Permutation2a extends App {
  def checkPermutation(stringA: String, stringB: String): Boolean = {
    def permute(listB: List[Char], letters: Array[Int], rslt: Boolean): Boolean = listB match {
      case Nil => rslt
      case x :: xs =>
        val c = x.toInt
        letters(c) -= 1
        if (letters(c) < 0) false
        else permute(xs, letters, true)
    }

    if (stringA.size != stringB.size) false
    else {
      val letters = new Array[Int](128)
      val charsA = stringA.toCharArray
      for (i <- charsA)
        letters(i.toInt) += 1

      permute(stringB.toList, letters, true)
    }
  }

  var string1 = "abc"
  var string2 = "abc"
  println(s"$string1 vs $string2: ${checkPermutation(string1, string2)}")

  string1 = "abc"
  string2 = "cab"
  println(s"$string1 vs $string2: ${checkPermutation(string1, string2)}")

  string1 = "abce"
  string2 = "abcd"
  println(s"$string1 vs $string2: ${checkPermutation(string1, string2)}")

  string1 = "abce"
  string2 = "abcdf"
  println(s"$string1 vs $string2: ${checkPermutation(string1, string2)}")
}
