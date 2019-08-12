package com

object MyTest extends App {
  val myOpt: Option[String] = None
  print(myOpt.getOrElse("None"))
//  myOpt match {
//    case Some(string) => print("some string")
//  }
}
