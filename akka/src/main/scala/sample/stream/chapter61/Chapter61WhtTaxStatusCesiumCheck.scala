package sample.stream.chapter61

import akka.NotUsed
import akka.stream.scaladsl.{Flow, GraphDSL, RunnableGraph, Sink, Source}

import scala.concurrent.Future

object Chapter61WhtTaxStatusCesiumCheck {

  import akka.actor.ActorSystem
  import akka.stream._

  // implicit actor system
  implicit val system = ActorSystem("Sys")

  // implicit actor materializer
  implicit val materializer = ActorMaterializer()

  def main(args: Array[String]): Unit = {
    val g = RunnableGraph.fromGraph(GraphDSL.create() {
      implicit builder =>
        import GraphDSL.Implicits._

        // Source
        val A: Outlet[String] = builder.add(Source.fromIterator(() => cesiumIdLines)).out

        // Flows
        val B: FlowShape[String, Seq[String]] = builder.add(queryCesiumByBatches)
        val C = builder.add(analyzeResult)
        val D: Inlet[Any] = builder.add(Sink.ignore).in

        // Graph
        A ~> B ~> C ~> D

        ClosedShape
    })
    g.run()

  }

  val cesiumIdLines: Iterator[String] = scala.io.Source.fromFile(
    "/Users/oWen/Documents/scalaProjects/scalaPrinciples/ImportantNotes/akka/src/main/resources/test.csv", "UTF-8").getLines()

  import scala.concurrent.ExecutionContext.Implicits.global
  import scala.concurrent.duration._

  val queryCesiumByBatches: Flow[String, Seq[String], NotUsed] = Flow[String]
    .groupedWithin(10, 1 second)
    .mapAsync(3) {
      x =>
        Future[Seq[String]] {
          synchronized {
            println("=============")
            println(x)
            println("ooooooooooooo")
            x
          }
        }
    }

  val analyzeResult = Flow[Seq[String]]
    .map {
      x => println(s"x: $x")
    }
}