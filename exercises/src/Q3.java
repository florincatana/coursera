
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.IntStream;

import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;


public class Q3 {
	private boolean[] marked; // marked[v] = has vertex v been marked?
	private int[] id; // id[v] = id of connected component containing v
	private int[] size; // size[id] = number of vertices in given component
	private int count = 0; // number of connected components
	private Map<String, Integer> result = new TreeMap<String, Integer>();
	
	public Q3(QGraph G, int s) {
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
	
	private void dfs(QGraph G, int v) {
		marked[v] = true;
		id[v] = count;
		result.put(G.name(v), count);
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
	
	public Iterable<Integer> getResult() {
		Queue<Integer> ret = new Queue<Integer>();
		result.values().stream().forEach(v -> ret.enqueue(v));
		return ret;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		QGraph G = QUtils.readQGraphFromFile("q3.txt");
		Q3 q3 = new Q3(G, 0);
		StdOut.println(G);
		StdOut.println(q3.getResult());
	}
}
