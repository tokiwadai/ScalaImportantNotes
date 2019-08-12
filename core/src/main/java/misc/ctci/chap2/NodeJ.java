package misc.ctci.chap2;

public class NodeJ {
    int data;
    NodeJ next;
    public NodeJ(int d) {
        data = d;
    }

    void append2Tail(int d) {
        NodeJ end = new NodeJ(d);
        NodeJ n = this;
        while (n.next != null) {
            n = n.next;
        }
        n.next = end;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        NodeJ curr = this;
        while (curr != null) {
            sb.append(curr.data);
            curr = curr.next;
        }
        return sb.toString();
    }
}
