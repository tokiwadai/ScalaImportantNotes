package misc.ctci.chap4.an_intro

object Traversal extends App {
  case class Tree[T](val data: T, val left: Tree[T], val right: Tree[T])

  def visit[T](node: Tree[T]) =
    println(node.data)

  // Left - current node - Right
  def inOrderTraversal[T](node: Tree[T]): Unit =
    if(node != null) {
      inOrderTraversal(node.left)
      visit(node)
      inOrderTraversal(node.right)
    }

  // current node - Left - Right
  def preOrderTraversal[T](node: Tree[T]): Unit =
    if(node != null) {
      visit(node)
      preOrderTraversal(node.left)
      preOrderTraversal(node.right)
    }

  // Left - Right - current node
  def postOrderTraversal[T](node: Tree[T]): Unit =
    if(node != null) {
      postOrderTraversal(node.left)
      postOrderTraversal(node.right)
      visit(node)
    }

  val node1 = Tree(1, null, null)
  val node2 = Tree(2, null, null)
  val node3 = Tree(3, node1, node2)
  val node4 = Tree(4, null, null)
  val root = Tree(5,node3,node4)

  println("inOrderTraversal")
  inOrderTraversal(root)
  println("preOrderTraversal")
  preOrderTraversal(root)
  println("postOrderTraversal")
  postOrderTraversal(root)
}
