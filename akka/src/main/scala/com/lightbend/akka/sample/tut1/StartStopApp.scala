package com.lightbend.akka.sample.tut1

import akka.actor.{Actor, ActorSystem, Props}

import scala.io.StdIn

class StartStopActor1 extends Actor {
  override def preStart(): Unit = {
    println("first started")
    context.actorOf(Props[StartStopActor2], "second")
  }
  override def postStop(): Unit = println("first stopped")

  override def receive: Receive = {
    case "stop" => context.stop(self)
  }
}

class StartStopActor2 extends Actor {
  override def preStart(): Unit = println("second started")
  override def postStop(): Unit = println("second stopped")

  // Actor.emptyBehavior is a useful placeholder when we don't
  // want to handle any messages in the actor.
  override def receive: Receive = Actor.emptyBehavior
}

object StartStopApp extends App {
  val system = ActorSystem()

  val ss1 = system.actorOf(Props[StartStopActor1], "StartStopActor1")
  println(s"First: $ss1")
  ss1 ! "stop"

  println(">>> Press ENTER to exit <<<")
  try StdIn.readLine()
  finally system.terminate()
}
