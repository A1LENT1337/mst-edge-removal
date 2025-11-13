package mst;

import java.util.*;
import java.io.File;

public class Main {

    public static void main(String[] args) {
        System.out.println("=== MST Edge Removal - Bonus Task Implementation ===");
        System.out.println("Demonstrating efficient edge replacement in Minimum Spanning Tree\n");

        createResultsDirectory();

        Graph graph = createDemonstrationGraph();
        System.out.println("=== Original Graph ===");
        graph.printGraph();

        MSTSolver solver = new MSTSolver(graph);

        System.out.println("\n=== Building Minimum Spanning Tree ===");
        List<Edge> originalMST = solver.buildMST();

        System.out.println("\n=== MST Before EDGE Removal ===");
        solver.printMST(originalMST);
        solver.saveMSTToCSV(originalMST, "results/original_mst.csv");
        solver.createVisualization(originalMST, "results/visualization_original.txt"); // ДОБАВЛЕНО

        demonstrateCompleteWorkflow(solver, 1, 2);

        System.out.println("\n PROGRAM COMPLETED SUCCESSFULLY! ===");
        System.out.println("All bonus task requirements have been implemented:");
        System.out.println("✓ MST construction from given graph");
        System.out.println("✓ MST display before edge removal");
        System.out.println("✓ Edge removal from MST");
        System.out.println("✓ Component display after removal");
        System.out.println("✓ Replacement edge finding");
        System.out.println("✓ New MST display");
    }

    private static void demonstrateCompleteWorkflow(MSTSolver solver, int src, int dest) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("DEMONSTRATING EDGE REMOVAL AND REPLACEMENT");
        System.out.println("=".repeat(60));

        // Find the edge to remove
        Edge edgeToRemove = solver.findEdgeInMST(src, dest);
        if (edgeToRemove == null) {
            System.out.println("ERROR: Edge " + src + "-" + dest + " not found in MST!");
            return;
        }

        // Remove one edge from MST
        List<Edge> mstAfterRemoval = solver.removeEdgeAndFindReplacement(edgeToRemove);

        // Show new MST after reconnection
        System.out.println("\n=== NEW MST AFTER RECONNECTION ===");
        solver.printMST(mstAfterRemoval);
        solver.saveMSTToCSV(mstAfterRemoval, "results/updated_mst.csv");
        solver.createVisualization(mstAfterRemoval, "results/visualization_updated.txt"); // ДОБАВЛЕНО

        // Clearly show which edge was added to reconnect the tree
        identifyNewlyAddedEdge(solver, mstAfterRemoval);
    }

    private static void identifyNewlyAddedEdge(MSTSolver solver, List<Edge> newMST) {
        System.out.println("\n--- RECONNECTION ANALYSIS ---");
        List<Edge> originalMST = new ArrayList<>(solver.buildMST());

        Edge addedEdge = null;
        Edge removedEdge = null;

        // Find added edge
        for (Edge edge : newMST) {
            if (!originalMST.contains(edge)) {
                addedEdge = edge;
                break;
            }
        }

        // Find removed edge
        for (Edge edge : originalMST) {
            if (!newMST.contains(edge)) {
                removedEdge = edge;
                break;
            }
        }

        if (addedEdge != null) {
            System.out.println("★ EDGE ADDED TO RECONNECT TREE: " + addedEdge + " ★");
            System.out.println("This edge successfully reconnected the components");
        }

        int originalWeight = solver.calculateTotalWeight(originalMST);
        int newWeight = solver.calculateTotalWeight(newMST);

        System.out.println("MST weight: " + originalWeight + " → " + newWeight);

        if (originalWeight == newWeight) {
            System.out.println("Weight unchanged - optimal replacement found!");
        } else if (newWeight > originalWeight) {
            System.out.println("Weight increased - this is expected when removing critical edges");
        } else {
            System.out.println("Weight decreased - this shouldn't happen with MST!");
        }

        // Show the swap
        if (removedEdge != null && addedEdge != null) {
            System.out.println("Edge swap: " + removedEdge + " → " + addedEdge);
        }
    }

    private static Graph createDemonstrationGraph() {
        Graph graph = new Graph(6);

        // Build a graph that will show clear components when edge is removed
        graph.addEdge(0, 1, 4);
        graph.addEdge(0, 2, 3);
        graph.addEdge(1, 2, 1);
        graph.addEdge(1, 3, 2);
        graph.addEdge(2, 3, 4);
        graph.addEdge(3, 4, 2);
        graph.addEdge(4, 5, 6);
        graph.addEdge(3, 5, 3);

        return graph;
    }

    private static void createResultsDirectory() {
        File resultsDir = new File("results");
        if (!resultsDir.exists()) {
            resultsDir.mkdir();
        }
    }
}