package sample.stream.mapConcat

import akka.actor.{ActorLogging, ActorSystem, Props}
import akka.pattern.ask
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Source
import akka.util.Timeout

import scala.concurrent.duration._
import scala.concurrent.{Await, ExecutionContext, ExecutionContextExecutor, Future}

object MapConcat3 extends App  {
  // implicit actor system
  implicit val system = ActorSystem("Sys")
  implicit val ec: ExecutionContext = system.dispatcher
  // implicit actor materializer
  implicit val materializer = ActorMaterializer()

  val source = Source(1 to 100)
  implicit val askTimeout = Timeout(60 seconds)

  val actor = system.actorOf(Props[MyActor], "myactor")

  val res2: Future[List[Int]] = source
    .map(elem => elem * 2)
    .groupedWithin(10, 10 minute)
    .mapAsync(parallelism = 5) {
      list => {
        Future {
          println(s"Receiving $list")
          Thread.sleep(1000)
          list.map(elem => elem * 10)
        }
      }
    }.runFold(List[Int]())(_ ++ _)
  val temp2 = Await.result(res2 , 10 minute)
  println(s"temp2 [${temp2.size}]: $temp2")

  system.terminate()
}
