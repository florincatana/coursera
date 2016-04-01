import java.util.stream.IntStream;

import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.StdOut;

public class E {
	public static void main(String[] args) {
		QGraph Q = QUtils.readQGraphFromFile("q1.txt");
		Q1 q1 = new Q1(Q, 0);
		StdOut.println("Q1: " + q1.getOrder());
		Q = QUtils.readQGraphFromFile("q2.txt");
		Q2 q2 = new Q2(Q, 0);
		StdOut.println("Q2: " + q2.getOrder());
		Graph G = QUtils.readGraphFromFile("q3.txt");
		Q3 q3 = new Q3(G, 0);
		StdOut.print("Q3: ");
		IntStream.range(0, G.V()).forEach(s -> StdOut.print(q3.id(s) + " "));
	}
}
