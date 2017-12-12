package sample.stream.akka.io

import akka.actor.ActorSystem
import akka.stream.scaladsl.Source
import akka.stream.{ActorMaterializer, ThrottleMode}
import akka.{Done, NotUsed}

import scala.concurrent.Future

object FactorialThrottle {
  def main(args: Array[String]): Unit = {
    // implicit actor system
    implicit val system = ActorSystem("Sys")
    // implicit actor materializer
    implicit val materializer = ActorMaterializer()

    val source: Source[Int, NotUsed] = Source(1 to 100)
    val factorials = source.scan(BigInt(1))((acc, next) ⇒ acc * next)

    import scala.concurrent.duration._
    val result: Future[Done] = factorials
      .zipWith(Source(0 to 100))((num, idx) ⇒ s"$idx! = $num")
      .throttle(1, 1.second, 1, ThrottleMode.shaping)
      .runForeach(println)

    implicit val ec = system.dispatcher
    result.onComplete(_ ⇒ system.terminate())
  }
}
