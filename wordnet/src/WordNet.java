import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;


public class WordNet {
	
	private static final String CVS_SPLITTER = ",";
	private Map<String, ArrayList<Integer>> nouns = new HashMap<String, ArrayList<Integer>>();
	private Map<Integer, String> synsetMap = new HashMap<Integer, String>();
	private Digraph G;
	private SAP sap;
	
	// constructor takes the name of the two input files
	public WordNet(String synsets, String hypernyms) {
		if (synsets == null)
			throw new NullPointerException("synsets filename");
		if (hypernyms == null)
			throw new NullPointerException("synsets filename");
		
		int numberOfVertices = 0;
		In in = new In(synsets);
		String line = "";
		while ((line = in.readLine()) != null) {
			if (line.length() == 0)
				continue;
			String[] entries = line.split(CVS_SPLITTER);
			int id = Integer.parseInt(entries[0]);
			for (String entry : entries[1].split(" ")) {
				ArrayList<Integer> nounsIds = new ArrayList<Integer>();
				if (nouns.containsKey(entry)) {
					nounsIds = nouns.get(entry);
				} else {
					nouns.put(entry, nounsIds);					
				}
				nounsIds.add(id);
				synsetMap.put(id, entries[1]);
			}
			numberOfVertices++;
		}
		
		G = new Digraph(numberOfVertices);
		
		in = new In(hypernyms);
		while ((line = in.readLine()) != null) {
			String[] entries = line.split(CVS_SPLITTER);
			int hyponym = Integer.parseInt(entries[0]);
			for (int index = 1; index < entries.length; index++) {
				G.addEdge(hyponym, Integer.parseInt(entries[index]));
			}
		}
		
		DirectedCycle cycle = new DirectedCycle(this.G);
		if (cycle.hasCycle()) {
			throw new IllegalArgumentException("G - Not a DAG");
		}
		
		long rootCount = java.util.stream.IntStream.range(0, G.V()).filter(
		    v -> !G.adj(v).iterator().hasNext()).count();
		if (rootCount != 1)
			throw new IllegalArgumentException("G - Not rooted!");
		
		sap = new SAP(G);
	}
	
	// returns all WordNet nouns
	public Iterable<String> nouns() {
		return nouns.keySet();
	}
	
	// is the word a WordNet noun?
	public boolean isNoun(String word) {
		if (word == null)
			throw new NullPointerException("word");
		return nouns.keySet().contains(word);
	}
	
	// distance between nounA and nounB (defined below)
	public int distance(String nounA, String nounB) {
		validateInput(nounA, nounB);
		return sap.length(nouns.get(nounA), nouns.get(nounB));
	}
	
	// a synset (second field of synsets.txt) that is the common ancestor of
	// nounA and nounB
	// in a shortest ancestral path (defined below)
	public String sap(String nounA, String nounB) {
		validateInput(nounA, nounB);
		int a = sap.ancestor(nouns.get(nounA), nouns.get(nounB));
		return synsetMap.get(a);
	}
	
	private void validateInput(String nounA, String nounB) {
		if (!isNoun(nounA))
			throw new IllegalArgumentException("nounA - not found!");
		if (!isNoun(nounB))
			throw new IllegalArgumentException("nounB - not found!");
	}
	
	// do unit testing of this class
	public static void main(String[] args) {
		WordNet wordnet = new WordNet("synsets.txt", "hypernyms.txt");
		StdOut.printf("length = %d, ancestor = %s\n", wordnet.distance("Actifed", "antihistamine"),
		    wordnet.sap("Actifed", "antihistamine"));
	}
}
