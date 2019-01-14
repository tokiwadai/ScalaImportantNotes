package coderust

object BinarySearch extends App {
  def binSearch(array: Array[Int], key: Int, start: Int, end: Int): Int = {
    if (array.length == 0) -1
    val mid = start + (end - start) / 2
    if (array(mid) == key) mid
    else if (array(mid) < key)
      binSearch(array, key, mid + 1, end)
    else binSearch(array, key, start, mid - 1)
  }


  def expr: Int = {
    val x = { print("x"); 1 }
    lazy val y = { print("y"); 2 }
    def z = { print("z"); 3 }
    z + y + x + z + y + x
  }
  (expr)
//  println("===")
//  val x1 = { println("x"); 1 }
//  lazy val y1 = { println("y"); 2 }
//  x1+y1

  val arr = Array(1, 2, 3, 4, 5, 6)
  val res = binSearch(arr, 4, 0 , arr.length)
  println(s"$res")
}