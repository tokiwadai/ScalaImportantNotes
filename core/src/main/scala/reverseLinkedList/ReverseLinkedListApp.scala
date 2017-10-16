package reverseLinkedList

object ReverseLinkedListApp extends App {
  case class LinkedListNode[T](item: T, next: LinkedListNode[T]) {
    def hasNext: Boolean = {
      (next != null)
    }

    private def print(node: LinkedListNode[T]): String = {
      if (node.hasNext) node.item.toString + ", " + print(node.next)
      else node.item.toString
    }

    override def toString(): String = {
      print(this)
    }
  }

  def rec(listToDo: LinkedListNode[Int], reverseList: LinkedListNode[Int]): LinkedListNode[Int] = {
    if (listToDo.hasNext)
      rec(listToDo.next, LinkedListNode(listToDo.item, reverseList))
    else
      LinkedListNode(listToDo.item, reverseList)
  }

  def reverse(link: LinkedListNode[Int]): LinkedListNode[Int] = {
    val listToDo = link.next
    val reverseList = LinkedListNode(link.item, null)
    val res = rec(listToDo, reverseList)
    res
  }

  val node4 = LinkedListNode[Int](28, null)
  val node3 = LinkedListNode[Int](21, node4)
  val node2 = LinkedListNode[Int](14, node3)
  val node = LinkedListNode[Int](7, node2)
  println(s"node: $node")

  val res = reverse(node)
  println(s"reversed node: $res")
}