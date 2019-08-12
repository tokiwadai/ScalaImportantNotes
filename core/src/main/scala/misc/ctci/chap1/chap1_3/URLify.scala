package misc.ctci.chap1.chap1_3

object URLify extends App {
  def replaceSpaces(chars: Array[Char], trueLen: Int) = {
    var spaceCount, index = 0
    for (i <- 0 until trueLen)
      if (chars(i) == ' ')
        spaceCount += 1

    index = trueLen + spaceCount * 2
    if (trueLen < chars.size) chars(trueLen) = '\0'
    for (i <- trueLen-1 to 0 by -1)
      if (chars(i) == ' ') {
        chars(index - 1) = '0'
        chars(index - 2) = '2'
        chars(index - 3) = '%'
        index -= 3
      } else {
        chars(index - 1) = chars(i)
        index -= 1
      }
  }

  var string = "Mr John Smith    "
  println(s"before: [${string}]" +
    s"-[${string.trim.length}]-[${string.length}]")
  val chars = string.toCharArray
  println(s"chars.len [${chars.length}]")
  replaceSpaces(chars, string.trim.length)
  println(s"after: [${chars.mkString}]-[${chars.length}]")
}