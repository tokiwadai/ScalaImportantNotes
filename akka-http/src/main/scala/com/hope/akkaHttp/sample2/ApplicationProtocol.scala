package com.hope.akkaHttp.sample2

import spray.json.DefaultJsonProtocol

final case class Application(jarUri: String, className: String, args: List[String])

trait ApplicationProtocol extends DefaultJsonProtocol {
  implicit val appFormat = jsonFormat3(Application)
}
