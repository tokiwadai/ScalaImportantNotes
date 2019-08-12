package misc.ctci.chap2.chap2_2

import misc.ctci.chap2.Node

// O(n) time & O(n) space
object ReturnK2lastRecursive extends App {
  def printKth2Last(head: Option[Node], k: Int): Int = {
    if (head == None) return 0

    val index = printKth2Last(head.get.next, k) + 1
    if (index == k) {
      println(s"${k}th to the last node is ${head.get.data}")
    }
    println(s"index $index == $head")
    return index
  }

  var node1 = new Node(1, None)
  var node2 = new Node(2, Some(node1))
  var node3= new Node(3, Some(node2))
  var node4= new Node(4, Some(node3))
  var node5= new Node(5, Some(node4))
  printKth2Last(Some(node5), 4)
}
