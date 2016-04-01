package e2;


import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;
import util.QDepthFirstOrder;
import util.QGraph;
import util.QUtils;


public class Q2 {
	private Iterable<Integer> order;
	Queue<String> result = new Queue<String>();
	
	public Q2(QGraph G, int s) {
		QDepthFirstOrder dfs = new QDepthFirstOrder(G);
		order = dfs.reversePost();
		for (int o : order) {
			result.enqueue(G.name(o));
		}
	}
	
	public Iterable<String> getResult() {
		return result;
	}
	
	public static void main(String[] args) {
		QGraph G = QUtils.readQGraphFromFile("q2.txt");
		Q2 q2 = new Q2(G, 0);
		StdOut.println(q2.getResult());
		StdOut.println(G);
	}
}
