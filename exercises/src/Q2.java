
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;


public class Q2 {
	private static final int INFINITY = Integer.MAX_VALUE;
	private boolean[] marked; // marked[v] = is there an s-v path
	private int[] edgeTo; // edgeTo[v] = previous edge on shortest s-v path
	private int[] distTo; // distTo[v] = number of edges shortest s-v path
	
	private Queue<String> preorder = new Queue<String>();

	public Q2(QGraph G, int s) {
		marked = new boolean[G.V()];
		distTo = new int[G.V()];
		edgeTo = new int[G.V()];
		bfs(G, s);
	}
	
	private void bfs(QGraph G, int s) {
		Queue<Integer> q = new Queue<Integer>();
		for (int v = 0; v < G.V(); v++)
			distTo[v] = INFINITY;
		distTo[s] = 0;
		marked[s] = true;
		q.enqueue(s);
		
		while (!q.isEmpty()) {
			int v = q.dequeue();
			preorder.enqueue(G.name(v));
			for (int w : G.adj(v)) {
				if (!marked[w]) {
					edgeTo[w] = v;
					distTo[w] = distTo[v] + 1;
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
		QGraph G = QUtils.readQGraphFromFile("q2.txt");
		Q2 q2 = new Q2(G, 0);
		StdOut.println(q2.getResult());
	}
}
