package sample.stream.hello.akka

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Flow, Sink, Source}


object HelloAkkaStreamSynchBasic2 {

  def main(args: Array[String]): Unit = {
    implicit val actorSystem = ActorSystem()
    implicit val actorMaterializer = ActorMaterializer()

    val source = Source(List("hello","from","akka","streams!"))
    val sink = Sink.foreach(actorSystem.log.info)
    val capitalizer: Flow[String, String, NotUsed] = Flow[String].map(_.capitalize)
    val stream = source.via(capitalizer).to(sink)

    stream.run()
  }
}
