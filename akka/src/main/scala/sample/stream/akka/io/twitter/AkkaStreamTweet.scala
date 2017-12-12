package sample.stream.akka.io.twitter

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Sink, Source}

object AkkaStreamTweet {
  val akkaTag = Hashtag("#akka")

  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem("reactive-tweets")
    implicit val materializer = ActorMaterializer()

    val tweets: Source[Tweet, NotUsed] = Source(TwitterClient.retrieveTweets("#Akka"))
    //    val source = Source(() => TwitterClient.retrieveTweets("#Akka"))

    val authors: Source[Author, NotUsed] =
      tweets
        .filter(_.hashtags.contains(akkaTag))
        .map(_.author)

    authors.runWith(Sink.foreach(println))
  }
}
