package sample.stream.multipleFilesReader

import java.io.File

import akka.NotUsed
import akka.stream.scaladsl.{Flow, GraphDSL, RunnableGraph, Sink, Source}

object MultipleFilesStreamReader1 extends MultipleFilesStreamReaderUtils {

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

    val g = RunnableGraph.fromGraph(GraphDSL.create() {
      implicit builder =>
        import GraphDSL.Implicits._

        val files: Source[File, NotUsed] = Source(fileNames)
        files.map(
          file => {
            val A: Outlet[String] = builder.add(
              Source.fromIterator(() =>
                scala.io.Source.fromFile(s"$directory/${file.getName}", "UTF-8").getLines)
            ).out
            val B: FlowShape[String, Seq[String]] = builder.add(queryCesiumByBatches)
            val C: FlowShape[Seq[String], Unit] = builder.add(analyzeResult)
            val D: Inlet[Any] = builder.add(Sink.ignore).in

            // Graph
            A ~> B ~> C ~> D
          }
        )
        ClosedShape
    })
    g.run()
  }
}
