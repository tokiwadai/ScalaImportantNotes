package misc.ctci.chap2.chap2_2

import misc.ctci.chap2.Node

// TODO: Does NOT work!!
object ReturnK2lastRecursive2 extends App {
  def printKth2Last(head: Option[Node], k: Int, counter: Int): Int = head match {
    case None => 0
    case Some(node) =>
      if (k == counter)
        println(s"${k}th to the last node is ${head.get.data}")
      printKth2Last(node.next, k, counter + 1)
    }

  var node1 = new Node(4, None)
  var node2 = new Node(3, Some(node1))
  var node3= new Node(2, Some(node2))
  var node4= new Node(1, Some(node3))
  printKth2Last(Some(node4), 1, 0)
}
