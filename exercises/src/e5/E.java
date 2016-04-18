package e5;


import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.StdOut;
import util.QUtils;


public class E {
    public static void main(String[] args) {
        FlowNetwork G1 = QUtils.readFlowNetworkFromFile("q1.txt");
        Q1 q1 = new Q1(G1, (int)('A'-'A'), (int)('J'-'A'));
        StdOut.println(q1.getResult());
        StdOut.println("-----------------------------------");
        FlowNetwork G2 = QUtils.readFlowNetworkFromFile("q2.txt");
        Q2 q2 = new Q2(G2, (int)('A'-'A'), (int)('J'-'A'));
        StdOut.println(q2.getResult());
        StdOut.println("-----------------------------------");
    }
}
