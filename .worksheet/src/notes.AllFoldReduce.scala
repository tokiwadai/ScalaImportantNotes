package notes

import java.io.File

object AllFoldReduce {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(102); 
  println("Welcome to the Scala worksheet");$skip(30); 
  val l = List(0, 1, 2, 3, 4);System.out.println("""l  : List[Int] = """ + $show(l ));$skip(143); 
  // Accumulator
  def sumAcc(xs: List[Int], acc: Int): Int = xs match {
    case Nil     => acc
    case y :: ys => sumAcc(ys, (acc + y))
  };System.out.println("""sumAcc: (xs: List[Int], acc: Int)Int""");$skip(15); val res$0 = 
  sumAcc(l, 0);System.out.println("""res0: Int = """ + $show(res$0));$skip(128); 


  // without Accumulator
  def sum(xs: List[Int]): Int = xs match {
    case Nil     => 0
    case y :: ys => y + sum(ys)
  };System.out.println("""sum: (xs: List[Int])Int""");$skip(9); val res$1 = 
  sum(l);System.out.println("""res1: Int = """ + $show(res$1));$skip(126); 

  // ========================
  // Reduce LEFT
  def sumReduceL(xs: List[Int]): Int = {
    (0 :: xs) reduceLeft (_ + _)
  };System.out.println("""sumReduceL: (xs: List[Int])Int""");$skip(16); val res$2 = 
  sumReduceL(l);System.out.println("""res2: Int = """ + $show(res$2));$skip(116); 

  // ========================
  // Fold LEFT
  def sumFoldL(xs: List[Int]): Int = {
    (xs foldLeft 0)(_ + _)
  };System.out.println("""sumFoldL: (xs: List[Int])Int""");$skip(14); val res$3 = 
  sumFoldL(l);System.out.println("""res3: Int = """ + $show(res$3));$skip(128); 

  // ========================
  // Reduce RIGHT
  def sumReduceR(xs: List[Int]): Int = {
    (0 :: xs) reduceRight (_ + _)
  };System.out.println("""sumReduceR: (xs: List[Int])Int""");$skip(16); val res$4 = 
  sumReduceR(l);System.out.println("""res4: Int = """ + $show(res$4));$skip(124); 

  // Fold RIGHT - simplified function
  def sumFoldR(xs: List[Int]): Int = {
    (xs.head + ((xs foldRight 0)(_ + _)))
  };System.out.println("""sumFoldR: (xs: List[Int])Int""");$skip(14); val res$5 = 
  sumFoldR(l);System.out.println("""res5: Int = """ + $show(res$5));$skip(97); 

  // ========================
  // Analysis
  // LEFT
  val dFL = ((((8 - 0) - 1) - 2) - 3) - 4;System.out.println("""dFL  : Int = """ + $show(dFL ));$skip(33); 
  val list = List(0, 1, 2, 3, 4);System.out.println("""list  : List[Int] = """ + $show(list ));$skip(27); val res$6 = 
  (list foldLeft 8)(_ - _);System.out.println("""res6: Int = """ + $show(res$6));$skip(23); val res$7 = 
  (list fold 8)(_ - _);System.out.println("""res7: Int = """ + $show(res$7));$skip(33); val res$8 = 
  (8 :: list) reduceLeft (_ - _);System.out.println("""res8: Int = """ + $show(res$8));$skip(29); val res$9 = 
  (8 :: list) reduce (_ - _);System.out.println("""res9: Int = """ + $show(res$9));$skip(56); 
  
  // RIGHT
  val dFR = 0 - (1 - (2 - (3 - (4 - 8))));System.out.println("""dFR  : Int = """ + $show(dFR ));$skip(16); 
  println(list);$skip(28); val res$10 = 
  (list foldRight 8)(_ - _);System.out.println("""res10: Int = """ + $show(res$10));$skip(34); val res$11 = 
  (8 :: list) reduceRight (_ - _);System.out.println("""res11: Int = """ + $show(res$11));$skip(56); 

	val children: Array[String] = Array("hello", "world");System.out.println("""children  : Array[String] = """ + $show(children ));$skip(93); val res$12 = 
	children.foldLeft("acc")((file, child) => {println(s"$file, $child"); file + ", " + child});System.out.println("""res12: String = """ + $show(res$12));$skip(35); 
	
  val file: File = new File("~");System.out.println("""file  : java.io.File = """ + $show(file ));$skip(95); val res$13 = 
	children.foldLeft(file)((file, child) => { println(s"$file, $child"); new File(file, child)});System.out.println("""res13: java.io.File = """ + $show(res$13))}
}
