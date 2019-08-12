package misc

import scala.collection.mutable.ArrayBuffer

object MiscExperiments extends App {
  class Foo(x: String) {
    def ^^(pf: PartialFunction[String, Int]): Option[Int] =
      if (pf.isDefinedAt(x))
        Some(pf(x))
      else
        None
  }

  val foo = new Foo("bar")
  foo ^^ {
    case "baz" => 41
    case "bar" => 42
  } match {
    case Some(x) => println(x)
  }

  val arr1 = Array(1, 2, 3, 5, 9)
  val arr2 = Array(3, 2, 1, 6, 10)
  println("arr2.sorted: " + arr2.sorted.mkString(" "))
  val zi = arr1.zip(arr2).map(el =>
    if (el._1 > el._2) (1, 0)
    else if (el._1 == el._2) (0, 0)
    else (0, 1)
  ).foldLeft(Array(0, 0)) {
    (rslt: Array[Int], curr: (Int, Int)) =>
      if (curr._1 > curr._2) Array(rslt(0)+1, rslt(1))
      else if (curr._1 == curr._2) Array(rslt(0), rslt(1))
      else Array(rslt(0), rslt(1)+1)
  }
  println(zi.mkString(","))

  val len = arr1.size
  var arr3 = ArrayBuffer[Int]()
  val op = for (i <- len -1 to 0 by -1) {
    arr3 += arr1(i)
  }
  println(arr3.toArray.mkString(","))
}
