package e2;

import edu.princeton.cs.algs4.StdOut;
import util.QGraph;
import util.QUtils;

public class E {
	public static void main(String[] args) {
		QGraph Q = QUtils.readQGraphFromFile("q1.txt");
		Q1 q1 = new Q1(Q, 0);
		StdOut.println("Q1: " + q1.getResult());
		StdOut.println("-----------------------------------");		
		Q = QUtils.readQGraphFromFile("q2.txt");
		Q2 q2 = new Q2(Q, 0);
		StdOut.println("Q2: " + q2.getResult());
		StdOut.println("-----------------------------------");		
		QGraph G = QUtils.readQGraphFromFile("q3.txt");
		Q3 q3 = new Q3(G, 0);
		StdOut.println("Q3: " + q3.getResult());
	}
}
