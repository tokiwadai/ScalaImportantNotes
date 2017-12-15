package sample.stream.multipleFilesReader

import java.io.File

import akka.NotUsed
import akka.stream.scaladsl.Flow

import scala.concurrent.Future

trait MultipleFilesStreamReaderUtils {

  val directory = "/Users/oWen/Documents/scalaProjects/scalaPrinciples/ImportantNotes/" +
    "akka/src/main/resources/files"

  val readCesiumIdFileFlow: Flow[File, Iterator[String], NotUsed] = Flow[File]
    .map { file => readCesiumIdFile(file) }

  def readCesiumIdFile(file: File): Iterator[String] =
    scala.io.Source.fromFile(s"$directory/${file.getName}", "UTF-8").getLines()

  val fileNames: List[File] = getListOfFiles(directory)

  def getListOfFiles(dir: String): List[File] = {
    val d = new File(dir)
    if (d.exists && d.isDirectory) {
      d.listFiles.filter(_.isFile).toList
    } else {
      List[File]()
    }
  }

  import scala.concurrent.ExecutionContext.Implicits.global
  import scala.concurrent.duration._

  val queryCesiumByBatches: Flow[String, Seq[String], NotUsed] = Flow[String]
    .groupedWithin(1000, 100 millisecond)
    .mapAsync(4)(x =>
      Future[Seq[String]] {
        synchronized {
          println("=============")
          println(s"queryCesiumByBatches: $x")
          println("ooooooooooooo")
          x
        }
      }
    )

  val analyzeResult: Flow[Seq[String], Unit, NotUsed] = Flow[Seq[String]]
    .map {
      x => println(s"analyzeResult: x.size [${x.size}] x [$x]")
    }
}