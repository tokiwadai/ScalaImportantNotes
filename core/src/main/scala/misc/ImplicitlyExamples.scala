package misc

// https://docs.scala-lang.org/tutorials/FAQ/context-bounds.html
object ImplicitlyExamples extends App {
  implicit val ordering = new Ordering[String]

  // Context bound
  def f[A : Ordering](a: A, b: A): String = implicitly[Ordering[A]].describe(a, b)

  val result1st = f("Hello", "World")
  println(s"First: ${result1st}")


  def f2[A](a: A, b: A)(implicit ord: Ordering[A]) = {
    import ord._
    describe(a, b)
  }

  val result2nd = f2("Hello", "World")
  println(s"Second: $result2nd")
}

class Ordering[Z] {
  def describe(x: Z, y: Z): String = {
    s"$x $y"
  }
}