package e4;


import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;
import util.QUtils;


public class Q3 {
    private double[] distTo;
    private DirectedEdge[] edgeTo;
    private Iterable<DirectedEdge> cycle;
    
    int stop = 0;
    
    public Q3(EdgeWeightedDigraph G, int s, int p) {
        distTo = new double[G.V()];
        edgeTo = new DirectedEdge[G.V()];
        for (int v = 0; v < G.V(); v++)
            distTo[v] = Double.POSITIVE_INFINITY;
        distTo[s] = 0.0;
        
        // Bellman-Ford algorithm
        for (int i = 0; i < p; i++) {
            for (int v = 0; v < G.V(); v++) {
                relax(G, v);
            }
        }
    }
    
    private void relax(EdgeWeightedDigraph G, int v) {
        for (DirectedEdge e : G.adj(v)) {
            int w = e.to();
            if (distTo[w] > distTo[v] + e.weight()) {
                distTo[w] = distTo[v] + e.weight();
                edgeTo[w] = e;
            }
        }
    }
    
    public boolean hasNegativeCycle() {
        return cycle != null;
    }
    
    public Iterable<String> getResult() {
        Queue<String> result = new Queue<String>();
        for (int v = 0; v < distTo.length; v++) {
            if (distTo[v] < Double.POSITIVE_INFINITY) {
                result.enqueue(String.format("%d", (int) distTo[v]));
            } else {
                result.enqueue("-");
            }
        }
        return result;
    }
    
    public static void main(String[] args) {
        EdgeWeightedDigraph G3 = QUtils.readEdgeWeightedDigraphFromFile("q3.txt");
        Q3 q3 = new Q3(G3, (int) ('H' - 'A'), 3);
        StdOut.println(q3.getResult());
        StdOut.println("127 96 28 32 42 22 22 0");
    }
}
