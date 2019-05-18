package misc

object CallByNameValueSample extends App {
  def calculate() = {
    println("calculating...")
    1
  }

  def callByValue(x: Int) = {
    println(s"x1 = $x")
    println(s"x2 = $x")
  }

  def callByName(x: => Int) ={
    println(s"x1 = $x")
    println(s"x2 = $x")
  }

  println("call by Value")
  callByValue(calculate)
  println("call by Name")
  callByName(calculate)
}
