package concurrency

// http://alvinalexander.com/scala/concurrency-with-scala-futures-tutorials-examples

import scala.concurrent.{ Future, Await }
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

object Futures1 extends App {
  implicit val baseTime: Long = System.currentTimeMillis

  val f = Future {
    sleep(500)
    1 + 1
  }

  val res = Await.result(f, 1 second)
  println(res)
  sleep(1000)
}