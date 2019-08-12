package misc.ctci.chap2.chap2_2

import misc.ctci.chap2.Node

// O(n) time & O(1) space
object ReturnK2lastRecursiveIterative extends App {
  def nth2Last(head: Option[Node], k: Int): Option[Node] = {
    var p1 = head
    var p2 = head

    for(_ <- 0 until k) {
      if (p1 == None) return None
      p1 = p1.get.next
    }
    println(s"p1 ${p1}")

    while (p1 != None) {
      p1 = p1.get.next
      p2 = p2.get.next
    }
    println(s"p2 ${p2}")

    return p2
  }

  var node1 = new Node(1, None)
  var node2 = new Node(2, Some(node1))
  var node3= new Node(3, Some(node2))
  var node4= new Node(4, Some(node3))
  var node5= new Node(5, Some(node4))
  val head = nth2Last(Some(node5), 4)
  print(head)
}
