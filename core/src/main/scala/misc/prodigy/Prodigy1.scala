package misc.prodigy

object Prodigy1 extends App {
  def getLeet(string: String): String = {
    val a = Set('a', 'A')
    val e = Set('e', 'E')
    val i = Set('i','I')
    val o = Set('o', 'O')
    val s = Set('s','S')
    val t = Set('t','T')
    val d = Set('b', 'D')
    def replace(c: Char) =
      if (a.contains(c)) '4'
      else if (e.contains(c)) '3'
      else if (i.contains(c)) '1'
      else if (o.contains(c)) '0'
      else if (s.contains(c) || d.contains(c)) '5'
      else if (t.contains(c)) '7'
      else c

    string.map(replace)
  }

  var string1 = "Let's have some fun."
  var string = getLeet(string1)
  println(s"$string1 => $string")
  assert(string == "L37'5 h4v3 50m3 fun.")

  string1 = "C is for cookie, that's good enough for me"
  string = getLeet(string1)
  println(s"$string1 => $string")
  assert(string == "C 15 f0r c00k13, 7h47'5 g00d 3n0ugh f0r m3")

  string1 = "By the power of Grayskull!"
  string = getLeet(string1)
  println(s"$string1 => $string")
  assert(string == "By 7h3 p0w3r 0f Gr4y5kull!")
}
