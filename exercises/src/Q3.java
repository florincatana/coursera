
import java.util.stream.IntStream;

import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.StdOut;


public class Q3 {
	private boolean[] marked; // marked[v] = has vertex v been marked?
	private int[] id; // id[v] = id of connected component containing v
	private int[] size; // size[id] = number of vertices in given component
	private int count; // number of connected components
	
	public Q3(Graph G, int s) {
		marked = new boolean[G.V()];
		id = new int[G.V()];
		size = new int[G.V()];
		for (int v = 0; v < G.V(); v++) {
			if (!marked[v]) {
				dfs(G, v);
				count++;
			}
		}
	}
	
	private void dfs(Graph G, int v) {
		marked[v] = true;
		id[v] = count;
		size[count]++;
		for (int w : G.adj(v)) {
			if (!marked[w]) {
				dfs(G, w);
			}
		}
	}
	
	public int id(int v) {
		return id[v];
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Graph G = QUtils.readGraphFromFile("q3.txt");
		Q3 q3 = new Q3(G, 0);
		StdOut.println(G);
		IntStream.range(0, G.V()).forEach(s -> StdOut.print(q3.id(s) + " "));
	}
}
