package sample.stream.akka.io

import akka.{Done, NotUsed}
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Source

import scala.collection.immutable
import scala.concurrent.Future

object SimpleSource {
  def main(args: Array[String]): Unit = {
    // implicit actor system
    implicit val system = ActorSystem("Sys")
    // implicit actor materializer
    implicit val materializer = ActorMaterializer()

    val r: immutable.Seq[Int] = (1 to 100)
    val source: Source[Int, NotUsed] = Source(1 to 100)
//    source.runForeach(i ⇒ println(i))(materializer)

    val done: Future[Done] = source.runForeach(i ⇒ println(i))(materializer)
    implicit val ec = system.dispatcher
    done.onComplete(_ ⇒ system.terminate())
  }
}
