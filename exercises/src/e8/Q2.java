package e8;


import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;


public class Q2 {
    private final int R;
    private int[] right;

    private String pat;
    private Queue<Character> result = new Queue<Character>();
    private int lastChar = 0;
    
    public Q2(String pat, String textToSearch) {
        this.R = 256;
        this.pat = pat;

        right = new int[R];
        for (int c = 0; c < R; c++)
            right[c] = -1;
        for (int j = 0; j < pat.length(); j++)
            right[pat.charAt(j)] = j;
        
        lastChar = pat.length() - 1;
        
        search(textToSearch);
    }

    public int search(String txt) {
        int M = pat.length();
        int N = txt.length();
        int skip;
        for (int i = 0; i <= N - M; i += skip) {
            skip = 0;
            for (int j = M-1; j >= 0; j--) {
                if (j == lastChar)
                    result.enqueue(txt.charAt(i+j));
                if (pat.charAt(j) != txt.charAt(i+j)) {
                    skip = Math.max(1, j - right[txt.charAt(i+j)]);
                    break;
                }
            }
            if (skip == 0) return i;    // found
        }
        return N;                       // not found
    }

    public Queue<Character> getResult() {
        return result;
    }
    
    public static void main(String[] args) {
        Q2 q2 = new Q2("D W I T H A L".replaceAll(" ", ""), 
            "E T O Y O U W I T H A L L M Y D U T Y A N D W I T H A L L M".replaceAll(" ", ""));
        StdOut.println(q2.getResult());
    }
}
