package sample.stream.mapConcat

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Source

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import scala.util.{Failure, Success, Try}

object MapConcat4 {
  // implicit actor system
  implicit val system = ActorSystem("Sys")
  implicit val ec = system.dispatcher

  // implicit actor materializer
  implicit val materializer = ActorMaterializer()

  val source = Source(1 to 100)

  def main(args: Array[String]): Unit = {
    Try {
      val result: Future[List[Int]] = source
        .via(MyFlow.getFlow)
        .runFold(List[Int]())(_ ++ _)
      Await.result(result, 10 minute)
    } match {
      case Success(list) => println(s"temp2 [${list.size}]: $list")
      case Failure(e) => println(e)
    }
    println("terminating system...")
    system.terminate
    println("System.exit...")
    System.exit(0)
  }
}
