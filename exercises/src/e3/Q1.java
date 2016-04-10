package e3;


import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.EdgeWeightedGraph;
import edu.princeton.cs.algs4.KruskalMST;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;
import util.QUtils;


public class Q1 {
    KruskalMST krustalMST = null;
    
    public Q1(EdgeWeightedGraph G) {
        krustalMST = new KruskalMST(G);
    }
    
    public Iterable<String> getResult() {
        Queue<String> result = new Queue<String>();
        for (Edge e : krustalMST.edges()) {
            result.enqueue(String.format("%d", (int) e.weight()));
        }
        return result;
    }
    
    public static void main(String[] args) {
        EdgeWeightedGraph G = QUtils.readEdgeWeightedGraphFromFile("q1.txt");
        Q1 q1 = new Q1(G);
        StdOut.println(q1.getResult());
    }
}
