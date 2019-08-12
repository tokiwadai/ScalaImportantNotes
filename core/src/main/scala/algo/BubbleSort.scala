package algo

import scala.annotation.tailrec

object BubbleSort extends App {
  @tailrec
  def iter2(arr: List[Int], acc: List[Int]): List[Int] = arr match {
    case Nil => acc
    case x :: xs =>
      println(s"xs2: $xs")
      val accum = if (xs != Nil && xs.head > x) {
        acc :+ xs.head :+ x
      } else
        acc :+ x
      iter2(xs, accum)
  }

  @tailrec
  def iter1(arr: List[Int], acc: List[Int]): List[Int] =  arr match {
      case Nil => acc
      case _ :: xs =>
        println(s"xs: $xs")
        iter1(xs, iter2(xs, acc))
  }

//  def iter(arr: List[Int], acc: List[Int]): List[Int] =  {
//    val acc2 = if (acc == Nil) arr.head :: acc
//    else
//      arr match {
//        case Nil => acc
//        case _ :: xs =>
//          println(s"xs: $xs")
//          iter1(xs, acc)
//      }
//  }

  @tailrec
  def iter3(arr: List[Int], acc: List[Int]): List[Int] = arr match {
      case Nil => acc
      case x :: xs =>
        println(s"xs: $xs")
        acc match {
          case Nil => iter3(xs, iter2(xs, x :: acc))
          case _ =>
            iter3(xs, iter2(arr, acc))
        }
    }


  val arr = List(6, 5, 4, 3, 2, 1)
  val len = arr.length

  val rslt = iter3(arr, Nil)
  println(s"rslt: $rslt")
}
