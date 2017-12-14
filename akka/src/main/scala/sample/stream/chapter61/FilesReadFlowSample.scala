package sample.stream.chapter61

import java.io.File

import akka.NotUsed
import akka.stream.scaladsl.{Flow, GraphDSL, Sink, Source}

import scala.concurrent.Future

object FilesReadFlowSample {

  import akka.actor.ActorSystem
  import akka.stream._

  // implicit actor system
  implicit val system = ActorSystem("Sys")
  // implicit actor materializer
  implicit val materializer = ActorMaterializer()

  def main(args: Array[String]): Unit = {
    val g: Flow[String, Unit, NotUsed] = Flow.fromGraph(GraphDSL.create() {
      implicit builder =>
        import GraphDSL.Implicits._

        val A = builder.add(doQuery)
        val B = builder.add(analyzeResult)
        A ~> B
        FlowShape(A.in, B.out)
    })

    val files = Source(fileNames)
    val lines = files.map(file =>
      Source.fromIterator(() => scala.io.Source.fromFile(file.getName, "UTF-8").getLines)
    )

    val done = lines.runForeach(g.runWith(_, Sink.ignore))
    //    implicit val ec = system.dispatcher
    //    done.onComplete(_ => system.terminate())
  }

  val fileNames: List[File] = ???

  import scala.concurrent.ExecutionContext.Implicits.global
  import scala.concurrent.duration._

  val doQuery = Flow[String]
    .groupedWithin(1000, 100 millisecond)
    .mapAsync(4)(x =>
      Future[Seq[String]] {
        synchronized {
          // Do Something
          Nil
        }
      }
    )

  val analyzeResult: Flow[Seq[String], Unit, NotUsed] = ???
}

/*
Better solution to stream lines from multiple files


 */