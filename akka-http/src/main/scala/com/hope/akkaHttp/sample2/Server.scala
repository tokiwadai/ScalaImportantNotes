package com.hope.akkaHttp.sample2

import akka.http.scaladsl.model.{HttpEntity, StatusCodes}
import akka.http.scaladsl.server.{HttpApp, Route}

object Server extends HttpApp with App {

  override protected def routes: Route =
    path("apps") {
      post {
        entity(as[HttpEntity]) { entity =>
          System.out.println(entity.getContentType())
          complete(StatusCodes.Created)
        }
      }
    }

  startServer("localhost", 8080)
}
