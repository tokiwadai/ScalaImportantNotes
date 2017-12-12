package sample.stream.chapter61

import java.io.File

import akka.NotUsed
import akka.stream.scaladsl.{Flow, GraphDSL, Sink, Source}

object Chapter61WhtTaxStatusCesiumCheck3 extends Chapter61WhtTaxStatusCesiumCheckUtils {

  import akka.actor.ActorSystem
  import akka.stream._

  // implicit actor system
  implicit val system = ActorSystem("Sys")
  // implicit actor materializer
  implicit val materializer = ActorMaterializer()

  def main(args: Array[String]): Unit = {
    val g1: Flow[String, Unit, NotUsed] = Flow.fromGraph(
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
    val lines: Source[Source[String, NotUsed], NotUsed] =
      files.map(file => {
        val A: Source[String, NotUsed] = Source.fromIterator(
          () => {
            val lines = scala.io.Source.fromFile(s"$directory/${file.getName}", "UTF-8").getLines
            lines
          }
        )
        A
      })

    val done = lines.runForeach(
      (src: Source[String, NotUsed]) => g1.runWith(src, Sink.ignore)
    )
    //    implicit val ec = system.dispatcher
    //    done.onComplete(_ => system.terminate())
  }
}
