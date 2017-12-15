package sample.stream.multipleFilesReader

import java.io.File

import akka.NotUsed
import akka.stream.scaladsl.{Flow, GraphDSL, Sink, Source}

import scala.collection.immutable
import scala.concurrent.Future

object MultipleFilesStreamReader0 extends MultipleFilesStreamReaderUtils {

  import akka.actor.ActorSystem
  import akka.stream._

  // implicit actor system
  implicit val system = ActorSystem("Sys")

  // implicit actor materializer
  implicit val materializer = ActorMaterializer()

  def main(args: Array[String]): Unit = {

    val g1: Flow[File, immutable.Seq[List[String]], NotUsed] = Flow.fromGraph(GraphDSL.create() {
      implicit builder =>
        import GraphDSL.Implicits._

        val A: FlowShape[File, List[String]] = builder.add(fileToCesiumID)
        val B: FlowShape[List[String], immutable.Seq[List[String]]] = builder.add(analyzeResult1)
        A ~> B
        FlowShape(A.in, B.out)
    })

    val A1: Source[File, NotUsed] = Source.fromIterator(() => fileNames.toIterator)
    val res: (NotUsed, Future[immutable.Seq[List[String]]]) = g1.runWith(A1, Sink.head)
  }

  val fileToCesiumID: Flow[File, List[String], NotUsed] = Flow[File]
    .map(
      file => {
        lazy val lines = scala.io.Source.fromFile(s"$directory/${file.getName}", "UTF-8").getLines().toList
        println(s"lines: ${lines.size}")
        lines
      }
    )

  import scala.concurrent.ExecutionContext.Implicits._
  import scala.concurrent.duration._

  val analyzeResult1: Flow[List[String], immutable.Seq[List[String]], NotUsed] = Flow[List[String]]
    .groupedWithin(1000, 10 second)
    .mapAsyncUnordered(5) {
      x => {
        println(s"x: ${x.size}")
        Future(x)
      }
    }
}
