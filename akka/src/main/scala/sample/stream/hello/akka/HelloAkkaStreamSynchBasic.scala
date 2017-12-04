package sample.stream.hello.akka

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Sink, Source}


object HelloAkkaStreamSynchBasic {

  def main(args: Array[String]): Unit = {
    implicit val actorSystem = ActorSystem()
    implicit val actorMaterializer = ActorMaterializer()

    val stream = Source(List("hello","from","akka","streams!"))
      .map(_.capitalize)
      .to(Sink.foreach(actorSystem.log.info))

    stream.run()
  }
}
