package com.hope.persist

import akka.actor.{ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit}
import org.scalatest._

class AuctionPersistentActorTest extends TestKit(ActorSystem("AuctionPersistentActorTest"))
  with WordSpecLike
  with Matchers
  with BeforeAndAfterAll
  with ImplicitSender {

  override def afterAll: Unit = {
    TestKit.shutdownActorSystem(system)
  }

  "AuctionPersistentActor" should {
    "add a bid item and preserve it after restart" in {
      val auctionActor = system.actorOf(Props[AuctionPersistentActor])

      auctionActor ! Bid("user1", 2)

      auctionActor ! GetBids

      expectMsg(Bids(List(Bid("user1", 2))))
    }

    "add a bid item and preserve it after throwing nuke exception" in {
      val auctionActor = system.actorOf(Props[AuctionPersistentActor])

      auctionActor ! Bid("user1", 3)
      auctionActor ! "nuke"
      auctionActor ! GetBids

      expectMsg(Bids(List(Bid("user1", 3), Bid("user1", 2))))
    }
  }
}
