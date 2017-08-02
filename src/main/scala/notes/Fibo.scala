package notes

object Fibo {
  def fib(n:Int) = fibo(n, 0, 1)
  def fibo(n:Int, a:Int, b:Int):Int = n match {
    case 0 => a
    case _ => fibo(n-1, b, a+b)
  }
  def main(args:Array[String]):Unit = {
    println(fib(10))
  }
}