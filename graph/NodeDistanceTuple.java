package graph;

public class NodeDistanceTuple implements Comparable<NodeDistanceTuple> {
    private Node node;
    private Integer distance;

    public NodeDistanceTuple(Node n, Integer dst) {
        this.node = n;
        this.distance = dst;
    }

    public Node node() {
        return this.node;
    }

    public Integer distance() {
        return this.distance;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof NodeDistanceTuple) {
            NodeDistanceTuple to = (NodeDistanceTuple) o;
            return to.node() == this.node() && to.distance() == this.distance();
        }

        return false;
    }

    @Override
    public int compareTo(NodeDistanceTuple t) {
        return this.distance().compareTo(t.distance());
    }
}
