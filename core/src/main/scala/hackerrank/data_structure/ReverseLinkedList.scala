package hackerrank.data_structure

import hackerrank.data_structure.SinglyLinkedList._

import scala.annotation.tailrec

object ReverseLinkedList extends App {
  import java.io._

  // Complete the reverse function below.
  /*
   * For your reference:
   *
   * SinglyLinkedListNode {
   *     data: Int
   *     next: SinglyLinkedListNode
   * }
   *
   */
  def reverse(llist: SinglyLinkedListNode): SinglyLinkedListNode = {
    @tailrec
    def rec(list: SinglyLinkedListNode, revLL: SinglyLinkedListNode): SinglyLinkedListNode = list.next match {
      case null => new SinglyLinkedListNode(list.data, revLL)
      case _ => rec(list.next, new SinglyLinkedListNode(list.data, revLL))
    }
    if (llist == null) llist
    else
      rec(llist.next, new SinglyLinkedListNode(llist.data, null))
  }

  val printWriter = new PrintWriter(System.out)

  val sll3 = new SinglyLinkedListNode(4)
  val sll2 = new SinglyLinkedListNode(3, sll3)
  val sll1 = new SinglyLinkedListNode(19, sll2)
  val sll0 = new SinglyLinkedListNode(20, sll1)

  printSinglyLinkedList(sll0, " ", printWriter)
  printWriter.println
  val llist1 = reverse(sll0)
  printSinglyLinkedList(llist1, " ", printWriter)
  printWriter.println
  printWriter.close()
}
