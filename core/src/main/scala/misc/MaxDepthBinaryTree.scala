package misc

/*
Time complexity: O(n)
https://www.geeksforgeeks.org/write-a-c-program-to-find-the-maximum-depth-or-height-of-a-tree/
 */
object MaxDepthBinaryTree extends App {
  case class Node(data: Int, var left: Node = null, var right: Node = null)

  def maxDepth(node: Node, id: String = "", counter: Int = 0): Int =
    if (node == null) {
      println(s"**counter [$counter] - ${id.toUpperCase} - depth = 0")
      0
    } else {
      println(s"B4 [$id] - counter [$counter] - node=${node.data}")
      val lDepth = maxDepth(node.left, "left", counter + 1)
      println(s"counter [$counter] - lDepth = $lDepth - node=${node.data}")
      //println(s"B4 right - $counter")
      val rDepth = maxDepth(node.right, "right", counter + 1)
      if (lDepth > rDepth) {
        println(s"counter [$counter] - lDepth = ${lDepth+1} - rDepth = $rDepth - node=${node.data}")
        lDepth + 1
      } else {
        println(s"counter [$counter] - lDepth = ${lDepth} - rDepth = ${rDepth+1} - node=${node.data}")
        rDepth + 1
      }
    }

  val root = Node(1)
  root.left = Node(2)
  root.right = Node(3)
  root.left.left = Node(4)
  root.left.right = Node(5)
  root.left.right.left = Node(6)

  println(s"maxDepth = [${maxDepth(root)}]")
}
