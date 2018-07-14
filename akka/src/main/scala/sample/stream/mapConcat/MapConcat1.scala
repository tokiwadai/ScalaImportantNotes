package sample.stream.mapConcat

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Source

import scala.concurrent.{Await, Future}

object MapConcat1 extends App {
  // implicit actor system
  implicit val system = ActorSystem("Sys")
  // implicit actor materializer
  implicit val materializer = ActorMaterializer()

  def f: Future[List[Int]] = Future.successful((1 to 5).toList)

  def g(l: List[Int]): List[String] = l.map(_.toString * 2)

  Source
    .fromFuture(f)
    .mapConcat(g) // emits 5 elements of type Int
    .runForeach(println)

  Source
    .fromFuture(f)
    .map(g) // emits one element of type List[Int]
    .runForeach(println)


  Source('A' to 'E')
    .mapConcat(letter => (1 to 3).map(index => s"$letter$index"))
    .fold("")(_ + ", " +  _)
    .runForeach(println)

  val src: Source[Map[Int, String], NotUsed] =
    Source(List(Map((1-> "A"), (2 -> "B")), Map((3-> "C"), (4 -> "D")), Map((5-> "E"), (6 -> "F")) ))

  src.runForeach(println)

  val res: Future[Map[Int, String]] = src
    .runFold(Map[Int, String]())(_ ++ _)

  import scala.concurrent._
  import scala.concurrent.duration._
  val temp: Map[Int, String] = Await.result(res , 10 minute)
  println(temp)

  val source = Source(1 to 100)
  val res3: Future[List[Int]] = source
    .groupedWithin(10, 1 minute)
    .map { list => {
      println(s"list: $list")
      list.map(elem => elem * 10
      )
    }}.runFold(List[Int]())(_ ++ _)
  val temp3 = Await.result(res3 , 10 minute)
  println(temp3)



  import scala.concurrent.ExecutionContext.Implicits.global
  val res2: Future[List[Int]] = source
    .groupedWithin(10, 5 minute)
    .mapAsync(2) {
      list => {
        println(s"list: $list")
        Future[Seq[Int]] {
          Thread.sleep(2000)
          list.map(elem => elem * 10)
        }
      }
    }.runFold(List[Int]())(_ ++ _)
  val temp2 = Await.result(res2 , 10 minute)
  println(temp2)

  system.terminate()

//  Source('A' to 'E')
//    .flatMapConcat(letter => Source(1 to 3).map(index => s"$letter$index"))
//    .runForeach(println)
//  Source
//    .fromFuture(Future.successful(Stream.range(1, 10)))
//    .flatMapConcat(Source.apply)
//    .runForeach(println)
}
