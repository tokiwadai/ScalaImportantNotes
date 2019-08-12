package misc.ctci.chap2.chap2_1

import misc.ctci.chap2.Node

// Not functional
object DeleteDupsBuffer2 extends App {
  def deleteDups(n: Option[Node]) = {
    var currNode = n
    var set = Set[Int]()
    var somePrevNode: Option[Node] = None
    while (currNode != None) {
      if (set.contains(currNode.get.data))
        somePrevNode.get.next = currNode.get.next
      else {
        set = set + currNode.get.data
        somePrevNode = currNode
      }
      currNode = currNode.get.next
    }
  }

  var node1 = new Node(1, None)
  var node2 = new Node(2, Some(node1))
  var node3= new Node(3, Some(node2))
  var node4= new Node(4, Some(node3))
  deleteDups(Some(node4))
  println(node4)

  node1 = new Node(1, None)
  node2 = new Node(2, Some(node1))
  node3= new Node(1, Some(node2))
  node4= new Node(4, Some(node3))
  deleteDups(Some(node4))
  println(node4)

  node1 = new Node(5, None)
  node2 = new Node(1, Some(node1))
  node3= new Node(1, Some(node2))
  node4= new Node(4, Some(node3))
  deleteDups(Some(node4))
  println(node4)}
