package hackerrank.algo

/*
  BUBBLE SORT
 */
object InsertionSort_part2 extends App {
  def insertionSort2Count(n: Int, arr: Array[Int]): Int = {
    var count = 0
    for(i <- 0 until n-1) {
      for (j <- 0 until n-i-1) {
        if (arr(j) > arr(j+1)) {
          val arr_jp1 = arr(j+1)
          arr(j+1) = arr(j)
          arr(j) = arr_jp1
          println(arr.mkString(" "))
          count +=1
        }
      }
    }
    count
  }

  // Complete the insertionSort1 function below.
  def insertionSort2(n: Int, arr: Array[Int]): Array[Int] = {
    for(i <- 0 until n-1) {
      for (j <- 0 until n-i-1) {
        if (arr(j) > arr(j+1)) {
          val arr_jp1 = arr(j+1)
          arr(j+1) = arr(j)
          arr(j) = arr_jp1
          println(arr.mkString(" "))
        }
      }
    }
    arr
  }

  def bubbleSort(n: Int, arr: Array[Int]): Array[Int] = {
    for(i <- 0 until n) {
      for (j <- 1 to n if j < n-i) {
        if (arr(j-1) > arr(j)) {
          val arr_jm1 = arr(j-1)
          arr(j-1) = arr(j)
          arr(j) = arr_jm1
          println(arr.mkString(" "))
        }
      }
    }
    arr
  }


  var arr = Array(2, 3, 4, 5 ,1)
  arr = Array(1, 4, 3, 5, 6, 2)
  println(s"arr: ${arr.mkString(",")}")
  println(s"bubbleSort: ${bubbleSort(arr.length, arr).mkString(" ")}")
  arr = Array(1, 4, 3, 5, 6, 2)
  println(s"arr: ${arr.mkString(",")}")
  arr = Array(2, 1, 3, 1, 2)
  println(s"insertionSort2: ${insertionSort2Count(arr.length, arr)}")
}
