package com.hope.persist

import akka.persistence.PersistentActor

case class Cmd(data: (Int, String))
case class Evt(data: (Int, String))

case class BankAccountState(events: List[(Int, String)] = Nil) {
  def updated(evt: Evt): BankAccountState = copy(evt.data :: events)
  def size: Int = events.length
  override def toString: String = events.reverse.toString
}

class BankPersistenceActor extends PersistentActor {
  var state = BankAccountState()

  def updateState(event: Evt): Unit =
    state = state.updated(event)

  def numEvents =
    state.size

  override def persistenceId: String = "bank-persistence-id"

  val receiveCommand: Receive = {
    case Cmd(data) =>
      persist(Evt(data)) { event =>
        updateState(event)
        context.system.eventStream.publish(event)
      }
    case "snap"  => saveSnapshot(state)
    case "print" => println(state)
  }

  override def receiveRecover: Receive = ???

}
