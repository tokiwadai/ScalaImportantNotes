package misc.ctci.chap1.chap1_2

object Permutation1 extends App {
  var list1 = List('a' , 'b', 'c')
  var list2 = List('a' , 'b', 'c')
  println(list1 == list2)

  list1 = List('a' , 'b', 'c')
  list2 = List('b' , 'a', 'c')
  println(list1 == list2)

  def checkPermutation(stringA: String, stringB: String) ={
    def sort(string: String) = string.toCharArray.toSet

    if (stringA.size != stringB.size) false
    else sort(stringA) == sort(stringB)
  }

  var string1 = "abc"
  var string2 = "abc"
  println(s"$string1 vs $string2: ${checkPermutation(string1,string2)}")

  string1 = "abc"
  string2 = "cab"
  println(s"$string1 vs $string2: ${checkPermutation(string1,string2)}")

  string1 = "abce"
  string2 = "abcd"
  println(s"$string1 vs $string2: ${checkPermutation(string1,string2)}")

  string1 = "abce"
  string2 = "abcdf"
  println(s"$string1 vs $string2: ${checkPermutation(string1,string2)}")
}
