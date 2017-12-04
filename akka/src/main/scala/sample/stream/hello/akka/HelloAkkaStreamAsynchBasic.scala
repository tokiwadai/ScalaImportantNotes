package sample.stream.hello.akka

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import akka.routing.RoundRobinPool
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Flow, Sink, Source}

object HelloAkkaStreamAsynchBasic {

  def main(args: Array[String]): Unit = {
    import akka.pattern.ask
    import akka.util.Timeout

    import scala.concurrent.duration._
    implicit val actorSystem = ActorSystem()
    implicit val actorMaterializer = ActorMaterializer()
    implicit val askTimeout = Timeout(5 seconds)

    val capitalizer = actorSystem.actorOf(Props[Capitalizer].withRouter(RoundRobinPool(10)))
    val source = Source(List("hello","from","akka","streams!"))
    val sink = Sink.foreach(actorSystem.log.info)
    val flowOrdered = Flow[String].mapAsync(parallelism = 5)(elem => (capitalizer ? elem).mapTo[String])
    val flowUnordered = Flow[String].mapAsyncUnordered(parallelism = 5)(elem => (capitalizer ? elem).mapTo[String])

    val stream = source.via(flowUnordered).to(sink)
    stream.run()
  }
}

class Capitalizer extends Actor with ActorLogging {
  def receive = {
    case str : String =>
      log.info(s"Capitalizing $str")
      sender ! str.capitalize
  }
}