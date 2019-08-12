package concurrency.future.callback

import concurrency.sleep

import scala.concurrent.Future
import scala.util.{Failure, Success}

object FutureCallback1App {
  def main(args: Array[String]) = {
    val fcb1 = new FutureCallback1
    fcb1.run(new Session)
    sleep(2000)
    println("done")
  }
}

class FutureCallback1 {
  import scala.concurrent.ExecutionContext.Implicits.global
  def run(session: Session) = {
    val f: Future[List[String]] = Future {
      session.getRecentPosts
    }

    f onComplete {
      case Success(posts) => for (post <- posts) {
        println("Success...")
        println(s"post: $post")
      }
      case Failure(t) => println("An error has occured: " + t.getMessage)
    }
  }

}

class Session {
  def getRecentPosts: List[String] = {
    sleep(1000)
    List("A", "B", "C")
  }
}



