package mst;

import java.util.*;
import java.io.*;

public class Graph {
    private int vertices;
    private List<Edge> edges;

    public Graph(int vertices) {
        this.vertices = vertices;
        this.edges = new ArrayList<>();
    }

    public void addEdge(int src, int dest, int weight) {
        edges.add(new Edge(src, dest, weight));
    }

    public List<Edge> getEdges() {
        return new ArrayList<>(edges);
    }

    public int getVerticesCount() {
        return vertices;
    }

    public void printGraph() {
        System.out.println("Graph with " + vertices + " vertices and " + edges.size() + " edges:");
        for (Edge edge : edges) {
            System.out.println("  " + edge.src + " - " + edge.dest + " (weight: " + edge.weight + ")");
        }
    }

    public void saveToCSV(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("source,destination,weight");
            for (Edge edge : edges) {
                writer.println(edge.toCSV());
            }
            System.out.println("Graph saved to: " + filename);
        } catch (IOException e) {
            System.err.println("Error saving graph to CSV: " + e.getMessage());
        }
    }

    public static Graph createTestGraph() {
        Graph graph = new Graph(5);

        // Adding edges as per the plan
        graph.addEdge(0, 1, 2);
        graph.addEdge(1, 2, 3);
        graph.addEdge(0, 3, 6);
        graph.addEdge(1, 4, 5);
        graph.addEdge(2, 4, 4);
        graph.addEdge(3, 4, 7);
        graph.addEdge(1, 3, 5); // Additional edge for replacement demonstration

        return graph;
    }
}