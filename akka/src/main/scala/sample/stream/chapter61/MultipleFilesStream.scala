package sample.stream.chapter61

import java.nio.file.Path

import akka.NotUsed
import akka.stream.scaladsl.{FileIO, Flow, Framing, Keep, Sink, Source}
import akka.util.ByteString
import sample.stream.chapter61.Chapter61WhtTaxStatusCesiumCheck4.fileNames

import scala.concurrent.Future

object MultipleFilesStream {
  import akka.actor.ActorSystem
  import akka.stream._

  // implicit actor system
  implicit val system = ActorSystem("Sys")
  // implicit actor materializer
  implicit val materializer = ActorMaterializer()

  def main(args: Array[String]): Unit = {
    // Given a stream of bytestrings delimited by the system line separator we can get lines represented as Strings
    val lines: Flow[ByteString, String, NotUsed] = Flow[ByteString]
      .via(Framing.delimiter(ByteString(System.lineSeparator), 10000, allowTruncation = true))
      .map(bs => bs.utf8String)

    // given as stream of Paths we read those files and count the number of lines
    val lineCounter: Sink[Path, Future[Long]] = Flow[Path]
      .flatMapConcat { path =>
        val src: Source[ByteString, Future[IOResult]] = FileIO.fromPath(path)
        val res: Source[String, Future[IOResult]] = src.via(lines)
        res
      }
      .fold(0l)((count, line) => count + 1)
      .toMat(Sink.head)(Keep.right)

    // Here's our test data source (replace paths with real paths)
    val testFiles: Source[Path, NotUsed] = Source(fileNames)
      .map(_.toPath)

    import scala.concurrent.ExecutionContext.Implicits.global
    // Runs the line counter over the test files returns a Future,
    // which contains the number of lines, which we then print out to the console when it completes
    testFiles runWith lineCounter foreach println
  }
}
