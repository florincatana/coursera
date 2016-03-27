import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

public class SAPTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void sap_length1() {
	    In in = new In("digraph1.txt");
	    Digraph G = new Digraph(in);
	    SAP sap = new SAP(G);
	    assertEquals(sap.length(2,3), 3);
	    assertEquals(sap.length(3,2), 3);
	    assertEquals(sap.length(3,11), 4);
	    assertEquals(sap.length(11,3), 4);	
	}
}
