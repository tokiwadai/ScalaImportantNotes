package com.hope.persist

import akka.http.scaladsl.model._
import akka.http.scaladsl.testkit.ScalatestRouteTest
import akka.testkit.TestKit
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{Matchers, WordSpec}

class AuctionRoutesSpec  extends WordSpec with Matchers with ScalaFutures with ScalatestRouteTest {
  val auctionRoute2Test = WebServerDemo.auctionRoute

  override def afterAll: Unit = {
    TestKit.shutdownActorSystem(system)
  }

  "auctionRoute" should {
    "return empty Bids list if no present (GET /auction)" in {
      // note that there's no need for the host part in the uri:
      val request = HttpRequest(uri = "/auction")

      request ~> auctionRoute2Test ~> check {
        status should ===(StatusCodes.OK)

        // we expect the response to be json:
        contentType should ===(ContentTypes.`application/json`)

        // and no entries should be in the list:
        entityAs[String] should ===("""{"bids":[]}""")
      }
    }

    "return 'command executed!' string if no present (PUT /auction?cmd=save)" in {
      // note that there's no need for the host part in the uri:
      val request = HttpRequest(uri = "/auction?cmd=save", method = HttpMethods.PUT)

      request ~> auctionRoute2Test ~> check {
        status should ===(StatusCodes.Accepted)

        // we expect the response to be json:
        contentType should ===(ContentTypes.`text/plain(UTF-8)`)

        // and no entries should be in the list:
        entityAs[String] should ===("""command executed!""")
      }
    }

    "return Bids list if no present (PUT /auction?bid=20&user=to)" in {
      // note that there's no need for the host part in the uri:
      val request = HttpRequest(uri = "/auction?bid=20&user=to", method = HttpMethods.PUT)

      request ~> auctionRoute2Test ~> check {
        status should ===(StatusCodes.Accepted)

        // we expect the response to be json:
        contentType should ===(ContentTypes.`text/plain(UTF-8)`)

        // and no entries should be in the list:
        entityAs[String] should ===("""bid placed""")
      }

      val request2 = HttpRequest(uri = "/auction", method = HttpMethods.GET)
      request2 ~> auctionRoute2Test ~> check {
        status should ===(StatusCodes.OK)

        // we expect the response to be json:
        contentType should ===(ContentTypes.`application/json`)

        // and no entries should be in the list:
        entityAs[String] should ===("""{"bids":[{"userId":"to","offer":20}]}""")
//        entityAs[List[String]] should === (List(""))
      }
    }
  }
}
