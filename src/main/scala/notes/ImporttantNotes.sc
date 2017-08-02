package notes

import org.scalatest.Assertions._

object ImportantNotes {
  val list: List[Double] = List(400, 30, 2, 1)    //> list  : List[Double] = List(400.0, 30.0, 2.0, 1.0)

  // LEFT
  def divLeft(xs: List[Double], acc: Double): Double = xs match {
    case Nil     => acc
    case x :: xs => divLeft(xs, acc / x)
  }                                               //> divLeft: (xs: List[Double], acc: Double)Double
  val divLeftRes: Double = divLeft(list, 100000)  //> divLeftRes  : Double = 4.166666666666667

  val tLeft: Double = ((((100000.0 / 400) / 30) / 2) / 1)
                                                  //> tLeft  : Double = 4.166666666666667
  val reduceLeft: Double = (100000.0 :: list) reduceLeft ((x, y) => x / y)
                                                  //> reduceLeft  : Double = 4.166666666666667
  val foldLeft: Double = (list foldLeft 100000.0)(_ / _)
                                                  //> foldLeft  : Double = 4.166666666666667
  assert((divLeftRes == tLeft) == (reduceLeft == foldLeft))
                                                  //> res0: org.scalatest.Assertion = Succeeded

  // RIGHT
  def divRight(xs: List[Double], acc: Double): Double = xs match {
    case Nil     => acc
    case x :: xs => x / divRight(xs, acc)
  }                                               //> divRight: (xs: List[Double], acc: Double)Double
  val divRightRes = divRight(list, 100000)        //> divRightRes  : Double = 2666666.6666666665

  val tRight: Double = (400 / (30 / (2 / (1 / 100000.0))))
                                                  //> tRight  : Double = 2666666.6666666665
  val reduceRight: Double = (list :+ 100000.0) reduceRight ((x, y) => x / y)
                                                  //> reduceRight  : Double = 2666666.6666666665
  val foldRight: Double = (list foldRight 100000.0)(_ / _)
                                                  //> foldRight  : Double = 2666666.6666666665
  assert((divRightRes == tRight) == (reduceRight == foldRight))
                                                  //> res1: org.scalatest.Assertion = Succeeded

  // FOLD, REDUCE
  // https://commitlogs.com/2016/09/10/scala-fold-foldleft-and-foldright/
  List(1, 2, 3, 4, 5).par.foldLeft(8)(
    (x, y) => {
      println(s"x: $x, y: $y")
      x + y
    })                                            //> x: 8, y: 1
                                                  //| x: 9, y: 2
                                                  //| x: 11, y: 3
                                                  //| x: 14, y: 4
                                                  //| x: 18, y: 5
                                                  //| res2: Int = 23
  List(1, 2, 3, 4, 5).fold(0)(_ + _)              //> res3: Int = 15
  List(1, 2, 3, 4, 5).par.fold(0)(
    (x, y) => {
      println(s"x: $x, y: $y")
      x + y
    })                                            //> x: 0, y: 2
                                                  //| x: 0, y: 3
                                                  //| x: 0, y: 1
                                                  //| x: 0, y: 4
                                                  //| x: 0, y: 5
                                                  //| x: 1, y: 2
                                                  //| x: 4, y: 5
                                                  //| x: 3, y: 9
                                                  //| x: 3, y: 12
                                                  //| res4: Int = 15
  (List(1, 2, 3, 4, 5) :+ 8).par.reduce(
    (x, y) => {
      println(s"x: $x, y: $y")
      x + y
    })                                            //> x: 5, y: 8
                                                  //| x: 2, y: 3
                                                  //| x: 4, y: 13
                                                  //| x: 1, y: 5
                                                  //| x: 6, y: 17
                                                  //| res5: Int = 23

  (8 :: List(1, 2, 3, 4, 5)).par.reduce(
    (x, y) => {
      println(s"x: $x, y: $y")
      x + y
    })                                            //> x: 4, y: 5
                                                  //| x: 3, y: 9
                                                  //| x: 1, y: 2
                                                  //| x: 8, y: 3
                                                  //| x: 11, y: 12
                                                  //| res6: Int = 23
  val product = (x: Int, y: Int) => {
    val result = x * y
    println(s"multiplied $x by $y to yield $result")
    result
  }                                               //> product  : (Int, Int) => Int = <function2>
  val a = Array(1, 2, 3)                          //> a  : Array[Int] = Array(1, 2, 3)
  a.scanLeft(10)(product)                         //> multiplied 10 by 1 to yield 10
                                                  //| multiplied 10 by 2 to yield 20
                                                  //| multiplied 20 by 3 to yield 60
                                                  //| res7: Array[Int] = Array(10, 10, 20, 60)
  a.scanRight(10)(product)                        //> multiplied 3 by 10 to yield 30
                                                  //| multiplied 2 by 30 to yield 60
                                                  //| multiplied 1 by 60 to yield 60
                                                  //| res8: Array[Int] = Array(60, 60, 30, 10)
  val findMax = (x: Int, y: Int) => {
    Thread.sleep(10)
    val winner = x max y
    println(s"compared $x to $y, $winner was larger")
    winner
  }                                               //> findMax  : (Int, Int) => Int = <function2>

  val b = Array.range(0, 10)                      //> b  : Array[Int] = Array(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)
  b.reduce(findMax)                               //> compared 0 to 1, 1 was larger
                                                  //| compared 1 to 2, 2 was larger
                                                  //| compared 2 to 3, 3 was larger
                                                  //| compared 3 to 4, 4 was larger
                                                  //| compared 4 to 5, 5 was larger
                                                  //| compared 5 to 6, 6 was larger
                                                  //| compared 6 to 7, 7 was larger
                                                  //| compared 7 to 8, 8 was larger
                                                  //| compared 8 to 9, 9 was larger
                                                  //| res9: Int = 9
  b.reduceLeft(findMax)                           //> compared 0 to 1, 1 was larger
                                                  //| compared 1 to 2, 2 was larger
                                                  //| compared 2 to 3, 3 was larger
                                                  //| compared 3 to 4, 4 was larger
                                                  //| compared 4 to 5, 5 was larger
                                                  //| compared 5 to 6, 6 was larger
                                                  //| compared 6 to 7, 7 was larger
                                                  //| compared 7 to 8, 8 was larger
                                                  //| compared 8 to 9, 9 was larger
                                                  //| res10: Int = 9
  b.reduceRight(findMax)                          //> compared 8 to 9, 9 was larger
                                                  //| compared 7 to 9, 9 was larger
                                                  //| compared 6 to 9, 9 was larger
                                                  //| compared 5 to 9, 9 was larger
                                                  //| compared 4 to 9, 9 was larger
                                                  //| compared 3 to 9, 9 was larger
                                                  //| compared 2 to 9, 9 was larger
                                                  //| compared 1 to 9, 9 was larger
                                                  //| compared 0 to 9, 9 was larger
                                                  //| res11: Int = 9

}