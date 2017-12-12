package sample.stream.akka.io

import java.nio.file.Paths

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.scaladsl.{FileIO, Source}
import akka.stream.{ActorMaterializer, IOResult}
import akka.util.ByteString

import scala.concurrent.Future

object Factorial {
  def main(args: Array[String]): Unit = {
    // implicit actor system
    implicit val system = ActorSystem("Sys")
    // implicit actor materializer
    implicit val materializer = ActorMaterializer()

    val source: Source[Int, NotUsed] = Source(1 to 100)
//    source.runForeach(i ⇒ println(i))(materializer)

    val factorials = source.scan(BigInt(1))((acc, next) ⇒ acc * next)

    val result: Future[IOResult] =
      factorials
        .map(num ⇒ ByteString(s"$num\n"))
        .runWith(FileIO.toPath(Paths.get("factorials.txt")))
    implicit val ec = system.dispatcher
    result.onComplete(_ ⇒ system.terminate())
  }
}
