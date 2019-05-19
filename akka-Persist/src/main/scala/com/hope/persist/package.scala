package com.hope

import akka.persistence.PersistentActor

package object persist {
  case class Bid(userId: String, offer: Int)
  case object GetBids
  case class Bids(bids: List[Bid])


  trait AuctionPersistentProxy extends PersistentActor
}
