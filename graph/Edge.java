package graph;

public class Edge {

    private String dstId;
    private int distance;

    public Edge(String dst, int distance) {
        this.dstId = dst;
        this.distance = distance;
    }

    public String dstId() {
        return dstId;
    }

    public Integer distance() {
        return distance;
    }
}
