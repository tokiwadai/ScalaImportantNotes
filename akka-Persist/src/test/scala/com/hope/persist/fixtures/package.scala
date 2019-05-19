package com.hope.persist

import akka.actor.ActorLogging
import akka.persistence.PersistentActor

package object fixtures {
  import com.hope.persist.fixtures.RestartableActor._

  trait RestartableActor extends PersistentActor with ActorLogging {

    abstract override def receiveCommand =
      super.receiveCommand orElse {
        case RestartActor =>
          log.info(s"receiving $RestartActor, will throw RestartActorException...")
          throw RestartActorException
      }
  }

  object RestartableActor {
    case object RestartActor

    private object RestartActorException extends Exception
  }
}
