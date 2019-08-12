package misc.ctci

object CountCharOccurences extends App {
  val str = "aaaccbbdcccccfffa"

  val countArray = new Array[Int](255)
  val strLen = str.size

//  for (i <- 0 until strLen)
//    countArray(str.charAt(i)) += 1
  str.foreach{ ch =>
    //println(s"ch: $ch")
    countArray(ch) += 1
  }
  println(s"count[a] ${countArray(97)}")

  var list: List[String] = Nil
  val chars = new Array[Char](strLen)
  for (i <- 0 until strLen) {
    chars(i) = str.charAt(i)
    var find = 0
    for (j  <- 0 until i)
      if (str.charAt(i) == chars(j)) {
        //println("i: " + i + "-" + str.charAt(i) + ", j:" + j  + "-" + chars(j))
        find += 1
      }

    if (find == 1) {
      list = list :+ s"${str.charAt(i)}${countArray(str.charAt(i))}"
      println(s"Number of Occurrence of ${str.charAt(i)}"
        + s" letter is:  ${countArray(str.charAt(i))}")
    }
  }
  print(s"result: ${list.mkString("")}")
}
