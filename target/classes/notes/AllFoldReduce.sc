package notes

import java.io.File

object AllFoldReduce {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  val l = List(0, 1, 2, 3, 4)                     //> l  : List[Int] = List(0, 1, 2, 3, 4)
  // Accumulator
  def sumAcc(xs: List[Int], acc: Int): Int = xs match {
    case Nil     => acc
    case y :: ys => sumAcc(ys, (acc + y))
  }                                               //> sumAcc: (xs: List[Int], acc: Int)Int
  sumAcc(l, 0)                                    //> res0: Int = 10


  // without Accumulator
  def sum(xs: List[Int]): Int = xs match {
    case Nil     => 0
    case y :: ys => y + sum(ys)
  }                                               //> sum: (xs: List[Int])Int
  sum(l)                                          //> res1: Int = 10

  // ========================
  // Reduce LEFT
  def sumReduceL(xs: List[Int]): Int = {
    (0 :: xs) reduceLeft (_ + _)
  }                                               //> sumReduceL: (xs: List[Int])Int
  sumReduceL(l)                                   //> res2: Int = 10

  // ========================
  // Fold LEFT
  def sumFoldL(xs: List[Int]): Int = {
    (xs foldLeft 0)(_ + _)
  }                                               //> sumFoldL: (xs: List[Int])Int
  sumFoldL(l)                                     //> res3: Int = 10

  // ========================
  // Reduce RIGHT
  def sumReduceR(xs: List[Int]): Int = {
    (0 :: xs) reduceRight (_ + _)
  }                                               //> sumReduceR: (xs: List[Int])Int
  sumReduceR(l)                                   //> res4: Int = 10

  // Fold RIGHT - simplified function
  def sumFoldR(xs: List[Int]): Int = {
    (xs.head + ((xs foldRight 0)(_ + _)))
  }                                               //> sumFoldR: (xs: List[Int])Int
  sumFoldR(l)                                     //> res5: Int = 10

  // ========================
  // Analysis
  // LEFT
  val dFL = ((((8 - 0) - 1) - 2) - 3) - 4         //> dFL  : Int = -2
  val list = List(0, 1, 2, 3, 4)                  //> list  : List[Int] = List(0, 1, 2, 3, 4)
  (list foldLeft 8)(_ - _)                        //> res6: Int = -2
  (list fold 8)(_ - _)                            //> res7: Int = -2
  (8 :: list) reduceLeft (_ - _)                  //> res8: Int = -2
  (8 :: list) reduce (_ - _)                      //> res9: Int = -2
  
  // RIGHT
  val dFR = 0 - (1 - (2 - (3 - (4 - 8))))         //> dFR  : Int = -6
  println(list)                                   //> List(0, 1, 2, 3, 4)
  (list foldRight 8)(_ - _)                       //> res10: Int = -6
  (8 :: list) reduceRight (_ - _)                 //> res11: Int = 6

	val children: Array[String] = Array("hello", "world")
                                                  //> children  : Array[String] = Array(hello, world)
	children.foldLeft("acc")((file, child) => {println(s"$file, $child"); file + ", " + child})
                                                  //> acc, hello
                                                  //| acc, hello, world
                                                  //| res12: String = acc, hello, world
	
  val file: File = new File("~")                  //> file  : java.io.File = ~
	children.foldLeft(file)((file, child) => { println(s"$file, $child"); new File(file, child)})
                                                  //> ~, hello
                                                  //| ~/hello, world
                                                  //| res13: java.io.File = ~/hello/world
}