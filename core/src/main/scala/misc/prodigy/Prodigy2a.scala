package misc.prodigy

import scala.collection.mutable

object Prodigy2a extends App {
  def compute(string: String): String = {
    val map = mutable.HashMap[Char, Int]()
    def add(c: Char): Int =
      map.get(c) match {
        case None => map.put(c, 1).getOrElse(1)
        case Some(value) => map.put(c, (value + 1)).get
      }
    string.map(add)
    map.toList.sortBy(_._1).map{case (a, b) => s"$a$b"}.mkString
  }

  var string = "aaabbdcccccf"
  println(s"string_with_count: ${compute(string)}")
  assert(compute(string) == "a3b2d1c5f1")

  string = "aabbaa"
  println(s"string_with_count: ${compute(string)}")
}
