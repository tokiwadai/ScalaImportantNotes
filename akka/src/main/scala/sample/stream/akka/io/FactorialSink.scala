package sample.stream.akka.io

import java.nio.file.Paths

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, IOResult}
import akka.stream.scaladsl.{FileIO, Flow, Keep, Sink, Source}
import akka.util.ByteString

import scala.concurrent.Future

object FactorialSink {
  def main(args: Array[String]): Unit = {
    // implicit actor system
    implicit val system = ActorSystem("Sys")
    // implicit actor materializer
    implicit val materializer = ActorMaterializer()

    val source: Source[Int, NotUsed] = Source(1 to 100)
    val factorials = source.scan(BigInt(1))((acc, next) ⇒ acc * next)

    def lineSink(filename: String): Sink[String, Future[IOResult]] =
      Flow[String]
        .map(s ⇒ ByteString(s + "\n"))
        .toMat(FileIO.toPath(Paths.get(filename)))(Keep.right)

    val result: Future[IOResult] = factorials
      .map(_.toString)
      .runWith(lineSink("factorial2.txt"))
    implicit val ec = system.dispatcher
    result.onComplete(_ ⇒ system.terminate())
  }
}
