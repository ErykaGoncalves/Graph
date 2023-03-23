package graph;

public class Node {

    private String id;

    public Node(String id) {
        this.id = id;
    }

    public String id() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Node) {
            Node node = (Node) o;
            return node.id() == this.id;
        }

        return false;
    }
}
