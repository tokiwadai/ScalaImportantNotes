package misc.ctci.chap4.chap4_1

case class Node[T](vertex: T, adjacent: List[Node[T]]) {
  import misc.ctci.chap4.chap4_1.States._
  var state: States.State = unvisited

  override def toString(): String = {
//    s"$vertex"
    s"$vertex-${adjacent.mkString}"
  }
}

//case class Graph[T](vertices: List[T])

object States {
  type State = String
  val visited: State = "visited"
  val visiting: State = "visiting"
  val unvisited: State = "unvisited"
}

object RouteBtwNodes extends App {
  import misc.ctci.chap4.chap4_1.States._
  import java.util

  def search[T](start: Node[T], end: Node[T]): Boolean = {
    if (start == end) return true

    val q = new util.LinkedList[Node[T]]
    start.state = visiting
    q.add(start)
    var u: Node[T] = null
    while (!q.isEmpty) {
      u = q.removeFirst
      if (u != null) {
        for (v <- u.adjacent) {
          if (v.state == unvisited) {
            if (v == end) return true
            else {
              v.state = visiting
              q.add(v)
            }
          }
        }
        u.state = visited
      }
    }
    return false
  }

  var temp5 = Node("f", Nil)
  var temp4 = Node("e", List(temp5))
  var temp1 = Node("b", Nil)
  var temp2 = Node("c", Nil)
  var temp3 = Node("d", List(temp4))
  var temp0 = Node("a", List(temp1, temp2, temp3))

  val start = temp3
  val end = temp2
  val connected = search(start, end)
  println(s"$start and $end: $connected")
}