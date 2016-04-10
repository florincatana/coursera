package e4;


import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.StdOut;
import util.QUtils;


public class E {
    public static void main(String[] args) {
        EdgeWeightedDigraph G1 = QUtils.readEdgeWeightedDigraphFromFile("q1.txt");
        Q1 q1 = new Q1(G1, (int)('G'-'A'), (int)('F'-'A'));
        StdOut.println(q1.getResult());
        StdOut.println("-----------------------------------");
        EdgeWeightedDigraph G2 = QUtils.readEdgeWeightedDigraphFromFile("q2.txt");
        Q2 q2 = new Q2(G2, (int) ('H' - 'A'), 'B', new String("H D G C F B E A").replaceAll(" ", ""));
        StdOut.println(q2.getResult());
        StdOut.println("-----------------------------------");
        EdgeWeightedDigraph G3 = QUtils.readEdgeWeightedDigraphFromFile("q3.txt");
        Q3 q3 = new Q3(G3, (int)('H'-'A'), 3);
        StdOut.println(q3.getResult());
        StdOut.println("-----------------------------------");
    }
}
