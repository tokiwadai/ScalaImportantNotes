package sample.stream.multipleFilesReader

import java.io.File

import akka.{Done, NotUsed}
import akka.stream.scaladsl.{Flow, GraphDSL, Sink, Source}

import scala.concurrent.Future

object MultipleFilesStreamReader3 extends MultipleFilesStreamReaderUtils {

  import akka.actor.ActorSystem
  import akka.stream._

  // implicit actor system
  implicit val system = ActorSystem("Sys")
  // implicit actor materializer
  implicit val materializer = ActorMaterializer()

  def main(args: Array[String]): Unit = {
    val g: Flow[String, Unit, NotUsed] = Flow.fromGraph(
      GraphDSL.create() {
        implicit builder =>
          import GraphDSL.Implicits._

          val B: FlowShape[String, Seq[String]] = builder.add(queryCesiumByBatches)
          val C: FlowShape[Seq[String], Unit] = builder.add(analyzeResult)
          B ~> C
          FlowShape(B.in, C.out)
      }
    )

    val files: Source[File, NotUsed] = Source(fileNames)
    val lines: Source[Source[String, NotUsed], NotUsed] = files.map(file => {
      val A: Source[String, NotUsed] = Source.fromIterator(
        () => {
          val result: Iterator[String] =
            scala.io.Source.fromFile(s"$directory/${file.getName}", "UTF-8").getLines
          result
        }
      )
      A
    })

    val done: Future[Done] = lines.runForeach(
      (src: Source[String, NotUsed]) => {
        val res: (NotUsed, Future[Done]) = g.runWith(src, Sink.ignore)
        res
      }
    )
    //    implicit val ec = system.dispatcher
    //    done.onComplete(_ => system.terminate())
  }
}
