package sample.stream.multipleFilesReader

import java.io.File

import akka.stream.scaladsl.{Flow, GraphDSL, Sink, Source}
import akka.{Done, NotUsed}

import scala.concurrent.Future


object MultipleFilesStreamReader5 extends MultipleFilesStreamReaderUtils {

  import akka.actor.ActorSystem
  import akka.stream._

  // implicit actor system
  implicit val system = ActorSystem("Sys")
  // implicit actor materializer
  implicit val materializer = ActorMaterializer()

  def main(args: Array[String]): Unit = {
    val g: Flow[File, Unit, NotUsed] = Flow.fromGraph(
      GraphDSL.create() {
        implicit builder =>
          import GraphDSL.Implicits._

          val A: FlowShape[File, String] = builder.add(Flow[File]
            .flatMapConcat { file =>
              val result: Iterator[String] =
                scala.io.Source.fromFile(s"${file.getAbsolutePath}", "UTF-8").getLines
              val src: Source[String, NotUsed] = Source.fromIterator(() => result)
              src
            }
          )
          val B: FlowShape[String, Seq[String]] = builder.add(queryCesiumByBatches)
          val C: FlowShape[Seq[String], Unit] = builder.add(analyzeResult)

          A ~> B ~> C
          FlowShape(A.in, C.out)
      }
    )

    val files: Source[File, NotUsed] = Source(fileNames)

    val (res: NotUsed, done: Future[Done]) = g.runWith(files, Sink.ignore)
    implicit val ec = system.dispatcher
    done.onComplete(_ => system.terminate())
  }
}
