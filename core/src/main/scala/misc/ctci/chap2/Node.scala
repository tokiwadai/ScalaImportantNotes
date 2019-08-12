package misc.ctci.chap2

class Node(var data: Int, var next: Option[Node]) {
  def appendToTail(d: Int) = {
    def append(n: Node, dt: Int): Unit = n.next match {
      case None => n.next = Some(new Node(dt, None))
      case Some(node) => append(node, dt)
    }
    val n = this
    append(n, d)
  }

  override def toString: String = {
    def getString(nxt: Option[Node], acc: List[String]): List[String] = nxt match {
      case None => acc
      case Some(nd) =>
        getString(nd.next, acc :+ s"${nd.data}")
    }
    getString(Some(this), Nil).mkString
  }
}
