package misc.ctci.chap1.chap1_1

// This is not functional programming approach!!
object IsUnique2 extends App {
  def isUnique(string: String): Boolean = {
    if (string.size>128) return false
    else {
      val chars = new Array[Boolean](128)
      for (i <- 0 until string.size) {
        val value: Char = string.charAt(i)
        if (chars(value)) return false
        else {
          chars(value) = true
        }
      }
    }
    return true
  }

  var word = "abcde"
  println(s"$word ==> ${isUnique(word)}")

  word = "abcdec"
  println(s"$word ==> ${isUnique(word)}")

}
