package e4;


import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;
import util.QUtils;


public class Q2 {
    private double[] distTo;
    private DirectedEdge[] edgeTo;
    
    public Q2(EdgeWeightedDigraph G, int s, char b, String topologicalOrder) {
        distTo = new double[G.V()];
        edgeTo = new DirectedEdge[G.V()];
        for (int v = 0; v < G.V(); v++)
            distTo[v] = Double.POSITIVE_INFINITY;
        distTo[s] = 0.0;
        
        for (byte c : topologicalOrder.getBytes()) {
            for (DirectedEdge e : G.adj((int) c - 'A'))
                relax(e);
            if (c == b)
                break;
        }
    }
    
    // relax edge e
    private void relax(DirectedEdge e) {
        int v = e.from(), w = e.to();
        if (distTo[w] > distTo[v] + e.weight()) {
            distTo[w] = distTo[v] + e.weight();
            edgeTo[w] = e;
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
        EdgeWeightedDigraph G2 = QUtils.readEdgeWeightedDigraphFromFile("q2.txt");
        Q2 q2 = new Q2(G2, (int) ('G' - 'A'), 'A', new String("G C H D B A F E ").replaceAll(" ", ""));
        StdOut.println(q2.getResult());
    }
}
