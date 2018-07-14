package sample.stream.hello.akka

import akka.NotUsed
import akka.actor.{ActorSystem, Props}
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
    val flowOrdered: Flow[String, String, NotUsed] = Flow[String].mapAsync(parallelism = 5)(elem => (capitalizer ? elem).mapTo[String])
    val flowUnordered: Flow[String, String, NotUsed] =
      Flow[String].mapAsyncUnordered(parallelism = 5)(elem => (capitalizer ? elem).mapTo[String])

    val stream = source.via(flowUnordered).to(sink)
    stream.run
    actorSystem.terminate
  }
}

