package e3;


import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.EdgeWeightedGraph;
import edu.princeton.cs.algs4.IndexMinPQ;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;
import util.QGraph;
import util.QUtils;


public class Q2 {
    private Edge[] edgeTo;
    private double[] distTo;
    private boolean[] marked;
    private IndexMinPQ<Double> pq;
    List<Integer> edgeOrder = new ArrayList<Integer>();
    
    public Q2(EdgeWeightedGraph G, int s) {
        edgeTo = new Edge[G.V()];
        distTo = new double[G.V()];
        marked = new boolean[G.V()];
        pq = new IndexMinPQ<Double>(G.V());
        for (int v = 0; v < G.V(); v++)
            distTo[v] = Double.POSITIVE_INFINITY;
        
        prim(G, s);
    }
    
    private void prim(EdgeWeightedGraph G, int s) {
        distTo[s] = 0.0;
        pq.insert(s, distTo[s]);
        while (!pq.isEmpty()) {
            int v = pq.delMin();
            scan(G, v);
        }
    }
    
    private void scan(EdgeWeightedGraph G, int v) {
        marked[v] = true;
        edgeOrder.add(v);
        for (Edge e : G.adj(v)) {
            int w = e.other(v);
            if (marked[w])
                continue; // v-w is obsolete edge
            if (e.weight() < distTo[w]) {
                distTo[w] = e.weight();
                edgeTo[w] = e;
                if (pq.contains(w))
                    pq.decreaseKey(w, distTo[w]);
                else
                    pq.insert(w, distTo[w]);
            }
        }
    }
    
    public Iterable<Edge> edges() {
        Queue<Edge> mst = new Queue<Edge>();
        for (int v = 0; v < edgeTo.length; v++) {
            Edge e = edgeTo[v];
            if (e != null) {
                mst.enqueue(e);
            }
        }
        return mst;
    }
    
    public Q2(QGraph G, int s) {
    }
    
    public Iterable<String> getResult() {
        Queue<String> result = new Queue<String>();
        edgeOrder.stream().distinct().forEach(v -> {
            Edge e = edgeTo[v];
            if (e != null) {
                result.enqueue(String.format("%d", (int) e.weight()));
            }
        });
        return result;
    }
    
    public static void main(String[] args) {
        EdgeWeightedGraph G = QUtils.readEdgeWeightedGraphFromFile("q2.txt");
        Q2 q2 = new Q2(G, (int) ('H' - 'A'));
        StdOut.println(q2.getResult());
    }
}
