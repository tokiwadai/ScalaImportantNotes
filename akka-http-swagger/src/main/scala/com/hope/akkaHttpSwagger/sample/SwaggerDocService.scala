package com.hope.akkaHttpSwagger.sample

import com.github.swagger.akka.SwaggerHttpService
import com.github.swagger.akka.model.Info
import com.hope.akkaHttpSwagger.sample.add.AddService
import com.hope.akkaHttpSwagger.sample.hello.HelloService
import io.swagger.models.ExternalDocs
import io.swagger.models.auth.BasicAuthDefinition

object SwaggerDocService extends SwaggerHttpService {
  override val apiClasses: Set[Class[_]] = Set(classOf[AddService], classOf[HelloService])
  override val host = "localhost:12345"
  override val info = Info(version = "1.0")
  override val externalDocs = Some(new ExternalDocs("Core Docs", "http://acme.com/docs"))
  override val securitySchemeDefinitions = Map("basicAuth" -> new BasicAuthDefinition())
  override val unwantedDefinitions = Seq("Function1", "Function1RequestContextFutureRouteResult")
}
