package mst;

import java.util.*;
import java.io.*;

public class MSTSolver {
    private Graph graph;
    private List<Edge> mst;

    public MSTSolver(Graph graph) {
        this.graph = graph;
        this.mst = new ArrayList<>();
    }

    public List<Edge> buildMST() {
        List<Edge> allEdges = new ArrayList<>(graph.getEdges());
        Collections.sort(allEdges); // O(E log E)

        UnionFind uf = new UnionFind(graph.getVerticesCount());
        mst.clear();

        // Kruskal's algorithm
        for (Edge edge : allEdges) {
            if (!uf.connected(edge.src, edge.dest)) {
                uf.union(edge.src, edge.dest);
                mst.add(edge);

                // Stop when we have V-1 edges
                if (mst.size() == graph.getVerticesCount() - 1) {
                    break;
                }
            }
        }

        return new ArrayList<>(mst);
    }

    public List<Edge> removeEdgeAndFindReplacement(Edge edgeToRemove) {
        System.out.println("\n=== Removing edge " + edgeToRemove + " ===");

        if (!mst.remove(edgeToRemove)) {
            System.out.println("Edge " + edgeToRemove + " not found in MST!");
            return mst;
        }

        // Find connected components after removal
        List<Set<Integer>> components = findComponents();
        System.out.println("Components after removal:");
        for (int i = 0; i < components.size(); i++) {
            System.out.println("  Component " + (i + 1) + ": " + components.get(i));
        }

        // Find optimal replacement edge - ПЕРЕДАЕМ УДАЛЕННОЕ РЕБРО
        System.out.println("\n=== Finding replacement edge ===");
        Edge replacement = findReplacementEdge(components, edgeToRemove);

        if (replacement != null) {
            mst.add(replacement);
            System.out.println("Found replacement edge: " + replacement);
        } else {
            System.out.println("No replacement edge found! Graph remains disconnected.");
        }

        return new ArrayList<>(mst);
    }

    private List<Set<Integer>> findComponents() {

        Map<Integer, List<Integer>> adjList = new HashMap<>();
        for (int i = 0; i < graph.getVerticesCount(); i++) {
            adjList.put(i, new ArrayList<>());
        }

        for (Edge edge : mst) {
            adjList.get(edge.src).add(edge.dest);
            adjList.get(edge.dest).add(edge.src);
        }

        // BFS to find connected components
        List<Set<Integer>> components = new ArrayList<>();
        boolean[] visited = new boolean[graph.getVerticesCount()];

        for (int i = 0; i < graph.getVerticesCount(); i++) {
            if (!visited[i]) {
                Set<Integer> component = new HashSet<>();
                Queue<Integer> queue = new LinkedList<>();

                queue.add(i);
                visited[i] = true;
                component.add(i);

                while (!queue.isEmpty()) {
                    int current = queue.poll();
                    for (int neighbor : adjList.get(current)) {
                        if (!visited[neighbor]) {
                            visited[neighbor] = true;
                            component.add(neighbor);
                            queue.add(neighbor);
                        }
                    }
                }

                components.add(component);
            }
        }

        return components;
    }

    private Edge findReplacementEdge(List<Set<Integer>> components, Edge removedEdge) {
        if (components.size() < 2) {
            return null; // Graph remains connected
        }

        Set<Integer> component1 = components.get(0);
        Set<Integer> component2 = components.get(1);

        Edge bestReplacement = null;

        // Check all edges in original graph
        for (Edge edge : graph.getEdges()) {
            // Check if edge connects two different components
            boolean connectsComponents =
                    (component1.contains(edge.src) && component2.contains(edge.dest)) ||
                            (component1.contains(edge.dest) && component2.contains(edge.src));

            // Edge should not be in current MST AND should not be the removed edge
            if (connectsComponents && !mst.contains(edge) && !edge.equals(removedEdge)) {
                if (bestReplacement == null || edge.weight < bestReplacement.weight) {
                    bestReplacement = edge;
                }
            }
        }

        return bestReplacement;
    }


    public int calculateTotalWeight(List<Edge> edges) {
        return edges.stream().mapToInt(edge -> edge.weight).sum();
    }

    public void printMST(List<Edge> mstEdges) {
        System.out.println("MST Edges (" + mstEdges.size() + " edges):");
        for (Edge edge : mstEdges) {
            System.out.println("  " + edge);
        }
        System.out.println("Total weight: " + calculateTotalWeight(mstEdges));
    }

    public void saveMSTToCSV(List<Edge> mstEdges, String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("source,destination,weight");
            for (Edge edge : mstEdges) {
                writer.println(edge.toCSV());
            }
            System.out.println("MST saved to: " + filename);
        } catch (IOException e) {
            System.err.println("Error saving MST to CSV: " + e.getMessage());
        }
    }

    public void createVisualization(List<Edge> edges, String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("Graph Visualization:");
            writer.println("Vertices: 0 to " + (graph.getVerticesCount() - 1));
            writer.println("\nEdges:");
            for (Edge edge : edges) {
                writer.println(edge.src + " --- " + edge.weight + " --- " + edge.dest);
            }
            writer.println("\nTotal weight: " + calculateTotalWeight(edges));
            System.out.println("Visualization saved to: " + filename);
        } catch (IOException e) {
            System.err.println("Error creating visualization: " + e.getMessage());
        }
    }

    public Edge findEdgeInMST(int src, int dest) {
        Edge target = new Edge(src, dest, 0);
        for (Edge edge : mst) {
            if (edge.equals(target)) {
                return edge;
            }
        }
        return null;
    }
}