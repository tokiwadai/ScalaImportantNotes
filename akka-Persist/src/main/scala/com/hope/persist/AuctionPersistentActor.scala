package com.hope.persist

import akka.actor.ActorLogging
import akka.persistence.{PersistentActor, SnapshotOffer}

case class Bid(userId: String, offer: Int)
case object GetBids
case class Bids(bids: List[Bid])

case class AuctionState(events: List[Bid] = Nil)  {
  def updated(evt: Bid): AuctionState = {
    println(s"append $evt")
    copy(evt :: events)
  }
  def size: Int = events.length
  override def toString: String = events.reverse.toString
}

class AuctionPersistentActor  extends PersistentActor with ActorLogging {
  private var state = AuctionState()

  def updateState(event: Bid): Unit =
    state = state.updated(event)

  override def persistenceId = "auction-persistent-actor"

  override val receiveRecover: Receive = {
    case bid: Bid =>
      log.info(s"receiveRecover event: $bid")
      updateState(bid)
    case SnapshotOffer(_, snap: AuctionState) => {
      log.info(s"receiveRecover offeredState = $snap")
      state = snap
    }
  }

  override val receiveCommand: Receive = {
    case bid@Bid(_, _) =>
      persist(bid) { event =>
        updateState(event)
        context.system.eventStream.publish(event)
      }
    case GetBids =>
      log.info(s"GetBids $state")
      sender ! Bids(state.events)
    case "save" =>
      log.info(s"saving snapshot on demand...")
      saveSnapshot(state)
    case "nuke"  =>
      log.warning("replicate throwing exception, nuke!!")
      throw new Exception("nuke")
    case other  =>
      log.warning(s"unknown command, ignored!!, $other")
      throw new Exception("unknown command")
  }
}
