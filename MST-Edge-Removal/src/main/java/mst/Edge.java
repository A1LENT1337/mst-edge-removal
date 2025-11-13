package mst;

public class Edge implements Comparable<Edge> {
    public int src;
    public int dest;
    public int weight;

    public Edge(int src, int dest, int weight) {
        this.src = src;
        this.dest = dest;
        this.weight = weight;
    }

    @Override
    public int compareTo(Edge other) {
        return Integer.compare(this.weight, other.weight);
    }

    @Override
    public String toString() {
        return src + "-" + dest + "(" + weight + ")";
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Edge edge = (Edge) obj;
        return (src == edge.src && dest == edge.dest) ||
                (src == edge.dest && dest == edge.src);
    }

    @Override
    public int hashCode() {
        return Math.min(src, dest) * 31 + Math.max(src, dest);
    }

    public String toCSV() {
        return src + "," + dest + "," + weight;
    }
}