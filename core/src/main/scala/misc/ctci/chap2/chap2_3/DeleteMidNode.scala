package misc.ctci.chap2.chap2_3

import misc.ctci.chap2.Node

object DeleteMidNode extends App {
  def deleteMidNode(n: Option[Node]): Boolean = {
    if (n == None || n.get.next == None)
      return false

    val nex = n.get.next
    n.get.data = nex.get.data
    n.get.next = nex.get.next

    return true
  }

  var node1 = new Node(1, None)
  var node2 = new Node(2, Some(node1))
  var node3= new Node(3, Some(node2))
  var node4= new Node(4, Some(node3))
  var node5= new Node(5, Some(node4))
  var node6= new Node(6, Some(node5))

  var node2Del = node6.next.get.next.get.next
  println(s"node2del: ${node2Del.get.data}")
  deleteMidNode(node2Del)
  println(node6)

  node1 = new Node(1, None)
  node2 = new Node(2, Some(node1))
  node3= new Node(3, Some(node2))
  node4= new Node(4, Some(node3))
  node5= new Node(5, Some(node4))
  node6= new Node(6, Some(node5))
  node2Del = node6.next.get.next
  println(s"node2del: ${node2Del.get.data}")
  deleteMidNode(node2Del)
  println(node6)
}
