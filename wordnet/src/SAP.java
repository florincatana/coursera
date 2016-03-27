import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;


public class SAP {
	private Digraph G;
	
	// constructor takes a digraph (not necessarily a DAG)
	public SAP(Digraph G) {
		if (G == null)
			throw new java.lang.NullPointerException("G");
		this.G = new Digraph(G);
	}
	
	// length of shortest ancestral path between v and w; -1 if no such path
	public int length(int v, int w) {
		return fullAncestor(v, w).getValue();
	}
	
	// a common ancestor of v and w that participates in a shortest ancestral
	// path; -1 if no such path
	public int ancestor(int v, int w) {
		return fullAncestor(v, w).getKey();
	}
	
	// length of shortest ancestral path between any vertex in v and any vertex
	// in w; -1 if no such path
	public int length(Iterable<Integer> v, Iterable<Integer> w) {
		return fullAncestor(v, w).getValue();
	}
	
	// a common ancestor that participates in shortest ancestral path; -1 if no
	// such path
	public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
		return fullAncestor(v, w).getKey();
	}
	
	private Map.Entry<Integer, Integer> fullAncestor(int v, int w) {
		if (!((v >= 0 && v <= this.G.V() - 1) && (w >= 0 && w <= this.G.V() - 1))) {
			throw new IndexOutOfBoundsException();
		}
		
		BreadthFirstDirectedPaths bfdV = new BreadthFirstDirectedPaths(G, v);
		BreadthFirstDirectedPaths bfdW = new BreadthFirstDirectedPaths(G, w);
		
		Map.Entry<Integer, Integer> ancestor = java.util.stream.IntStream.range(0, G.V())
				.filter(e -> bfdV.hasPathTo(e) && bfdW.hasPathTo(e))
				.mapToObj(e -> new AbstractMap.SimpleEntry<Integer, Integer>(e, bfdV.distTo(e) + bfdW.distTo(e)))
				.min(Comparator.comparing(s -> s.getValue()))
				.orElse(new AbstractMap.SimpleEntry<Integer, Integer>(-1, -1));
		
		return ancestor;
	}
	
	private Map.Entry<Integer, Integer> fullAncestor(Iterable<Integer> v, Iterable<Integer> w) {
		if (v == null)
			throw new NullPointerException("v");
		if (w == null)
			throw new NullPointerException("w");
		
        BreadthFirstDirectedPaths bfdV = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths bfdW = new BreadthFirstDirectedPaths(G, w);
		
		Map.Entry<Integer, Integer> ancestor = java.util.stream.IntStream.range(0, G.V())
				.filter(e -> bfdV.hasPathTo(e) && bfdW.hasPathTo(e))
				.mapToObj(e -> new AbstractMap.SimpleEntry<Integer, Integer>(e, bfdV.distTo(e) + bfdW.distTo(e)))
				.min(Comparator.comparing(s -> s.getValue()))
				.orElse(new AbstractMap.SimpleEntry<Integer, Integer>(-1, -1));
		
		return ancestor;
	}
	
	// do unit testing of this class
	public static void main(String[] args) {
		In in = new In(args[0]);
		Digraph G = new Digraph(in);
		SAP sap = new SAP(G);
		int length = sap.length(Arrays.asList(2121, 233), Arrays.asList(131, 22));
		int ancestor = sap.ancestor(Arrays.asList(2121, 233), Arrays.asList(131, 22222));
		StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
	}
}
