package test.traits

trait MyTrait

trait MyTrait1  {
  def myFunc = {
    println("MyTrait1.myFunc")
  }

  myFunc
}

trait MyTrait2 extends MyTrait1 {
  override def myFunc = {
    println("MyTrait2.myFunc")
  }
}

object MyTraitsApp extends App with MyTrait2 {
  println("starting...")
}
