package sample.stream.mapConcat

import akka.actor.{Actor, ActorLogging}

case class Request(list: List[Int])

class MyActor extends Actor with ActorLogging {
  def receive = {
    case Request(list) =>
      log.info(s"Receiving $list")
      Thread.sleep(1000)
      sender ! list.map(elem => elem * 10)
  }
}
