package e2;


import java.util.Map;
import java.util.TreeMap;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;
import util.QDepthFirstOrder;
import util.QGraph;
import util.QUtils;


public class Q3 {
	private boolean[] marked;
	private int[] id;
	private int count;
	
	private Map<String, Integer> result = new TreeMap<String, Integer>();
	private Queue<String> postorder = new Queue<String>();
	
	public Q3(QGraph G, int s) {
		QDepthFirstOrder dfs = new QDepthFirstOrder(G.reverse());
		
		marked = new boolean[G.V()];
		id = new int[G.V()];
		for (int v : dfs.reversePost()) {
			postorder.enqueue(G.name(v));
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
		for (int w : G.adj(v)) {
			if (!marked[w])
				dfs(G, w);
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
	
	public Iterable<String> getPostOrder() {
		return postorder;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		QGraph G = QUtils.readQGraphFromFile("q3.txt");
		Q3 q3 = new Q3(G, 0);
		StdOut.println(G);
		StdOut.println(q3.getResult());
		StdOut.println(q3.getPostOrder());
		StdOut.println("C H G I E D J A B F");
	}
}
