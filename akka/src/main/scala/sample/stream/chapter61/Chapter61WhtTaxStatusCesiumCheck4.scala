package sample.stream.chapter61

import java.nio.file.Path

import akka.{Done, NotUsed}
import akka.stream.scaladsl.{FileIO, Flow, Framing, GraphDSL, Sink, Source}
import akka.util.ByteString

import scala.concurrent.Future


object Chapter61WhtTaxStatusCesiumCheck4 extends Chapter61WhtTaxStatusCesiumCheckUtils {

  import akka.actor.ActorSystem
  import akka.stream._

  // implicit actor system
  implicit val system = ActorSystem("Sys")
  // implicit actor materializer
  implicit val materializer = ActorMaterializer()

  def main(args: Array[String]): Unit = {
    val g: Flow[Path, Unit, NotUsed] = Flow.fromGraph(
      GraphDSL.create() {
        implicit builder =>
          import GraphDSL.Implicits._

          val linesProcessing: Flow[ByteString, String, NotUsed] = Flow[ByteString]
            .via(Framing.delimiter(ByteString(System.lineSeparator), 10000, allowTruncation = true))
            .map(bs => bs.utf8String)
          val linesProcessing1: Flow[ByteString, String, NotUsed] = Flow[ByteString]
            .map(bs => bs.utf8String)

          val A: FlowShape[Path, String] = builder.add(Flow[Path]
            .flatMapConcat { path =>
              val src: Source[ByteString, Future[IOResult]] = FileIO.fromPath(path, 1)
              val res: Source[String, Future[IOResult]] = src via linesProcessing
              res
            }
          )
          val B: FlowShape[String, Seq[String]] = builder.add(queryCesiumByBatches)
          val C: FlowShape[Seq[String], Unit] = builder.add(analyzeResult)

          A ~> B ~> C
          FlowShape(A.in, C.out)
      }
    )

    val files: Source[Path, NotUsed] = Source(fileNames)
      .map(_.toPath)
    val res: (NotUsed, Future[Done]) = g.runWith(files, Sink.ignore)
    implicit val ec = system.dispatcher
    res._2.onComplete(_ => system.terminate())
  }
}
