package sample.stream.mapConcat

import akka.NotUsed
import akka.stream._
import akka.stream.scaladsl.{Flow, GraphDSL}

import scala.collection.immutable
import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, Future}

object MyFlow {

  def getGraph(implicit ec: ExecutionContext) = Flow.fromGraph(
    GraphDSL.create() {
      implicit builder =>
        import GraphDSL.Implicits._

        val A = builder.add(getFlowA)
        val B = builder.add(getFlowB)
        A ~> B
        FlowShape(A.in, B.out)
    })

  def getFlowA: Flow[Int, Int, NotUsed] = Flow[Int].map(_ * 2)

  def getFlowB(implicit ec: ExecutionContext): Flow[Int, immutable.Seq[Int], NotUsed] =
    Flow[Int]
      .groupedWithin(10, 10 minute)
      .mapAsyncUnordered(parallelism = 5) {
        list => {
          Future {
            println(s"Receiving $list")
            Thread.sleep(1000)
            list.map(elem => elem * 10)
          }
        }
      }
}
