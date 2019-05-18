package com.fx_arb

import org.scalatest.{Matchers, WordSpecLike}

import scala.math.log

class FxArbTest extends WordSpecLike with Matchers {
  val str = "USD"

  "createBaseCombos1" should {
    "return correct values" in {
      val currencies = List("USD", "JPY", "EUR")
      println(s"currencies: $currencies")
      var rslt = FxArb.createBaseCombos(currencies, "USD")
      println(s"rslt: $rslt")
    }
  }

  "createBaseCombos2" should {
    "return correct values" in {
      val temp = List(1,2,3).toSet[Int].subsets.map(_.toList).toList
      println(s"temp: $temp")
      val currencies = List("USD", "JPY", "EUR")
      val combos = currencies.toSet[String].subsets.map{
        item =>
          val list = item.toList
          if (list.size > 1 && list.head == "USD") list else Nil
      }.toList.filter(_ != Nil)
      println(s"combos: $combos")

      println(s"currencies: $currencies")
      var rslt = FxArb.createBaseCombos(currencies, "USD")
      println(s"rslt: $rslt")

      val rstl2 = rslt.map(item => {
        println(s"item: $item")
        item.map(elem => {
          val nextIdx = item.indexOf(elem) + 1
          println(s"currIdx: ${item.indexOf(elem)} - nextIdx: $nextIdx - ${item.size}")
          if (nextIdx < item.size)
            Some((elem, item(nextIdx)))
          else None
        })
      })
      println(s"rslt2: $rstl2")
    }
  }

  "calculateArb" should {
    "return correct values" in {
      val filename = getClass.getResource("/fx2.txt").getFile
      val fileContent: String = FxArb.getFileContent(filename)

      println(s"fileContent $fileContent")

      val table: Map[String, Map[String, Double]] = FxArb.parseFileMap(fileContent)
      println(s"fxRates: ${table.mkString(System.lineSeparator)}")

      val currencies = table.keys.toList
      println(s"currencies: ${currencies}")

      var baseCombos = FxArb.createBaseCombos(currencies, "USD")
      println(s"rslt: $baseCombos")

      var arbRoutes = FxArb.calculateAllArbRoutes(baseCombos, table, "USD")
      println(s"arbRoutes: $arbRoutes")
    }
  }

  "getBestArbRoute" should {
    "return correct values" in {
      val filename = getClass.getResource("/fx2.txt").getFile
      val fileContent: String = FxArb.getFileContent(filename)

      println(s"fileContent $fileContent")

      val table: Map[String, Map[String, Double]] = FxArb.parseFileMap(fileContent)
      println(s"fxRates: ${table.mkString(System.lineSeparator)}")

      val currencies = table.keys.toList
      println(s"currencies: ${currencies}")

      var baseCombos = FxArb.createBaseCombos(currencies, "USD")
      println(s"rslt: $baseCombos")

      var arbRoutes = FxArb.calculateAllArbRoutes(baseCombos, table, "USD")
      println(s"arbRoutes: $arbRoutes")

      var bestArbRoute = FxArb.getBestArbRoute(arbRoutes)
      println(s"bestArbRoute: $bestArbRoute")
    }
  }

  "parseFileMap" should {
    "return correct values" in {
      val filename = getClass.getResource("/fx2.txt").getFile
      val fileContent: String = FxArb.getFileContent(filename)

      println(s"fileContent $fileContent")

      val table = FxArb.parseFileMap(fileContent)
      println(s"fxRates: ${table.mkString(System.lineSeparator)}")
      println(s"table: $table")

      table.keys.size should equal(4)
      table.values.size should equal(4)
      table.map(_._2.toList).flatten.size should equal(12)

      table.get("USD").get.get("JPY") should not equal (None)
      table.get("USD").get("JPY") should equal(-1 * log(102.4590))

      table.get("EUR").get("JPY") should not equal (None)
      table.get("EUR").get("JPY") should equal(-1 * log(131.7110))

      val eurKeys = table.get("EUR").get.keys
      eurKeys.size should equal(3)
      eurKeys.toSet should equal(Set("USD", "JPY", "BTC"))
      eurKeys.toSet.contains("EUR") should equal(false)
    }
  }

  "runEnd2End" should {
    "return correct values" in {
      val fileContent: String = FxArb.downloadAndparseFile(FxArb.url)
      //println(s"fileContent $fileContent")

      val table = FxArb.parseFileMap(fileContent)
      println(s"fxRates: ${table.mkString(System.lineSeparator)}")
      //println(s"table: $table")
    }
  }
}
