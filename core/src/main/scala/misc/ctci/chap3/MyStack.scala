package misc.ctci.chap3

import java.util.EmptyStackException

// LIFO
class MyStack[A] {
  private class StackNode[A](val data: A) {
    var next: Option[StackNode[A]] = None
  }

  private var top: Option[StackNode[A]] = None

  def pop(): A = {
    if (top == None) throw new EmptyStackException
    val item = top.get.data
    top = top.get.next
    item
  }

  def push(item: A) = {
    val node = new StackNode(item)
    node.next = top
    top = Some(node)
  }

  def peek(): A = {
    if (top == None) throw new EmptyStackException
    top.get.data
  }

  def isEmpty(): Boolean = (top == None)
}
