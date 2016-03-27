import org.junit.*;

public class WordNetTest {
	WordNet wordnet = null;

	@Before
	public void setUp() throws Exception {
		wordnet = new WordNet("", "");
	}

	@Test(expected=NullPointerException.class)
	public void testC1() {
		new WordNet(null, "");
	}
	
	@Test(expected=NullPointerException.class)
	public void testC2() {
		new WordNet("", null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testSAP1() {
		wordnet.sap(null, "");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testSAP2() {
		wordnet.sap("", null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testSAP3() {
		new WordNet(null, "");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testSAP4() {
		new WordNet("", null);
	}

}
