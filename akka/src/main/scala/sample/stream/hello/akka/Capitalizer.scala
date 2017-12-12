package sample.stream.hello.akka

import akka.actor.{Actor, ActorLogging}

class Capitalizer extends Actor with ActorLogging {
  def receive = {
    case str : String =>
      log.info(s"Capitalizing $str")
      sender ! str.capitalize
  }
}
