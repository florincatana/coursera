import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeMap;

import edu.princeton.cs.algs4.In;

public class QGraph {
    private TreeMap<String, Integer> st = new TreeMap<String, Integer>();;
    private String[] keys;
    private static final String NEWLINE = System.getProperty("line.separator");

    private final int V;
    private int E;
    private Set<Integer>[] adj;
    private int[] indegree;

    public QGraph(String filename, String delimiter) {
        In in = new In(filename);
        while (in.hasNextLine()) {
            String[] a = in.readLine().trim().split(delimiter);
            for (int i = 0; i < a.length; i++) {
                if (!st.containsKey(a[i]))
                    st.put(a[i], st.size());
            }
        }

        keys = new String[st.size()];
        for (String name : st.keySet()) {
            keys[st.get(name)] = name;
        }

        this.V = st.size();
        this.E = 0;
        indegree = new int[V];
        adj = new LinkedHashSet[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new LinkedHashSet<Integer>();
        }
        
        in = new In(filename);
        while (in.hasNextLine()) {
            String[] a = in.readLine().split(delimiter);
            int v = st.get(a[0]);
            for (int i = 1; i < a.length; i++) {
                int w = st.get(a[i]);
                addEdge(v, w);
            }
        }
    }
    
    public QGraph(Iterable<String> symbols) {
    	if (symbols == null) throw new NullPointerException("Key set is null");
        for(String symbol: symbols) {
            if (!st.containsKey(symbol))
                st.put(symbol, st.size());
        }
        
        this.V = st.size();
        this.E = 0;
        if (V == 0) throw new IllegalArgumentException("Number of vertices in a QGraph must be greater than 0");

        this.keys = new String[st.size()];
        for (String name : st.keySet()) {
            this.keys[st.get(name)] = name;
        }
        
        indegree = new int[V];
        adj = new LinkedHashSet[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new LinkedHashSet<Integer>();
        }
    }
    
    public void addEdge(String v, String w) {
    	addEdge(st.get(v), st.get(w));
    }
    
    public void addEdge(int v, int w) {
        adj[v].add(w);
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
        return adj[v];
    }
    
    public int outdegree(int v) {
        validateVertex(v);
        return adj[v].size();
    }

    public int indegree(int v) {
        validateVertex(v);
        return indegree[v];
    }

    public String name(int v) {
        return keys[v];
    }
    
    private void validateVertex(int v) {
        if (v < 0 || v >= V)
            throw new IndexOutOfBoundsException("vertex " + v + " is not between 0 and " + (V-1));
    }
    
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(V + " vertices, " + E + " edges " + NEWLINE);
        for (int v = 0; v < V; v++) {
            s.append(String.format("%s: ", name(v)));
            for (int w : adj[v]) {
                s.append(String.format("%s ", name(w)));
            }
            s.append(NEWLINE);
        }
        return s.toString();
    }
}
