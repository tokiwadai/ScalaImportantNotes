package misc.ctci.chap2;

public class DeleteDupsNoBuffJ {
    static void deleteDups(NodeJ node) {
        NodeJ curr = node;
        while (curr != null) {
            NodeJ runner = curr;
            while(runner.next != null) {
                if (runner.next.data == curr.data) {
                    runner.next = runner.next.next;
                } else {
                    runner = runner.next;
                }
            }
            curr = curr.next;
        }
    }

    public static void main(String... args) {
        NodeJ node1 = new NodeJ(5);
        NodeJ node2 = new NodeJ(1);
        node2.next = node1;
        NodeJ node3 = new NodeJ(1);
        node3.next = node2;
        NodeJ node4 = new NodeJ(4);
        node4.next = node3;
        deleteDups(node4);
        System.out.println(node4.toString());
        return;
    }
}
