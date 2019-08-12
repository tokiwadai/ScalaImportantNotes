package misc.ctci.chap3

import java.util.EmptyStackException

// FIFO
class MyQueue[A] {
  private class QueueNode[A](val data: A) {
    var next: Option[QueueNode[A]] = None
  }

  private var first: Option[QueueNode[A]] = None
  private var last: Option[QueueNode[A]] = None

  def remove(): A = {
    if (first == None) throw new EmptyStackException
    val item = first.get.data
    first = first.get.next
    if (first == None)
      last = None
    item
  }

  def add(item: A) = {
    val node = new QueueNode(item)
    if (last != None)
      last.get.next = Some(node)
    last = Some(node)
    if (first == None)
      first = last
  }

  def peek(): A = {
    if (first == None) throw new EmptyStackException
    first.get.data
  }

  def isEmpty(): Boolean = (first == None)
}
