package hackerrank.data_structure

import java.io.PrintWriter

import hackerrank.data_structure.SinglyLinkedList._

object DeleteNodeLinkedList extends App {


  // Complete the deleteNode function below.

  /*
   * For your reference:
   *
   * SinglyLinkedListNode {
   *     data: Int
   *     next: SinglyLinkedListNode
   * }
   *
   */
  def deleteNode(llist: SinglyLinkedListNode, position: Int): SinglyLinkedListNode = {
    if (llist == null) return llist

    var next = llist.next
    var idx = 0
    if (position == 0) {
      llist.data = next.data
      llist.next = next.next
    } else
      while (next != null && idx <= position) {
        idx += 1
        println(s"idx [$idx] [${next.data}]")
        if (next.next != null && next.next.next == null) { // deleting last node
          next.next = null
        } else if (idx == position) {
          next.data = next.next.data
          next.next = next.next.next
        }
        next = next.next
      }
    llist
  }

  def deleteNode2(llist: SinglyLinkedListNode, position: Int): SinglyLinkedListNode = {
    if (llist == null) return llist

    var next = llist.next
    var idx = 0
    if (position == 0) {
      llist.data = next.data
      llist.next = next.next
    } else
      while (next != null && idx <= position) {
        idx += 1
        println(s"idx [$idx] [${next.data}]")
        if (idx == position && next.next != null) {
          next.data = next.next.data
          next.next = next.next.next
        }
        next = next.next
      }

    llist
  }

  // Does NOT work!!
  def deleteNode3(llist: SinglyLinkedListNode, position: Int): SinglyLinkedListNode = {
    def delete(list2Del: SinglyLinkedListNode, idx: Int, acc: SinglyLinkedListNode): SinglyLinkedListNode = list2Del match {
      case null => acc
      case n =>
        if (idx == position) {
          println(s"idx [$idx] [${n.data}]")
          n.data = n.next.data
          n.next = n.next.next
        }
        delete(n.next, idx+1, new SinglyLinkedListNode(n.data, acc))
    }
    if (llist == null) return llist
    delete(llist, 0, null)
  }

  val printWriter = new PrintWriter(System.out)

  val sll3 = new SinglyLinkedListNode(4)
  val sll2 = new SinglyLinkedListNode(3, sll3)
  val sll1 = new SinglyLinkedListNode(19, sll2)
  val sll0 = new SinglyLinkedListNode(20, sll1)
  val llist = new SinglyLinkedList(sll0, sll1)

  val position = 2
  val llist1 = deleteNode3(llist.head, position)

  printSinglyLinkedList(llist1, " ", printWriter)
  printWriter.println
  printWriter.close
}
