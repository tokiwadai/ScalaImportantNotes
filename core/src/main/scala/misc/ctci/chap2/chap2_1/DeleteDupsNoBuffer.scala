package misc.ctci.chap2.chap2_1

import misc.ctci.chap2.Node

import scala.annotation.tailrec

//TODO: NOT yet working!!
object DeleteDupsNoBuffer extends App {
  def deleteDups(n: Node): Unit = {
    def delete2(someRunnerNd: Option[Node], data: Int): Unit = someRunnerNd.get.next match {
      case None =>
//        println(someRunnerNd)
      case Some(runnerNd) =>
        println(s"runnerNd: $runnerNd")
        if (runnerNd.data  == data) {
          runnerNd.next = runnerNd.next.get.next
        }
        delete2(runnerNd.next, data)
    }

    @tailrec
    def delete(someCurrNode: Option[Node]): Unit = someCurrNode match {
      case None => Unit
      case Some(currNode) =>
        println(s"currNode.data: ${currNode.data} == ${currNode}")
        val runner = currNode
        //println(runner)
        delete2(Some(runner), currNode.data)
        delete(currNode.next)
    }
    delete(Some(n))
  }

  var node1 = new Node(1, None)
  var node2 = new Node(2, Some(node1))
  var node3= new Node(3, Some(node2))
  var node4= new Node(4, Some(node3))
  //println(node4)
  //var rslt = deleteDups(node4)
  //println(node4)

  node1 = new Node(1, None)
  node2 = new Node(2, Some(node1))
  node3= new Node(1, Some(node2))
  node4= new Node(4, Some(node3))
//  deleteDups(node4)
//  println(node4)

  node1 = new Node(5, None)
  node2 = new Node(1, Some(node1))
  node3= new Node(1, Some(node2))
  node4= new Node(4, Some(node3))
  deleteDups(node4)
  println(node4)
//
//  node1 = new Node(1, None)
//  node2 = new Node(1, Some(node1))
//  node3= new Node(1, Some(node2))
//  node4= new Node(1, Some(node3))
//  deleteDups(node4)
//  println(node4)

}
