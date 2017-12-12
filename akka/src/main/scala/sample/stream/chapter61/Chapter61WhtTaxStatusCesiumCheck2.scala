package sample.stream.chapter61

import akka.stream.scaladsl.{GraphDSL, RunnableGraph, Sink, Source}

object Chapter61WhtTaxStatusCesiumCheck2 extends Chapter61WhtTaxStatusCesiumCheckUtils {

  import akka.actor.ActorSystem
  import akka.stream._

  // implicit actor system
  implicit val system = ActorSystem("Sys")

  // implicit actor materializer
  implicit val materializer = ActorMaterializer()

  def main(args: Array[String]): Unit = {
    fileNames.map(
      file => {
        val g = RunnableGraph.fromGraph(
          GraphDSL.create() {
            implicit builder =>
              import GraphDSL.Implicits._

              val A: Outlet[String] = builder.add(
                Source.fromIterator(() =>
                  scala.io.Source.fromFile(s"$directory/${file.getName}", "UTF-8").getLines)
              ).out
              val B: FlowShape[String, Seq[String]] = builder.add(queryCesiumByBatches)
              val C: FlowShape[Seq[String], Unit] = builder.add(analyzeResult)
              val D: Inlet[Any] = builder.add(Sink.ignore).in

              // Graph
              A ~> B ~> C ~> D
              ClosedShape
          }
        )
        g.run()
      }
    )
  }
}
