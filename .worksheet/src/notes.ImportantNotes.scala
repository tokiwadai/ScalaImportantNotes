package notes

import org.scalatest.Assertions._

object ImportantNotes {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(120); 
  val list: List[Double] = List(400, 30, 2, 1);System.out.println("""list  : List[Double] = """ + $show(list ));$skip(146); 

  // LEFT
  def divLeft(xs: List[Double], acc: Double): Double = xs match {
    case Nil     => acc
    case x :: xs => divLeft(xs, acc / x)
  };System.out.println("""divLeft: (xs: List[Double], acc: Double)Double""");$skip(49); 
  val divLeftRes: Double = divLeft(list, 100000);System.out.println("""divLeftRes  : Double = """ + $show(divLeftRes ));$skip(59); 

  val tLeft: Double = ((((100000.0 / 400) / 30) / 2) / 1);System.out.println("""tLeft  : Double = """ + $show(tLeft ));$skip(75); 
  val reduceLeft: Double = (100000.0 :: list) reduceLeft ((x, y) => x / y);System.out.println("""reduceLeft  : Double = """ + $show(reduceLeft ));$skip(57); 
  val foldLeft: Double = (list foldLeft 100000.0)(_ / _);System.out.println("""foldLeft  : Double = """ + $show(foldLeft ));$skip(60); val res$0 = 
  assert((divLeftRes == tLeft) == (reduceLeft == foldLeft));System.out.println("""res0: org.scalatest.Assertion = """ + $show(res$0));$skip(149); 

  // RIGHT
  def divRight(xs: List[Double], acc: Double): Double = xs match {
    case Nil     => acc
    case x :: xs => x / divRight(xs, acc)
  };System.out.println("""divRight: (xs: List[Double], acc: Double)Double""");$skip(43); 
  val divRightRes = divRight(list, 100000);System.out.println("""divRightRes  : Double = """ + $show(divRightRes ));$skip(60); 

  val tRight: Double = (400 / (30 / (2 / (1 / 100000.0))));System.out.println("""tRight  : Double = """ + $show(tRight ));$skip(77); 
  val reduceRight: Double = (list :+ 100000.0) reduceRight ((x, y) => x / y);System.out.println("""reduceRight  : Double = """ + $show(reduceRight ));$skip(59); 
  val foldRight: Double = (list foldRight 100000.0)(_ / _);System.out.println("""foldRight  : Double = """ + $show(foldRight ));$skip(64); val res$1 = 
  assert((divRightRes == tRight) == (reduceRight == foldRight));System.out.println("""res1: org.scalatest.Assertion = """ + $show(res$1));$skip(198); val res$2 = 

  // FOLD, REDUCE
  // https://commitlogs.com/2016/09/10/scala-fold-foldleft-and-foldright/
  List(1, 2, 3, 4, 5).par.foldLeft(8)(
    (x, y) => {
      println(s"x: $x, y: $y")
      x + y
    });System.out.println("""res2: Int = """ + $show(res$2));$skip(37); val res$3 = 
  List(1, 2, 3, 4, 5).fold(0)(_ + _);System.out.println("""res3: Int = """ + $show(res$3));$skip(101); val res$4 = 
  List(1, 2, 3, 4, 5).par.fold(0)(
    (x, y) => {
      println(s"x: $x, y: $y")
      x + y
    });System.out.println("""res4: Int = """ + $show(res$4));$skip(107); val res$5 = 
  (List(1, 2, 3, 4, 5) :+ 8).par.reduce(
    (x, y) => {
      println(s"x: $x, y: $y")
      x + y
    });System.out.println("""res5: Int = """ + $show(res$5));$skip(108); val res$6 = 

  (8 :: List(1, 2, 3, 4, 5)).par.reduce(
    (x, y) => {
      println(s"x: $x, y: $y")
      x + y
    });System.out.println("""res6: Int = """ + $show(res$6));$skip(129); 
  val product = (x: Int, y: Int) => {
    val result = x * y
    println(s"multiplied $x by $y to yield $result")
    result
  };System.out.println("""product  : (Int, Int) => Int = """ + $show(product ));$skip(25); 
  val a = Array(1, 2, 3);System.out.println("""a  : Array[Int] = """ + $show(a ));$skip(26); val res$7 = 
  a.scanLeft(10)(product);System.out.println("""res7: Array[Int] = """ + $show(res$7));$skip(27); val res$8 = 
  a.scanRight(10)(product);System.out.println("""res8: Array[Int] = """ + $show(res$8));$skip(153); 
  val findMax = (x: Int, y: Int) => {
    Thread.sleep(10)
    val winner = x max y
    println(s"compared $x to $y, $winner was larger")
    winner
  };System.out.println("""findMax  : (Int, Int) => Int = """ + $show(findMax ));$skip(30); 

  val b = Array.range(0, 10);System.out.println("""b  : Array[Int] = """ + $show(b ));$skip(20); val res$9 = 
  b.reduce(findMax);System.out.println("""res9: Int = """ + $show(res$9));$skip(24); val res$10 = 
  b.reduceLeft(findMax);System.out.println("""res10: Int = """ + $show(res$10));$skip(25); val res$11 = 
  b.reduceRight(findMax);System.out.println("""res11: Int = """ + $show(res$11))}

}
