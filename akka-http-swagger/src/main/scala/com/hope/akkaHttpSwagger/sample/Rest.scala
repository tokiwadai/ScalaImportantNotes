package com.hope.akkaHttpSwagger.sample

import akka.actor.{ActorSystem, Props}
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.RouteConcatenation
import akka.stream.ActorMaterializer
import ch.megard.akka.http.cors.scaladsl.CorsDirectives.cors
import com.hope.akkaHttpSwagger.sample.add.{AddActor, AddService}
import com.hope.akkaHttpSwagger.sample.hello.{HelloActor, HelloService}

object Rest extends App with RouteConcatenation {
  implicit val system = ActorSystem("akka-http-swagger-sample")
  sys.addShutdownHook(system.terminate())

  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher

  val add = system.actorOf(Props[AddActor])
  val hello = system.actorOf(Props[HelloActor])
  val routes =
    cors()(
      new AddService(add).route ~
        new HelloService(hello).route ~
        SwaggerDocService.routes
    )
  Http().bindAndHandle(routes, "0.0.0.0", 12345)
}