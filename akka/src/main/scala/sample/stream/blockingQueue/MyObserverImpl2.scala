package sample.stream.blockingQueue

import java.io.File

import akka.NotUsed
import akka.stream.scaladsl.{Flow, GraphDSL, Sink, Source, SourceQueueWithComplete}
import sample.stream.multipleFilesReader.MultipleFilesStreamReader5.{analyzeResult, fileNames, queryCesiumByBatches}

object MyObserverImpl2 {

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

    val bufferSize = 100

    //if the buffer fills up then this strategy drops the oldest elements
    //upon the arrival of a new element.
    val overflowStrategy = akka.stream.OverflowStrategy.backpressure

    val sourceQueue: SourceQueueWithComplete[File] = Source
      .queue(bufferSize, overflowStrategy)
      .via(g)
      .to(Sink.ignore)
      .run()

    fileNames.map(fileName => sourceQueue offer fileName)
  }
}
