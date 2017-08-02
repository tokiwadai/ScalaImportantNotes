package interviewcake.productofOtherNumbers

object ProductOfOtherNumbers {
  def pirn1[T](str: String, arr: Array[T]) = {
    print(s"$str: ")
    arr.foreach(i => print(i + " "))
    println
  }

  def recBack(array: List[Int], acc: Array[Int], prodSoFar: Int, idx: Int): Array[Int] = array match {
    case Nil => acc
    case x :: xs => {
      // [42, 14, 2, 1]
      println(s"acc(idx): ${acc(idx)}, prodSoFar: $prodSoFar, acc(idx): ${acc(idx)} * ${prodSoFar}")
      acc(idx) = acc(idx) * prodSoFar
      val t = prodSoFar * array(0)
      recBack(array.tail, acc, t, idx-1)
    }
  }

  def rec(array: List[Int], acc: Array[Int], prodSoFar: Int, idx: Int): Array[Int] = array match {
    case Nil => acc
    case x :: xs => {
      acc(idx) = prodSoFar
      println(s"prodSoFar: $prodSoFar")
      val t = prodSoFar * array(0)
      println(s"acc(idx): ${acc(idx)}, prodSoFar: $prodSoFar, new prodSoFar: $t")
      rec(array.tail, acc, t, idx+1)
    }
  }

  def work(array: Array[Int]): Array[Int] = {
    //(2, 7, 3, 4)
    val array1 = Array.fill(array.length)(0)
    rec(array.toList, array1, 1, 0)
    pirn1("array1: ", array1)
    //[1, 2, 14, 42]

    recBack(array.toList.reverse, array1, 1, array.length - 1)
    pirn1("array2: ", array1)
    // [84, 24, 56, 42]
    array1
  }

  def main(strings: Array[String]) = {
    val arr = Array(2, 7, 3, 4)
    val res = work(arr)
    pirn1("result: ", res)
  }
}