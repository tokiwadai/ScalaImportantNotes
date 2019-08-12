package misc.ctci.chap2.chap2_1

import misc.ctci.chap2._

// Not functional
object DeleteDupsNoBuffer2 extends App {
  def deleteDups(n: Option[Node]): Unit = {
    var currNode = n
    while (currNode != None) {
      var runner = currNode
      while (runner.get.next != None) {
        if (runner.get.next.get.data == currNode.get.data)
            runner.get.next = runner.get.next.get.next
        else
          runner = runner.get.next
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
  println(node4)
}
