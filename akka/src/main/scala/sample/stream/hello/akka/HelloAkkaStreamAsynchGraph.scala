package sample.stream.hello.akka

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import akka.routing.RoundRobinPool
import akka.stream.{ActorMaterializer, ClosedShape}
import akka.stream.scaladsl.{Flow, GraphDSL, RunnableGraph, Sink, Source}

object HelloAkkaStreamAsynchGraph {

  def main(args: Array[String]): Unit = {
    import akka.pattern.ask
    import akka.util.Timeout

    import scala.concurrent.duration._
    implicit val actorSystem = ActorSystem()
    implicit val actorMaterializer = ActorMaterializer()
    implicit val askTimeout = Timeout(5 seconds)

    val capitalizer = actorSystem.actorOf(Props[Capitalizer].withRouter(RoundRobinPool(10)))
    val graph = RunnableGraph.fromGraph(GraphDSL.create() { implicit b =>
      import GraphDSL.Implicits._
      val source = Source(List("hello","from","akka","streams!"))
      val sink = Sink.foreach(actorSystem.log.info)
      val flow = Flow[String].mapAsyncUnordered(parallelism = 5)(elem => (capitalizer ? elem).mapTo[String])
      source ~> flow ~> sink

      ClosedShape
    })
    graph.run()
  }

  class Capitalizer extends Actor with ActorLogging {
    def receive = {
      case str : String =>
        log.info(s"Capitalizing $str")
        sender ! str.capitalize
    }
  }
}

