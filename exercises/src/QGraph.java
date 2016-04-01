import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.TreeMap;


public class QGraph {
	private TreeMap<String, Integer> st = new TreeMap<String, Integer>();;
	private String[] keys;
	private static final String NEWLINE = System.getProperty("line.separator");
	
	private final int V;
	private int E;
	private ArrayList<Collection<Integer>> adj;
	private int[] indegree;
	
	public QGraph(Iterable<String> symbols) {
		if (symbols == null)
			throw new NullPointerException("Key set is null");
		for (String symbol : symbols) {
			if (!st.containsKey(symbol))
				st.put(symbol, st.size());
		}
		
		V = st.size();
		this.E = 0;
		if (V == 0)
			throw new IllegalArgumentException("Number of vertices in a QGraph must be greater than 0");
		
		keys = new String[st.size()];
		for (String name : st.keySet()) {
			keys[st.get(name)] = name;
		}
		
		indegree = new int[V];
		adj = new ArrayList<Collection<Integer>>(V);
		for (int v = 0; v < V; v++) {
			adj.add(new LinkedHashSet<Integer>());
		}
	}
	
	public void addEdge(String v, String w) {
		addEdge(st.get(v), st.get(w));
	}
	
	public void addEdge(int v, int w) {
		validateVertex(v);
		validateVertex(w);
		adj.get(v).add(w);
	//	adj.get(w).add(v);
		indegree[w]++;
		E++;
	}
	
	public int V() {
		return V;
	}
	
	public int E() {
		return E;
	}
	
	public Iterable<Integer> adj(int v) {
		return adj.get(v);
	}
	
	public int outdegree(int v) {
		validateVertex(v);
		return adj.get(v).size();
	}
	
	public int indegree(int v) {
		validateVertex(v);
		return indegree[v];
	}
	
	public String name(int v) {
		return keys[v];
	}
	
	public int value(String name) {
		return st.get(name);
	}
	
	private void validateVertex(int v) {
		if (v < 0 || v >= V)
			throw new IndexOutOfBoundsException("vertex " + v + " is not between 0 and " + (V - 1));
	}
	
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(V + " vertices, " + E + " edges " + NEWLINE);
		for (int v = 0; v < V; v++) {
			s.append(String.format("%s: ", name(v)));
			for (int w : adj.get(v)) {
				s.append(String.format("%s ", name(w)));
			}
			s.append(NEWLINE);
		}
		return s.toString();
	}
}
