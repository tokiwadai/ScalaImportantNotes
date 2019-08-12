package misc.ctci.chap2.chap2_1

import misc.ctci.chap2.Node

import scala.annotation.tailrec

object DeleteDupsBuffer extends App {
  def deleteDups(n: Node): Unit = {
    @tailrec
    def delete(someCurrNode: Option[Node], prevNode: Option[Node], set: Set[Int]): Unit = someCurrNode match {
      case None => Unit
      case Some(currNode) =>
          if (set.contains(currNode.data)) {
            prevNode.get.next = currNode.next
            delete(currNode.next, prevNode, set)
          } else
            delete(currNode.next, someCurrNode, set + currNode.data)
    }
    delete(Some(n), None, Set[Int]())
  }

  var node1 = new Node(1, None)
  var node2 = new Node(2, Some(node1))
  var node3= new Node(3, Some(node2))
  var node4= new Node(4, Some(node3))
  deleteDups(node4)
  println(node4)

  node1 = new Node(1, None)
  node2 = new Node(2, Some(node1))
  node3= new Node(1, Some(node2))
  node4= new Node(4, Some(node3))
  deleteDups(node4)
  println(node4)

  node1 = new Node(5, None)
  node2 = new Node(1, Some(node1))
  node3= new Node(1, Some(node2))
  node4= new Node(4, Some(node3))
  deleteDups(node4)
  println(node4)

  node1 = new Node(1, None)
  node2 = new Node(1, Some(node1))
  node3= new Node(1, Some(node2))
  node4= new Node(1, Some(node3))
  deleteDups(node4)
  println(node4)

}
