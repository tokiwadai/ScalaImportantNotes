package com.hope.akkaHttpSwagger.sample

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.marshalling.Marshal
import akka.http.scaladsl.model.{HttpMethods, HttpRequest, HttpResponse, RequestEntity}
import akka.stream.ActorMaterializer
import com.hope.akkaHttpSwagger.sample.add.AddActor.AddRequest
import spray.json.DefaultJsonProtocol

import scala.concurrent.Future
import scala.util.{Failure, Success}

trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val itemFormat = jsonFormat1(AddRequest)
}

object RestClient extends App with JsonSupport {
  implicit val system = ActorSystem()
  implicit val ec = system.dispatcher
  implicit val materializer = ActorMaterializer()

  val http = Http(system)

  def postApplication(app: AddRequest): Future[HttpResponse] =
    Marshal(app).to[RequestEntity] flatMap { (entity: RequestEntity) =>
      val request = HttpRequest(method = HttpMethods.POST,
        uri = "http://localhost:12345/add", entity = entity)
      http.singleRequest(request)
    }

  val application = AddRequest(Array(150,20))
  postApplication(application) onComplete {
    case Failure(ex) => System.out.println(s"Failed to post $application, reason: $ex")
    case Success(response) => System.out.println(s"Server responded with $response")
  }
}



