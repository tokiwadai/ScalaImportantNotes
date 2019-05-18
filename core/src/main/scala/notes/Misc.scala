package notes

object Misc extends App {
  def listWithSum(numbers: List[Int]) =
    numbers.foldLeft((List[Int](), 0)) {
      (resultingTuple, currentInteger) =>
        (currentInteger :: resultingTuple._1, currentInteger + resultingTuple._2)
    }

  val list = List(1, 2, 3)
  println(listWithSum(list))


  val pws = Map("Apache Spark" -> "http://spark.apache.org/", "Scala" -> "http://www.scala-lang.org/")

  val websites = Seq("Apache Spark", "Scala").map(pws)
  print(s"websites: $websites")
}
