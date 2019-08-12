package coderust

import scala.annotation.tailrec

object BinarySearch extends App {
  @tailrec
  def binSearchRec(array: Array[Int], key: Int, start: Int, end: Int): Int = {
    if (array.length == 0) -1
    val mid = start + (end - start) / 2
    if (array(mid) == key) mid
    else if (array(mid) < key)
      binSearchRec(array, key, mid + 1, end)
    else binSearchRec(array, key, start, mid - 1)
  }

  def expr: Int = {
    val x: Int = { print("x"); 1 }
    lazy val y: Int = { print("y"); 2 }
    def z = { print("z"); 3 }
    z + y + x + z + y + x
  }
  expr
  println
  println(expr)
//  println("===")
//  val x1 = { println("x"); 1 }
//  lazy val y1 = { println("y"); 2 }
//  x1+y1

  val arr = Array(1, 2, 3, 4, 5, 6)
  val res = binSearchRec(arr, 4, 0 , arr.length)
  println
  println(s"res=$res")
}