package e3;


import edu.princeton.cs.algs4.EdgeWeightedGraph;
import edu.princeton.cs.algs4.StdOut;
import util.QUtils;


public class E {
    public static void main(String[] args) {
        EdgeWeightedGraph G1 = QUtils.readEdgeWeightedGraphFromFile("q1.txt");
        Q1 q1 = new Q1(G1);
        StdOut.println(q1.getResult());
        StdOut.println("-----------------------------------");
        EdgeWeightedGraph G2 = QUtils.readEdgeWeightedGraphFromFile("q2.txt");
        //StdOut.println((int)('H'-'A'));
        Q2 q2 = new Q2(G2, (int)('H'-'A'));
        StdOut.println(q2.getResult());
        StdOut.println("-----------------------------------");
    }
}
