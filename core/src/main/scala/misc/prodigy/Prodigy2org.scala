package misc.prodigy

import scala.collection.immutable.TreeMap

object Prodigy2org extends App {
  def compute(string: String): String = {
    var map: Map[Char, Int] = TreeMap()
    def add(c: Char): Int =
      map.get(c) match {
        case None => {
          map = map.updated(c, 1)
          map.get(c).get
        }
        case Some(value) => {
          map = map.updated(c, value + 1)
          map.get(c).get
        }
      }
    string.map(add)
    map.map{case (a, b) => s"$a$b"}.mkString
  }
  var string = "aaabbdcccccf"
  println(s"string+count: ${compute(string)}")

  string = "ccaabbaa"
  println(s"string+count: ${compute(string)}")
}
