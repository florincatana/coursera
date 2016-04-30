package e7;

import edu.princeton.cs.algs4.StdOut;


public class Q1 {
    private static final int R = 256;        // extended ASCII

    private Node root;      // root of trie
    private int N;          // number of keys in trie
    private int result = 0;
    private String allStrings;

    // R-way trie node
    private static class Node {
        private Node[] next = new Node[R];
        private boolean isString;
    }

    public Q1(String[] list) {
        allStrings = String.join(" ", list);
        for(String s: list)
            add(s);
    }

    public void add(String key) {
        root = add(root, key, 0);
    }

    private Node add(Node x, String key, int d) {
        if (x == null) {
            x = new Node();
            result++;
        }
        if (d == key.length()) {
            if (!x.isString) N++;
            x.isString = true;
        }
        else {
            char c = key.charAt(d);
            x.next[c] = add(x.next[c], key, d+1);
        }
        return x;
    }
    
    public int getResult() {
        return result;
    }
    
    public String toString() {
        return allStrings;
    }
    
    public static void main(String[] args) {
        Q1 q1 = new Q1( "323 22 121 2221 33 12 221".split(" "));
        StdOut.println(q1.toString());
        StdOut.println(q1.getResult());
    }
}
