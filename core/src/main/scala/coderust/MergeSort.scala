package coderust

import scala.annotation.tailrec

object MergeSort {
  def pirn1[T](str: String, arr: Array[T]) = {
    print(s"$str: ")
    arr.foreach(i => print(i + " "))
    println
  }
  def pirn[T](str: String, arr: Array[T]) = {
  }

  case class MergeSorter(arrOrg: Array[Int]) {
    private val arrCopy: Array[Int] = Array.fill(arrOrg.size)(0)

    def sort() = {
      pirn("arrCopy", arrCopy)
      mergeSort(0, arrOrg.size - 1)
    }

    def mergeSort(low: Int, high: Int): Unit = {
      if (low < high) {
        val mid = low + (high - low) / 2
        //        println(s"*******1 low: $low, mid: $mid, high: $high")
        mergeSort(low, mid)
        //        println(s"*******2 low: $low, mid: $mid, high: $high")
        mergeSort(mid + 1, high)
        //        println(s"*******3 low: $low, mid: $mid, high: $high")
        merge(low, mid, high)
      }
    }

    def merge(low: Int, mid: Int, high: Int) = {
      for (idx <- low to high) arrCopy(idx) = arrOrg(idx)
      pirn("===== arrCopy", arrCopy)

      val i = low
      val j = mid + 1
      val k = low
      val (x, y) = reassign(i, j, k, mid, high)
      pirn("I arrOrg", arrOrg)

      reassign2(x, y, mid)
      pirn("II arrOrg", arrOrg)
    }

    @tailrec
    private def reassign(i: Int, j: Int, k: Int, mid: Int, high: Int): (Int, Int) = {
      if (i <= mid && j <= high) {
        if (arrCopy(i) <= arrCopy(j)) {
          arrOrg(k) = arrCopy(i)
          // println(s"reassign== i: $i, j: $j, k: $k, mid: $mid, high: $high, arrOrg($k): ${arrOrg(k)}, arrCopy($i): ${arrCopy(i)}")
          reassign(i + 1, j, k + 1, mid, high)
        } else {
          arrOrg(k) = arrCopy(j)
          // println(s"reassign== i: $i, j: $j, k: $k, mid: $mid, high: $high, arrOrg($k): ${arrOrg(k)}, arrCopy($j): ${arrCopy(j)}")
          reassign(i, j + 1, k + 1, mid, high)
        }
      } else
        (i, k)
    }

    @tailrec
    private def reassign2(i: Int, k: Int, mid: Int): Unit = {
      if (i <= mid) {
        arrOrg(k) = arrCopy(i)
        // println(s"reassign2== i: $i, k: $k, mid: $mid, arrOrg($k): ${arrOrg(k)}, arrCopy($i): ${arrCopy(i)}")
        reassign2(i + 1, k + 1, mid)
      }
    }

  }

  def main(args: Array[String]): Unit = {
    val arr = Array(6, 4, 7, 2, 9, 1, 0)
    pirn1("arr: ", arr)

    val sorter: MergeSorter = MergeSorter(arr)
    sorter.sort()
    pirn1("result: ", arr)
  }
}