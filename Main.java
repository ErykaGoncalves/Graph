
import graph.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        Graph g = new Graph();

        g.addNode("A");
        g.addNode("B");
        g.addNode("C");
        g.addNode("D");
        g.addNode("E");
        g.addNode("F");
        g.addNode("G");
        g.addNode("I");

        g.addEdge("A", "I", 5);
        g.addEdge("A", "D", 50);
        g.addEdge("B", "A", 10);
        g.addEdge("C", "E", 10);
        g.addEdge("C", "B", 15);
        g.addEdge("C", "G", 25);
        g.addEdge("C", "D", 70);
        g.addEdge("D", "C", 70);
        g.addEdge("D", "G", 100);
        g.addEdge("E", "B", 10);
        g.addEdge("E", "I", 5);
        g.addEdge("F", "C", 5);
        g.addEdge("F", "E", 10);
        g.addEdge("F", "G", 25);
        g.addEdge("G", "F", 20);
        g.addEdge("G", "D", 100);

        System.out.println("\n-------------------------\n");
        g.print();

        Node origin = g.getNode("C");
        Node target = g.getNode("I");

        System.out.println("\n-------------------------\n");
        System.out.println("Djisktra\n");
        HashMap<Node, Integer> djikstra = g.djikstra(origin);

        for (Node node : djikstra.keySet()) {
            System.out.printf("%s -> %s is %d\n",
                    origin.id(), node.id(), djikstra.get(node));
        }

        System.out.println("\n-------------------------\n");
        System.out.printf("Longest path from Node %s to Node %s\n\n", origin.id(), target.id());

        List<Node> longestPath = g.longestPath(origin, target);

        for (Node node : longestPath) {
            if (!node.equals(target)) {
                System.out.printf("%s -> ", node.id());
            } else {
                System.out.printf("%s\n", node.id());
            }
        }

        System.out.printf("\nDistance is %d\n\n", g.pathDistance(longestPath));
    }
}
