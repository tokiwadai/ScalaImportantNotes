package misc.ctci.chap10.an_intro

object QuickSortApp extends App {
  def quickSort(arr: Array[Int]): Array[Int] = {
    def quickSort(ins: Array[Int], left: Int, right: Int): Unit = {
      def swap(ar: Array[Int], l: Int, r: Int) = {
        val tmp = ar(l)
        ar(l) = ar(r)
        ar(r) = tmp
      }

      def partition(ints: Array[Int], left: Int, right: Int) = {
        val pivot = ints((left+right) /2)
        var lef = left
        var righ = right
        while (lef <= righ) {
          while (ints(lef) < pivot) lef += 1
          while (ints(righ) > pivot) righ -= 1
          if (lef <= righ) {
            swap(ints, lef, righ)
            lef += 1
            righ -= 1
            //println(s"${ints.mkString(" ")}")
          }
        }
        lef
      }
      val idx = partition(arr, left, right)
      if (left < idx -1)
        quickSort(arr, left, idx -1)
      if (idx < right)
        quickSort(arr, idx, right)
    }

    quickSort(arr, 0, arr.length -1)
    arr
  }

  var arr = Array(2, 3, 4, 5 ,1)
  println(s"arr: ${arr.mkString(",")}")
  println(s"quickSort: ${quickSort(arr).mkString(" ")}")
  arr = Array(4, 5, 3, 7, 2)
  println(s"arr: ${arr.mkString(",")}")
  println(s"quickSort: ${quickSort(arr).mkString(" ")}")
}
