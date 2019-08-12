package misc

import scala.collection.immutable
import scala.collection.mutable

object Maps extends App {
  val a = immutable.Map("A" -> "Asli")
  //a("A") = "Palsu"    // Error!!
  val b = a + ("A" -> "Boss")
  println(s"a: $a")
  println(s"b: $b")

  val c = mutable.Map("C" -> "Chili")
  c("C") = "NO"

  println(s"c: $c")
  val d = c - "C"
  println(s"d: $d")
}
