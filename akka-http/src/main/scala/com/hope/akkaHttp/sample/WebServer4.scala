package com.hope.akkaHttp.sample

import akka.actor.{Actor, ActorLogging, ActorRef, ActorSystem, Props}
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.StatusCodes
import akka.stream.ActorMaterializer
import akka.http.scaladsl.server.Directives._
import akka.util.Timeout
import spray.json.DefaultJsonProtocol._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._

import scala.concurrent.{ExecutionContextExecutor, Future}
import scala.concurrent.duration._
import scala.io.StdIn
import akka.pattern.ask

object WebServer4 {

  case class Bid(userId: String, offer: Int)

  case object GetBids

  case class Bids(bids: List[Bid])

  class Auction extends Actor with ActorLogging {
    var bids = List.empty[Bid]

    def receive = {
      case bid@Bid(userId, offer) =>
        bids = bids :+ bid
        log.info(s"Bid complete: $userId, $offer")
      case GetBids => sender() ! Bids(bids)
      case _ => log.info("Invalid message")
    }
  }

  // these are from spray-json
  implicit val bidFormat = jsonFormat2(Bid)
  implicit val bidsFormat = jsonFormat1(Bids)

  def main(args: Array[String]) {
    implicit val system: ActorSystem = ActorSystem("System")
    implicit val materializer: ActorMaterializer = ActorMaterializer()
    // needed for the future flatMap/onComplete in the end
    implicit val executionContext: ExecutionContextExecutor = system.dispatcher

    val auction: ActorRef = system.actorOf(Props[Auction], "auction")
    val route =
      path("auction") {
        put {
          parameter('bid.as[Int], 'user) {
            (bid, user) =>
              // place a bid, fire-and-forget
              auction ! Bid(user, bid)
              complete((StatusCodes.Accepted, "bid placed"))
          }
        } ~
          get {
            implicit val timeout: Timeout = 5 seconds

            // query the actor for the current auction state
            val bids: Future[Bids] = (auction ? GetBids).mapTo[Bids]
            complete(bids)
          }
      }

    val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)
    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done

  }
}
