package misc.ctci.chap3.chap3_5

import scala.collection.mutable.Stack

object SortStack extends App {
  def sort(s: Stack[Int]) = {
    val r = Stack[Int]()
    while (!s.isEmpty) {
      val tmp = s.pop
      while (!r.isEmpty && r.top > tmp) {
        s.push(r.pop)
      }
      r.push(tmp)
      println(s"r=[$r] --- s=[$s]")
    }

    while(!r.isEmpty) {
      s.push(r.pop)
    }
  }

  var stack = Stack[Int](8, 1, 5, 3, 2, 12, 10)
  stack = Stack[Int](8, 5, 3, 1)
  stack = Stack[Int](1, 3, 5, 8)
  println(s"stack=[$stack]")
  sort(stack)
  print(s"stack: $stack")
}
