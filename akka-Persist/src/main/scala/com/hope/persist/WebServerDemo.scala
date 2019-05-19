package com.hope.persist

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.StatusCodes
import akka.stream.ActorMaterializer
import akka.http.scaladsl.server.Directives._
import akka.util.Timeout
import spray.json.DefaultJsonProtocol._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.server.Route

import scala.concurrent.{ExecutionContextExecutor, Future}
import scala.concurrent.duration._
import scala.io.StdIn
import akka.pattern.ask

object WebServerDemo {
  // these are from spray-json
  implicit val bidFormat = jsonFormat2(Bid)
  implicit val bidsFormat = jsonFormat1(Bids)

  implicit val system = ActorSystem("System")
  implicit val materializer = ActorMaterializer()
  // needed for the future flatMap/onComplete in the end
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher

  def getAuctionRoute(actorPARef: ActorRef): Route = {
    path("auction") {
      put {
        parameter('bid.as[Int], 'user) {
          (bid, user) =>
            // place a bid, fire-and-forget
            actorPARef ! Bid(user, bid)
            complete((StatusCodes.Accepted, "bid placed"))
        }
      } ~
        put {
          parameter('cmd) {
            cmd =>
              println(s"received $cmd")
              // run a command, fire-and-forget
              actorPARef ! cmd
              complete((StatusCodes.Accepted, "command executed!"))
          }
        } ~
        get {
          implicit val timeout: Timeout = 5 seconds

          // query the actor for the current auction state
          val bids: Future[Bids] = (actorPARef ? GetBids).mapTo[Bids]
          complete(bids)
        }
    }
  }

  val auctionRoute = getAuctionRoute(system.actorOf(Props[AuctionPersistentActor], "auctionActor"))

  def main(args: Array[String]) {
    val bindingFuture = Http().bindAndHandle(auctionRoute, "localhost", 8080)
    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done

  }
}
