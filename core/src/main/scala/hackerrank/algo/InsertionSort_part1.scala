package hackerrank.algo

object InsertionSort_part1 extends App {

  // Complete the insertionSort1 function below.
  def insertionSort1(n: Int, arr: Array[Int]): Array[Int] = {
    val valIns = arr(n-1)
    for (i <- n-2 to 0 by -1) {
      val val_i = arr(i)
      if (val_i > valIns) {
        arr(i + 1) = val_i
        if (i == 0) {
          println(arr.mkString(" "))
          arr(i) = valIns
        }
        println(arr.mkString(" "))
      } else {
        arr(i + 1) = valIns
        println(arr.mkString(" "))
        return arr
      }
    }
    return arr
  }

  val arr = Array(2, 3, 4, 5 ,1)
  println(s"arr: ${arr.mkString(",")}")
  println(s"inserted: ${insertionSort1(arr.length, arr).mkString(",")}")
}
