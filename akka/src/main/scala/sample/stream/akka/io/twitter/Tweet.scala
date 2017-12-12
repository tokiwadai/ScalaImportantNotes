package sample.stream.akka.io.twitter

case class Author(handle: String)

case class Hashtag(name: String)

case class Tweet(author: Author, body: String) {
  def hashtags: Set[Hashtag] = body
    .split(" ")
    .collect {
      case t if t.startsWith("#") => Hashtag(t.replaceAll("[^#\\w]", ""))
    }.toSet
}

