package graph;

import java.util.*;

class SortEdgeByDistance implements Comparator<Edge> {
    public int compare(Edge a, Edge b) {
        return a.distance().compareTo(b.distance());
    }
}

public class Graph {

    static final Integer MAX_DISTANCE = 9999999;
    static final Integer MIN_DISTANCE = 0;

    private LinkedHashMap<Node, List<Edge>> graph;

    public Graph() {
        this.graph = new LinkedHashMap<Node, List<Edge>>();
    }

    public Set<Node> nodes() {
        return this.graph.keySet();
    }

    public Node getNode(String id) {
        Node nodeToFind = null;
        for (Node node : nodes()) {
            if (node.id() == id) {
                nodeToFind = node;
            }
        }
        return nodeToFind;
    }

    public List<Edge> edgesForNode(Node node) {
        List<Edge> edges = this.graph.get(node);
        edges.sort(new SortEdgeByDistance());
        return edges;
    }

    public void addNode(String n) {
        Node newNode = new Node(n);
        for (Node node : nodes()) {
            if (node.id() == newNode.id()) {
                System.out.printf("Node %s already exists in the graph!\n", n);
                return;
            }
        }
        this.graph.put(newNode, new ArrayList<Edge>());
    }

    public boolean hasEdgeForNode(Node node, Edge newEdge) {
        List<Edge> edges = edgesForNode(node);
        for (Edge edge : edges) {
            if (newEdge.dstId() == edge.dstId()) {
                return true;
            }
        }
        return false;
    }

    public void addEdge(String srcId, String dstId, int distance) {
        Node node = getNode(srcId);
        if (node == null) {
            System.out.printf("Node with source %s doesn't exist! Please add it to the graph first.\n", srcId);
            return;
        }

        Edge newEdge = new Edge(dstId, distance);

        if (hasEdgeForNode(node, newEdge)) {
            System.out.printf("Node %s already contains edge to %s!\n", node.id(), newEdge.dstId());
            return;
        }

        List<Edge> edges = edgesForNode(node);
        edges.add(newEdge);

        this.graph.put(node, edges);
    }

    public List<Node> adjacentNodes(Node node) {
        List<Node> adjacentNodes = new ArrayList<Node>();
        for (Edge edge : edgesForNode(node)) {
            adjacentNodes.add(getNode(edge.dstId()));
        }
        return adjacentNodes;
    }

    public boolean hasAdjacentNodes(Node node) {
        return edgesForNode(node).size() > 0;
    }

    public void print() {
        System.out.println("GRAPH\n");
        for (Node node : nodes()) {
            System.out.printf("Node %s: ", node.id());
            if (edgesForNode(node).size() == 0) {
                System.out.printf("None");
            }
            for (Edge edge : edgesForNode(node)) {
                System.out.printf("-> %s - %d; ", edge.dstId(), edge.distance());
            }
            System.out.println();
        }
    }

    private LinkedHashMap<Node, Integer> distanceNodes(Node n) {
        LinkedHashMap<Node, Integer> distanceNodes = new LinkedHashMap<Node, Integer>();
        for (Node node : nodes()) {
            distanceNodes.put(node, MAX_DISTANCE);
            if (node.equals(n)) {
                distanceNodes.put(node, MIN_DISTANCE);
            }
        }
        return distanceNodes;
    }

    public Integer pathDistance(List<Node> path) {
        Integer distance = MIN_DISTANCE;
        for (int i = 0; i < path.size() - 1; i++) {
            Node n = path.get(i);
            Node next = path.get(i + 1);
            for (Edge edge : edgesForNode(n)) {
                if (edge.dstId() == next.id()) {
                    distance = distance + edge.distance();
                }
            }
        }
        return distance;
    }

    private List<Node> removeDuplicates(List<Node> nodes) {
        List<Node> newList = new ArrayList<Node>();

        for (Node n : nodes) {
            if (!newList.contains(n)) {
                newList.add(n);
            }
        }

        return newList;
    }

    public List<Node> longestPath(Node fromNode, Node toNode) {
        List<Node> visited = new ArrayList<Node>();
        Stack<Node> stack = new Stack<Node>();

        visited.add(fromNode);
        stack.push(fromNode);

        while (!stack.empty()) {
            Node u = stack.pop();

            if (u.equals(toNode)) {
                visited.add(u);
                break;
            }

            for (Edge edge : edgesForNode(u)) {
                Node v = getNode(edge.dstId());
                if (!visited.contains(v)) {
                    visited.add(u);
                    stack.push(v);
                }
            }
        }

        return removeDuplicates(visited);

    }

    public LinkedHashMap<Node, Integer> djikstra(Node fromNode) {
        LinkedHashMap<Node, Integer> distanceNodes = distanceNodes(fromNode);
        PriorityQueue<NodeDistanceTuple> queue = new PriorityQueue<NodeDistanceTuple>();

        queue.add(new NodeDistanceTuple(fromNode, MIN_DISTANCE));

        while (!queue.isEmpty()) {
            Node u = queue.poll().node();

            for (Edge edge : edgesForNode(u)) {
                Node v = getNode(edge.dstId());
                int w = edge.distance();
                if (distanceNodes.get(v) > distanceNodes.get(u) + w) {
                    distanceNodes.put(v, distanceNodes.get(u) + w);
                    queue.add(new NodeDistanceTuple(v, distanceNodes.get(v)));
                }
            }
        }

        return distanceNodes;
    }

    public boolean isAdjacent(Node fromNode, Node toNode) {
        for (Edge edge : edgesForNode(fromNode)) {
            if (edge.dstId() == toNode.id()) {
                return true;
            }
        }
        return false;
    }

    public boolean hasPath(Node fromNode, Node toNode) {
        if (isAdjacent(fromNode, toNode)) {
            return true;
        }

        HashMap<Node, Integer> shortestPath = djikstra(fromNode);

        return shortestPath.get(toNode) != MAX_DISTANCE;
    }
}
