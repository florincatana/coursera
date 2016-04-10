package e4;


import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.IndexMinPQ;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;
import util.QUtils;


public class Q1 {
    private double[] distTo;
    private DirectedEdge[] edgeTo;
    private IndexMinPQ<Double> pq;
    
    
    public Q1(EdgeWeightedDigraph G, int s, int b) {
        for (DirectedEdge e : G.edges()) {
            if (e.weight() < 0)
                throw new IllegalArgumentException("edge " + e + " has negative weight");
        }
        
        distTo = new double[G.V()];
        edgeTo = new DirectedEdge[G.V()];
        for (int v = 0; v < G.V(); v++)
            distTo[v] = Double.POSITIVE_INFINITY;
        distTo[s] = 0.0;
        
        pq = new IndexMinPQ<Double>(G.V());
        pq.insert(s, distTo[s]);
        while (!pq.isEmpty()) {
            int v = pq.delMin();
            for (DirectedEdge e : G.adj(v))
                relax(e);
            if (v == b) {
                break;
            }
        }
    }
    
    private void relax(DirectedEdge e) {
        int v = e.from(), w = e.to();
        if (distTo[w] > distTo[v] + e.weight()) {
            distTo[w] = distTo[v] + e.weight();
            edgeTo[w] = e;
            if (pq.contains(w))
                pq.decreaseKey(w, distTo[w]);
            else
                pq.insert(w, distTo[w]);
        }
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
        EdgeWeightedDigraph G1 = QUtils.readEdgeWeightedDigraphFromFile("q1.txt");
        Q1 q1 = new Q1(G1, (int) ('G' - 'A'), (int) ('B' - 'A'));
        StdOut.println(q1.getResult());
    }
}
