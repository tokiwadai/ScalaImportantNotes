package misc.ctci.chap4.an_intro


case class TreeNode[T](val data: T, val adjacent: List[TreeNode[T]]) {
  var visited = false
}

object BvsD_FirstSearch extends App {
  def visit[T](node: TreeNode[T]) = println(node.data)

  def dfsSearch(node: TreeNode[Char], char2search: Char): Char =
    if (node == null) return '\0'
    else {
      visit(node)
      node.visited = true
      for (node <- node.adjacent)
        if (node.visited == false && node.data != char2search)
          dfsSearch(node, char2search)
        else {
          println(s"Found it!! [${node.data}]")
          return node.data
        }
      return '\0'
    }

  def bfsSearch(node: TreeNode[Char], char2search: Char): Unit = {
    import scala.collection.mutable.Queue

    val queue = new Queue[TreeNode[Char]]
    node.visited = true
    queue.enqueue(node)

    while (!queue.isEmpty) {
      val r = queue.dequeue
      visit(r)
      if (r.data == char2search) {
        println(s"Found it!! [${r.data}]")
        return
      }
      for (n <- r.adjacent)
        if (n.visited == false) {
          n.visited = true
          queue.enqueue(n)
        }
    }
  }

  var nodeF = TreeNode('f', Nil)
  var nodeE = TreeNode('e', List(nodeF))
  var nodeD = TreeNode('d', List(nodeE))
  var nodeC = TreeNode('c', Nil)
  var nodeB = TreeNode('b', Nil)
  var nodeA = TreeNode('a', List(nodeD, nodeB, nodeC))
  println("Depth-First Search:")
  val found = dfsSearch(nodeA, 'f')
  println(s"found: $found")

  nodeF = TreeNode('f', Nil)
  nodeE = TreeNode('e', List(nodeF))
  nodeD = TreeNode('d', List(nodeE))
  nodeC = TreeNode('c', Nil)
  nodeB = TreeNode('b', Nil)
  nodeA = TreeNode('a', List(nodeD, nodeB, nodeC))
  println("Breath-First Search:")
  bfsSearch(nodeA, 'f')

}
