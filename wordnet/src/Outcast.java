import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
	private WordNet wordnet;

	// constructor takes a WordNet object
	public Outcast(WordNet wordnet) {
		if (wordnet == null)
			throw new NullPointerException("wordnet");
		this.wordnet = wordnet;
	}

	// given an array of WordNet nouns, return an outcast
	public String outcast(String[] nouns) {
		if (nouns == null)
			throw new NullPointerException("nouns");
		
		String outcast = Arrays.asList(nouns).stream()
				.flatMap(a -> Arrays.asList(nouns).stream().filter(e -> !e.equals(a))
						.map(e -> new AbstractMap.SimpleEntry<String, String>(a, e)))
				.collect(java.util.stream.Collectors.groupingBy(Map.Entry::getKey,
						java.util.stream.Collectors.mapping(Map.Entry::getValue, java.util.stream.Collectors.toList())))
				.entrySet().stream()
				.map(e -> new AbstractMap.SimpleEntry<String, Integer>(e.getKey(),
						e.getValue().stream().map(nounB -> wordnet.distance(e.getKey(), nounB)).reduce(0,
								(s, i) -> s + i)))
				.max(Comparator.comparing(s -> s.getValue()))
				.orElse(new AbstractMap.SimpleEntry<String, Integer>("", -1)).getKey();

		return outcast;
	}

	// see test client below
	public static void main(String[] args) {
	    WordNet wordnet = new WordNet(args[0], args[1]);
	    Outcast outcast = new Outcast(wordnet);
	    for (int t = 2; t < args.length; t++) {
	        In in = new In(args[t]);
	        String[] nouns = in.readAllStrings();
	        StdOut.println(args[t] + ": " + outcast.outcast(nouns));
	    }
	}
}