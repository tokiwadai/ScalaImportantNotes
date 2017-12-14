package sample.stream.akka.io.customFlow

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Sink, Source}

import scala.collection.immutable.Seq

case class Element(id: Int, value: Int)

object SampleElements {

  val E11 = Element(1, 1)
  val E21 = Element(2, 1)
  val E31 = Element(3, 1)
  val E42 = Element(4, 2)
  val E52 = Element(5, 2)
  val E63 = Element(6, 3)

  val Ones = Seq(E11, E21, E31)
  val Twos = Seq(E42, E52)
  val Threes = Seq(E63)

  val All = Ones ++ Twos ++ Threes
  // implicit actor system
  implicit val system = ActorSystem("Sys")
  // implicit actor materializer
  implicit val materializer = ActorMaterializer()

  def main(args: Array[String]): Unit = {
    Source(SampleElements.All)
      .via(new AccumulateWhileUnchanged(_.value))
      .runWith(Sink.foreach(println))
  }
}
