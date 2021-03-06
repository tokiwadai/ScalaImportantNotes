/*
https://jaxenter.com/tutorial-asynchronous-programming-with-akka-actors-105682.html
 */
package org.jamieallen.effectiveakka.pattern.extra

import scala.concurrent.{ExecutionContext, Future}
import scala.concurrent.duration._
import akka.util.Timeout
import akka.actor.{Actor, ActorRef}
import akka.pattern.ask
import org.jamieallen.effectiveakka.common.{AccountBalances, GetCustomerAccountBalances}

class AccountBalanceRetriever1(savingsAccounts: ActorRef, checkingAccounts: ActorRef, moneyMarketAccounts: ActorRef) extends Actor {
  implicit val timeout: Timeout = 100 milliseconds
  implicit val ec: ExecutionContext = context.dispatcher
  def receive = {
    case GetCustomerAccountBalances(id) =>
      val futSavings: Future[Any] = savingsAccounts ? GetCustomerAccountBalances(id)
      val futChecking = checkingAccounts ? GetCustomerAccountBalances(id)
      val futMM = moneyMarketAccounts ? GetCustomerAccountBalances(id)

      val futBalances = for {
        savings <- futSavings.mapTo[Option[List[(Long, BigDecimal)]]]
        checking <- futChecking.mapTo[Option[List[(Long, BigDecimal)]]]
        mm <- futMM.mapTo[Option[List[(Long, BigDecimal)]]]
      } yield AccountBalances(savings, checking, mm)

      // race condition on sender
      futBalances map (sender ! _)
  }
}
