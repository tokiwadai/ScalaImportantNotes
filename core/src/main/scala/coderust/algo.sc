package coderust

object algo {


  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  
  5 to 6                                          //> res0: scala.collection.immutable.Range.Inclusive = Range(5, 6)
  5 until 6                                       //> res1: scala.collection.immutable.Range = Range(5)

  def binarySearch(array: Array[Int], key: Int, low: Int, high: Int): Int = {
    val mid = low + (high - low) / 2

    if (low > high) -1
    else if (array(mid) == key) mid
    else if (array(mid) < key) binarySearch(array, key, mid + 1, high)
    else binarySearch(array, key, low, mid - 1)
  }                                               //> binarySearch: (array: Array[Int], key: Int, low: Int, high: Int)Int

  def binarySearch2(array: Array[Int], key: Int): Int = {
    def helper(array: Array[Int], key: Int, low: Int, high: Int): Int = {
      if (low <= high) {
        val mid = low + (high - low) / 2
        if (array(mid) == key) mid
        else if (array(mid) < key) helper(array, key, mid + 1, high)
        else helper(array, key, low, mid - 1)
      } else
        -1
    }
    val low: Int = 0
    val high: Int = array.size - 1

    helper(array, key, low, high)
  }                                               //> binarySearch2: (array: Array[Int], key: Int)Int

  val array: Array[Int] = Array(1, 2, 3, 4, 7, 8, 78, 90)
                                                  //> array  : Array[Int] = Array(1, 2, 3, 4, 7, 8, 78, 90)
  val key = 40                                    //> key  : Int = 40
  val res = binarySearch(array, key, 0, array.size - 1)
                                                  //> res  : Int = -1
  println(s"finding $key index: $res")            //> finding 40 index: -1

  val res2 = binarySearch2(array, key)            //> res2  : Int = -1
  println(s"finding $key index: $res2")           //> finding 40 index: -1

  val arr1 = Array(1, 2, 3)                       //> arr1  : Array[Int] = Array(1, 2, 3)
  val arr2 = arr1                                 //> arr2  : Array[Int] = Array(1, 2, 3)
  (arr2.foreach(print))                           //> 123

  arr1.update(1, 4)

  (arr1.foreach(print))                           //> 143
  (arr2.foreach(print))                           //> 143

  val (passed, failed) = List(49, 58, 76, 82, 88, 90) partition (_ > 60)
                                                  //> passed  : List[Int] = List(76, 82, 88, 90)
                                                  //| failed  : List[Int] = List(49, 58)
  val wordList = List("scala", "akka", "play framework", "sbt", "typesafe")
                                                  //> wordList  : List[String] = List(scala, akka, play framework, sbt, typesafe)
                                                  //| 
  val tweet = "This is an example tweet talking about scala and sbt."
                                                  //> tweet  : String = This is an example tweet talking about scala and sbt.

  (wordList.foldLeft(false)(_ || tweet.contains(_)))
                                                  //> res2: Boolean = true
  wordList.exists(tweet.contains)                 //> res3: Boolean = true
  
}