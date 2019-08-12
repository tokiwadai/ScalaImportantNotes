package misc.prodigy

import scala.annotation.tailrec

object Prodigy extends App {
  val mylist: List[Char] = List('a', 'b', 'c')
  val myMpa = mylist.zipWithIndex.map { case (value, idx) => (idx -> value) }.toMap
  println(s"myMpa: $myMpa")


  val str = "abc"
  println(str.length)


  @tailrec
  def recurse(idx: Int, list: List[Char], acc: Map[Int, Char]): Map[Int, Char] = list match {
    case Nil => acc
    case x :: xs => recurse(idx + 1, xs, acc + (idx -> x))
  }
  val rslt1 = recurse(0, mylist, Map())
  println(s"rslt1: $rslt1")


  @tailrec
  def recurse2(idx: Int, list: List[Char], acc: List[(Int, Char)]): List[(Int, Char)] = list match {
    case Nil => acc
    case x :: xs => recurse2(idx + 1, xs, acc :+ (idx, x))
  }
  val rslt2 = recurse2(0, mylist, Nil).toMap
  println(s"rslt2: $rslt2")
}
