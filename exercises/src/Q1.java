
import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

public class Q1 {
	private boolean[] marked; // marked[v] = is there an s-v path?
	private Queue<String> preorder = new Queue<String>();
	
	private Q1(Graph G, int s) {
        marked = new boolean[G.V()];
        dfs(G, s);
	}
	// depth first search from v
	private void dfs(Graph G, int v) {
		marked[v] = true;
		preorder.enqueue(Character.toString((char) (v + 65)));
		for (int w : G.adj(v)) {
			if (!marked[w]) {
				dfs(G, w);
			}
		}
	}
	
    public Iterable<String> getOrder() {
    	return preorder;
    }
    
    public String toStringAdj(Graph G, int v) {
        StringBuilder s = new StringBuilder();
        for (int w : G.adj(v)) {
            s.append(w + " ");
        }
        return s.toString();
    }
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Graph G = QUtils.readGraphFromFile("q1.txt");
		Q1 q1 = new Q1(G, 0);
		StdOut.println(G);
		StdOut.println(q1.getOrder());
	}
}
