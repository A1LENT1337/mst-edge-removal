# MST Edge Removal - Bonus Task Implementation

##  Project Overview
This Java program implements the **Bonus Task: Edge Removal from an MST** as specified in the requirements. The solution efficiently handles edge removal from a Minimum Spanning Tree and finds optimal replacement edges while maintaining MST properties.

##  Requirements Met
- ✅ Build MST from given graph (Kruskal's algorithm)
- ✅ Display MST edges before removal
- ✅ Remove one edge from MST
- ✅ Show resulting components after removal
- ✅ Find replacement edge to reconnect components
- ✅ Display new MST with clear reconnection indication
- ✅ Efficient algorithm implementation
- ✅ Ready-to-run after cloning

## Quick Start

### Prerequisites
- Java 23
- Git

### Installation & Execution
```bash
# 1. Clone the repository
git clone https://github.com/A1LENT1337/mst-edge-removal.git
cd mst-edge-removal

# 2. Compile the program
javac -d bin src/main/java/mst/*.java

# 3. Run the demonstration
java -cp bin mst.Main
```
## Output Example
```bash
=== MST BEFORE EDGE REMOVAL ===
MST Edges (5 edges):
  0-2(3)
  1-2(1)
  1-3(2)
  3-4(2)
  3-5(3)
Total weight: 11

--- REMOVING EDGE: 1-2(1) ---
Components after removal:
  Component 1: [0, 2]
  Component 2: [1, 3, 4, 5]

=== NEW MST AFTER RECONNECTION ===
MST Edges (5 edges):
  0-2(3)
  1-3(2)
  3-4(2)
  3-5(3)
  0-1(4)

★ EDGE ADDED TO RECONNECT TREE: 0-1(4) ★
MST weight: 11 → 11
Weight unchanged - optimal replacement found!
Edge swap: 1-2(1) → 0-1(4)
```

## Project Structure
````
mst-edge-removal/
├── src/main/java/mst/
│   ├── Main.java          # Demonstration with all requirements
│   ├── Graph.java         # Graph representation and I/O operations
│   ├── Edge.java          # Edge class with comparison logic
│   ├── UnionFind.java     # Union-Find data structure for Kruskal's
│   └── MSTSolver.java     # Core MST algorithms and operations
├── results/               # Generated output files (CSV, visualizations)
├── README.md             # This file
└── .gitignore           # Git exclusion rules
````
## Algorithm Efficiency
| Operation            | Time Complexity | Description                     |
|----------------------|----------------|---------------------------------|
| MST Construction     | O(E log E)     | Kruskal's with edge sorting     |
| Component Detection  | O(V + E)       | BFS graph traversal             |
| Replacement Search   | O(E)           | Linear scan of all edges       |
| Overall              | O(E log E)     | Efficient and optimal          |

# Core Components
### Graph Representation
- Undirected weighted graph
- Vertex and edge management
- CSV export capabilities

### MST Construction
- Kruskal's algorithm implementation
- Union-Find with path compression
- Union by rank for optimal performance

### Edge Replacement
- BFS component detection after edge removal
- Minimal weight edge selection
- Clear reconnection visualization

## Output Files
The program generates the following files in the results/ directory:
- original_graph.csv - Initial graph structure
- original_mst.csv - MST before edge removal
- updated_mst.csv - MST after edge replacement
- visualization_*.txt - Text-based graph visualizations

### CSV Format
```bash
source,destination,weight
0,1,2
1,2,3
2,3,4
```
# Testing
The implementation has been tested with:
- Various graph configurations
- Multiple edge removal scenarios
- Both weight-changing and weight-preserving replacements
- Edge cases (disconnected components, etc.)

# Key Features
- Clear Visualization: Each step is clearly displayed in console
- Efficient Algorithms: Optimal time complexity for all operations
- Data Export: CSV files for further analysis
- Well-Documented: Comprehensive code comments in English
- Zero Dependencies: Pure Java - runs anywhere
- Easy Compilation: Simple javac commands only

# Author
Nurdaulet Aitynbek  
Bonus Task Implementation for Endterm Registration (5%)



