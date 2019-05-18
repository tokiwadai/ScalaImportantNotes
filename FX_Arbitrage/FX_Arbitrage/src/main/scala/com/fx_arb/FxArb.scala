package com.fx_arb

import java.io._
import java.net.{HttpURLConnection, URL}

import org.apache.commons.lang3.StringUtils

import scala.io.Source

object FxArb {
  val INFINITY = Double.PositiveInfinity
  val url = "https://fx.priceonomics.com/v1/rates/"
  val sourceCurrency = "USD"


  def main(args: Array[String]) = {
    val fileContent: String = FxArb.downloadAndparseFile(url)
    //println(s"fileContent $fileContent")

    val table = FxArb.parseFileMap(fileContent)
    println(s"fxRates: ${table.mkString(System.lineSeparator)}")
    //println(s"table: $table")
    val currencies = table.keys.toList
    println(s"currencies: ${currencies}")

    val baseCombos = FxArb.createBaseCombos(currencies, sourceCurrency)
    println(s"rslt: $baseCombos")

    val arbRoutes: List[(List[String], Double)] = FxArb.calculateAllArbRoutes(baseCombos, table, sourceCurrency)
    println(s"arbRoutes: $arbRoutes")

    val bestArbRoute = FxArb.getBestArbRoute(arbRoutes)
    println(s"bestArbRoute: $bestArbRoute")

    if (bestArbRoute._2 > 1.0) {
      println(s"FX Arbitrage FOUND for base currency [$sourceCurrency] Arb route - value [${bestArbRoute._1.mkString(" -> ")} -> USD]-[${bestArbRoute._2}]!")
    } else {
      println(s"FX Arbitrage NOT Found for base currency [$sourceCurrency]...")
    }
  }

  def getBestArbRoute(allArbRoutes: List[(List[String], Double)]) = {
    allArbRoutes.foldLeft((List[String](), 0.0)) {
      (resultingTuple, currentArbValue) => {
//        println(s"currentRoute - currentArbValue = ${currentArbValue._1} - ${currentArbValue._2}")
        if (currentArbValue._2 > resultingTuple._2) {
          (currentArbValue._1, currentArbValue._2)
        } else {
          (resultingTuple._1, resultingTuple._2)
        }
      }
    }
  }

  def calculateAllArbRoutes(routes: List[List[String]], fxTable: Map[String, Map[String, Double]], sourceCurrency: String) = {
    def calculate(route: List[String]): (List[String], Double)= {
      val rslt: (List[String], Double) = route.foldLeft((List[String](), 1.0)) {
        (resultingTuple, currentCurr) => {
          val nextIdx = route.indexOf(currentCurr) + 1
          val rate =
            if (nextIdx == route.size) {
              val r = fxTable(currentCurr)(sourceCurrency)
//              println(s"base-quote [${currentCurr}-${sourceCurrency}] = [$r]")
              r
            } else {
              val r = fxTable(currentCurr)(route(nextIdx))
//              println(s"base-quote [${currentCurr}-${route(nextIdx)}] = [$r]")
              r
            }
          (resultingTuple._1 :+ currentCurr, resultingTuple._2 * rate)
        }
      }
//      println(s"route: [$route] - ${rslt}")
      rslt
    }

    val result: List[(List[String], Double)] = routes.map(
      item => {
        calculate(item)
      })
    result
  }

  def createBaseCombos(currencies: List[String], sourceCurrency: String): List[List[String]] = {
    val combos = currencies.toSet[String].subsets.map{
      item =>
        val list = item.toList
        if (list.size > 0 && list.head == sourceCurrency) list else Nil
    }.toList.filter(_ != Nil)
//    println(s"combos: $combos")
    combos.map(createCombinations(currencies, _)).flatten
  }

  def createCombinations(currencies: List[String], baseCombo: List[String]) = {
    currencies.foldLeft((List[List[String]](), StringUtils.EMPTY)) {
      (resultingTuple, currentCurr: String) =>
        val temp: List[String] =
            if (!baseCombo.contains(currentCurr)) baseCombo :+ currentCurr
            else Nil

        temp match {
          case Nil => (resultingTuple._1, currentCurr)
          case _ :: _ => (resultingTuple._1 :+ temp, currentCurr)
        }
    }._1
  }

  def downloadFile(urlString: String): String = {
    val url = new URL(urlString)
    val connection = url.openConnection().asInstanceOf[HttpURLConnection]
    connection.setRequestMethod("GET")

    val in: InputStream = connection.getInputStream
    val resource = new File(getClass.getResource(s"/com/fx_arb/${getClass.getSimpleName}.class").getFile)
    val tmpDir = new File(s"${resource.getParent}/tmp")
    val file = new File(s"${tmpDir.getAbsolutePath}/downloadedFile1.txt")
    try {
      if (!tmpDir.exists()) tmpDir.mkdir
      val out: OutputStream = new BufferedOutputStream(new FileOutputStream(file))
      val byteArray = Stream.continually(in.read).takeWhile(-1 !=).map(_.toByte).toArray
      out.write(byteArray)
      out.flush
      file.getAbsolutePath
    } finally {
      in.close
    }
  }

  def getFileContent(filename: String) =
    Source.fromFile(filename).mkString
      .replace("{", StringUtils.EMPTY)
      .replace("}", StringUtils.EMPTY)
      .replace("\"", StringUtils.EMPTY)

  def downloadAndparseFile(url: String) = {
    val fileName = downloadFile(url)
    println(s"file is saved as:[$fileName]")
    getFileContent(fileName)
  }

  def parseFileMap(fileContent: String): Map[String, Map[String, Double]] = {
    def insertMap(map: Map[String, Map[String, Double]], base: String,
                  quote: String, rate: Double): Map[String, Map[String, Double]] = {
      def add(base: String, quote: String, rate: Double,
              map: Map[String, Double]): Map[String, Double] = {
        // no need to add when base and quote currencies are the same
        if (base == quote) map
        else map + (quote -> rate)
      }

      val mapToAdd = map.get(base)
      val mapRst = mapToAdd match {
        case None =>
          //println(s"Init quote [$quote] for base [$base]")
          add(base, quote, rate, Map())
        case Some(quoteMap) =>
          quoteMap.get(quote) match {
            case None =>
            //println(s"Quote [$quote] Not found for base [$base]")
            case existingRate =>
            //println(s"Found existing quote [$existingRate] found for base [$base], will replace...")
          }
          add(base, quote, rate, quoteMap)
      }
      map + (base -> mapRst)
    }

    val list: Array[String] = fileContent.split(",")
    //println(s"list: ${list.mkString(",")}")
    val rateList: Array[(String, String, Double)] = list.map(item => {
      val fxRatePair = item.split(":")
      val currencyPair = fxRatePair.head.split("_")
      val base = currencyPair.head.trim
      val quote = currencyPair.tail.head.trim
      val rate = fxRatePair.tail.head
      (base, quote, rate.toDouble)
    })

    val res = rateList.toList.foldLeft((Map[String, Map[String, Double]](),
      (StringUtils.EMPTY, StringUtils.EMPTY, 0.0))) {
      (resultingTuple, currentItem) =>
        val (b, q, rate) = currentItem
        (insertMap(resultingTuple._1, b, q, rate), (b, q, rate))
    }
    //println(s"res_2: ${res._2}")
    res._1
  }
}
