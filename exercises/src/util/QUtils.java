package util;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.sun.accessibility.internal.resources.accessibility;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.EdgeWeightedGraph;
import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public final class QUtils {
	
	public static QGraph readQGraphFromFile(String fileName) {
		In in = new In(fileName);

		List<Map.Entry<String, String>> edges = Arrays.asList(in.readAllLines()).stream()
				.map(s -> s.trim())
				.filter(s -> s.length() > 0 && s.contains(":"))
				.map(s -> s.split(":"))
				.filter(a -> a.length == 2)
				.map(s -> new AbstractMap.SimpleEntry<String, String>(s[0].trim(), s[1].trim()))
				.filter(e -> e.getKey().length() > 0 && e.getValue().length() > 0)
				.flatMap(e -> Arrays.asList(e.getValue().split(" ")).stream()
					.map(s->s.trim()).filter(s -> s.length() >0)
					.map(s -> new AbstractMap.SimpleEntry<String, String>(e.getKey(), s)))
				.collect(Collectors.toList());
		//StdOut.println(edges);
		
		Collection<String> keys = edges.stream()
				.flatMap(e -> Arrays.asList(e.getKey(), e.getValue()).stream())
				.distinct()
				.sorted()
				.collect(Collectors.toList());
		//StdOut.println(keys);
		
		QGraph G = new QGraph(keys);
		edges.stream().forEach(e -> G.addEdge(e.getKey(), e.getValue()));
		return G;
	}
	
	
	public static Graph readGraphFromFile(String fileName) {
		In in = new In(fileName);

		List<Map.Entry<Integer, Integer>> edges = Arrays.asList(in.readAllLines()).stream()
				.map(s -> s.trim())
				.filter(s -> s.length() > 0 && s.contains(":"))
				.map(s -> s.split(":"))
				.map(s -> new AbstractMap.SimpleEntry<String, String>(s[0], s[1]))
				.flatMap(e -> Arrays.asList(e.getValue().split(" ")).stream()
					.map(s->s.trim()).filter(s -> s.length() == 1)
					//.sorted()
					.map(s -> new AbstractMap.SimpleEntry<Integer, Integer>(e.getKey().codePointAt(0) - 65, s.codePointAt(0) - 65))
					.map(s -> (s.getKey() < s.getValue())  ? s : new AbstractMap.SimpleEntry<Integer, Integer>(s.getValue(), s.getKey())))
				.distinct()
				.collect(Collectors.toList());
		
		Integer V = edges.stream()
				.flatMap(e -> Arrays.asList(e.getValue(), e.getKey()).stream())
				.max(Integer::compare)
				.get() + 1;
		Graph G = new Graph(V);
		edges.stream().forEach(e -> G.addEdge(e.getKey(), e.getValue()));
		//StdOut.println(edges);
		return G;
	}
	
	public static Digraph readDigraphFromFile(String fileName) {
		In in = new In(fileName);

		List<Map.Entry<Integer, Integer>> edges = Arrays.asList(in.readAllLines()).stream()
				.map(s -> s.trim())
				.filter(s -> s.length() > 0 && s.contains(":"))
				.map(s -> s.split(":"))
				.map(s -> new AbstractMap.SimpleEntry<String, String>(s[0], s[1]))
				.flatMap(e -> Arrays.asList(e.getValue().split(" ")).stream()
					.map(s->s.trim()).filter(s -> s.length() == 1)
					.map(s -> new AbstractMap.SimpleEntry<Integer, Integer>(e.getKey().codePointAt(0) - 65, s.codePointAt(0) - 65)))
				.collect(Collectors.toList());
		
		Integer V = edges.stream()
				.flatMap(e -> Arrays.asList(e.getValue(), e.getKey()).stream())
				.max(Integer::compare)
				.get() + 1;
		Digraph G = new Digraph(V);
		edges.stream().forEach(e -> G.addEdge(e.getKey(), e.getValue()));
		StdOut.println(edges);
		return G;
	}
	
	public static EdgeWeightedGraph readEdgeWeightedGraphFromFile(String fileName) {
            In in = new In(fileName);

            List<Edge> edges = Arrays.asList(in.readAllLines()).stream()
                            .map(s -> s.trim())
                            .filter(s -> s.length() > 0 && s.contains("-"))
                            .map(s -> s.replaceAll("\\s+", " ").split(" "))
                            .map(s -> new Edge(s[0].split("-")[0].codePointAt(0) - 65, s[0].split("-")[1].codePointAt(0) - 65, Double.parseDouble(s[1])))
                            .collect(Collectors.toList());
            
            Integer V = edges.stream()
                            .map(e -> Arrays.asList(e.either(), e.other(e.either())))
                            .flatMap(e -> e.stream())
                            .max(Integer::compare)
                            .get() + 1;
            EdgeWeightedGraph G = new EdgeWeightedGraph(V);
            edges.stream().forEach(e -> G.addEdge(e));
           // StdOut.println(edges);
            
            return G;	    
	}
	
    public static EdgeWeightedDigraph readEdgeWeightedDigraphFromFile(String fileName) {
        In in = new In(fileName);
        
        List<DirectedEdge> edges = Arrays.asList(in.readAllLines()).stream().map(s -> s.trim()).filter(
            s -> s.length() > 0 && s.contains("->")).map(s -> s.replaceAll("\\s+", " ").split(" ")).map(
                s -> new DirectedEdge(s[0].split("->")[0].codePointAt(0) - 65, s[0].split("->")[1].codePointAt(0) - 65,
                        Double.parseDouble(s[1]))).collect(Collectors.toList());
        
        Integer V = edges.stream().map(e -> Arrays.asList(e.from(), e.to())).flatMap(
            e -> e.stream()).max(Integer::compare).get() + 1;
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(V);
        edges.stream().forEach(e -> G.addEdge(e));
        // StdOut.println(edges);
        
        return G;
    }
    
    public static FlowNetwork readFlowNetworkFromFile(String fileName) {
        In in = new In(fileName);
        List<FlowEdge> edges = Arrays.asList(in.readAllLines()).stream().map(s -> s.trim()).filter(
            s -> s.length() > 0 && s.contains("->")).map(s -> s.replaceAll("\\s+", " ").split(" ")).map(
                s -> new FlowEdge(s[0].split("->")[0].codePointAt(0) - 65, s[0].split("->")[1].codePointAt(0) - 65,
                        Double.parseDouble(s[3]), Double.parseDouble(s[1]))).collect(Collectors.toList());
        //StdOut.println(edges);
        
        Integer V = edges.stream().map(e -> Arrays.asList(e.from(), e.to())).flatMap(
            e -> e.stream()).max(Integer::compare).get() + 1;
        FlowNetwork G = new FlowNetwork(V);
        edges.stream().forEach(e -> G.addEdge(e));
        return G;
    }
    
    public static int[] readArray(String fileName) {
        In in = new In(fileName);
        int[] result =  Arrays.asList(in.readAllLines()).stream()
                .map(s -> s.trim())
                .filter(s -> s.length() > 0)
                .map(s -> s.replaceAll("\\s+", " ").split(" "))
                .flatMapToInt(s -> Arrays.asList(s).stream().mapToInt( v->Integer.valueOf(v).intValue()))
                .toArray();
        return result;
    }
    
    public static String[] readStringArray(String fileName) {
        In in = new In(fileName);
        List<String> result =  Arrays.asList(in.readAllLines()).stream()
                .map(s -> s.trim())
                .filter(s -> s.length() > 0)
                .map(s -> s.replaceAll("\\s+", " ").split(" "))
                .flatMap(s -> Arrays.asList(s).stream())
                .map(s->s.trim())
                .filter(s -> s.length() > 0)
                .collect(Collectors.toList());
        String[] a = new String[result.size()];
        return result.toArray(a);
    }
	
}
