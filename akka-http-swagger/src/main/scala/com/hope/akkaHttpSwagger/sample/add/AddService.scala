package com.hope.akkaHttpSwagger.sample.add

import javax.ws.rs.Path

import scala.concurrent.ExecutionContext
import scala.concurrent.duration._
import akka.actor.ActorRef
import akka.http.scaladsl.server.Directives
import akka.pattern.ask
import akka.util.Timeout
import com.hope.akkaHttpSwagger.sample.DefaultJsonFormats
import com.hope.akkaHttpSwagger.sample.add.AddActor.{AddRequest, AddResponse}
import io.swagger.annotations._

@Api(value = "/add", produces = "application/json")
@Path("/add")
class AddService(addActor: ActorRef)(implicit executionContext: ExecutionContext)
  extends Directives with DefaultJsonFormats {

  implicit val timeout = Timeout(2.seconds)

  implicit val requestFormat = jsonFormat1(AddRequest)
  implicit val responseFormat = jsonFormat1(AddResponse)

  val route = add

  @ApiOperation(value = "Add integers", nickname = "addIntegers",
    httpMethod = "POST", response = classOf[AddResponse])
  @ApiImplicitParams(Array(
    new ApiImplicitParam(name = "body", value = "\"numbers\" to sum", required = true,
      dataTypeClass = classOf[AddRequest], paramType = "body")
  ))
  @ApiResponses(Array(
    new ApiResponse(code = 500, message = "Internal server error")
  ))
  def add =
    path("add") {
      post {
        entity(as[AddRequest]) { request =>
          complete { (addActor ? request).mapTo[AddResponse] }
        }
      }
    }

}