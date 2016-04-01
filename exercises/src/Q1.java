import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

public class Q1 {
	private boolean[] marked;
	private Queue<String> preorder = new Queue<String>();
	
	public Q1(QGraph G, int s) {
        marked = new boolean[G.V()];
        dfs(G, s);
    }

    // depth first search from v
    private void dfs(QGraph G, int v) {
        mark(G, v);
        for (int w : G.adj(v)) {
            if (!marked[w]) {
                dfs(G, w);
            }
        }
    }
	
	public void mark(QGraph G, int v) {
		marked[v] = true;
		preorder.enqueue(G.name(v));
	}
	
	public Iterable<String> getOrder() {
		return preorder;
	}
	
	public static void main(String[] args) {
		QGraph G = QUtils.readQGraphFromFile("q1.txt");
		Q1 q1 = new Q1(G, 0);
		StdOut.println(q1.getOrder());
		StdOut.println(G);
	}
}
