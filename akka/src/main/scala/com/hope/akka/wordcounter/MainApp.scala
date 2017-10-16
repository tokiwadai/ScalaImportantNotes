package com.hope.akka.wordcounter

import akka.actor.{ActorRef, ActorSystem, Props}

import scala.concurrent.{ExecutionContextExecutor, Future}

object MainApp extends App {
  import akka.pattern.ask
  import scala.concurrent.duration._

  override def main(args: Array[String]) {
    import akka.util.Timeout
    import akka.dispatch.ExecutionContexts.global

    val system: ActorSystem = ActorSystem("System")
    val wordCounterActor: ActorRef = system.actorOf(Props(new WordCounterActor(args(0))))

    implicit val timeout: Timeout = Timeout(25 seconds)
    implicit val ec: ExecutionContextExecutor = global
    val future: Future[Any] = wordCounterActor ? StartProcessFileMsg()

    future.map { result: Any =>
      println("Total number of words " + result)
      system.terminate
    }
    println("finish")
  }
}
