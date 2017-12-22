package sample.stream.blockingQueue

import akka.stream.scaladsl.{Sink, Source, SourceQueueWithComplete}

import scala.concurrent.Future

case class Weather(zipCode: String, temperature: Double, raining: Boolean)

object MyObserverImpl {

  import akka.actor.ActorSystem
  import akka.stream._

  // implicit actor system
  implicit val system = ActorSystem("Sys")
  // implicit actor materializer
  implicit val materializer = ActorMaterializer()

  def main(args: Array[String]): Unit = {

    val bufferSize = 100

    //if the buffer fills up then this strategy drops the oldest elements
    //upon the arrival of a new element.
    val overflowStrategy = akka.stream.OverflowStrategy.dropHead

    val queue: SourceQueueWithComplete[Any] = Source
      .queue(bufferSize, overflowStrategy)
      .to(Sink foreach println)
      .run() // in order to "keep" the queue Materialized value instead of the Sink's

    val res: Future[QueueOfferResult] = queue.offer(Weather("02139", 32.0, true))
    implicit val ec = system.dispatcher
    res.onComplete(
      (a) => {
        println(a.isSuccess)
        system.terminate
      }
    )
  }
}
