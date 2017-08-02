package coinFlip

import scala.annotation.tailrec
import scala.io.StdIn
import scala.util.Random

object CoinFlipApp {
  case class GameState(count: Int, wins: Int)
  val randomizer = new Random

  def main(args: Array[String]) = {
    println(s"[h]ead, [t]ail or [q]uit:")
    coinTossRecursive(GameState(0, 0))
  }

  def win(input: String, flipRes: String): (Boolean, Boolean) = {
    input match {
      case "H" | "T" => {
        if (flipRes == input) {
          (true, true)
        } else {
          (false, true)
        }
      }
      case _ => (false, false)
    }
  }

  @tailrec
  def coinTossRecursive(gameState: GameState): Unit = {
    val input = StdIn.readLine.toUpperCase()
    val flipResult = flipCoin
    val gameResult = win(input, flipResult)
    if (gameResult._1) {
      val gs = GameState(gameState.count + 1, gameState.wins + 1)
      printResult(s"WIN!", gs)
      coinTossRecursive(gs)
    } else {
      if (gameResult._2) {
        val gs = GameState(gameState.count + 1, gameState.wins)
        printResult(s"Loose!", gs)
        coinTossRecursive(gs)
      } else printResult("Game over! Final result: ", gameState)
    }
  }

  @tailrec
  private def coinTossRecursive1(gameState: GameState): Unit = {
    val input = StdIn.readLine.toUpperCase()
    input match {
      case "H" | "T" => {
        val flipped = flipCoin
        if (flipped == input) {
          val gs = GameState(gameState.count + 1, gameState.wins + 1)
          printResult(s"WIN! flipResult: $flipped", gs)
          coinTossRecursive1(gs)
        } else {
          val gs = GameState(gameState.count + 1, gameState.wins)
          printResult(s"Loose! flipResult: $flipped", gs)
          coinTossRecursive1(gs)
        }
      }
      case _ => printResult("Game over! Final result: ", gameState)
    }
  }

  def printResult(winLoose: String, gameState: GameState) = {
    println(s"$winLoose, # of games: ${gameState.count}, # of wins: ${gameState.wins}")
  }

  def flipCoin(): String = {
    val i: Int = randomizer nextInt 2
    val res = i match {
      case 0 => "H"
      case 1 => "T"
    }
    res
  }
}