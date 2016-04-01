package e2;


import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;
import util.QGraph;
import util.QUtils;


public class Q1 {
	private boolean[] marked;
	
	private Queue<String> preorder = new Queue<String>();
	
	public Q1(QGraph G, int s) {
		marked = new boolean[G.V()];
		bfs(G, s);
	}
	
	private void bfs(QGraph G, int s) {
		Queue<Integer> q = new Queue<Integer>();
		marked[s] = true;
		q.enqueue(s);
		
		while (!q.isEmpty()) {
			int v = q.dequeue();
			preorder.enqueue(G.name(v));
			for (int w : G.adj(v)) {
				if (!marked[w]) {
					marked[w] = true;
					q.enqueue(w);
				}
			}
		}
	}
	
	public Iterable<String> getResult() {
		return preorder;
	}
	
	public static void main(String[] args) {
		QGraph G = QUtils.readQGraphFromFile("q1.txt");
		Q1 q1 = new Q1(G, 0);
		StdOut.println(q1.getResult());
		StdOut.println(G);
	}
}
