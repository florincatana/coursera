package e7;


import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;


public class Q2 {
    private Node root;   // root of TST

    private static class Node {
        private char c;                        // character
        private Node left, mid, right;  // left, middle, and right subtries
        private int val;                     // value associated with string
        private int depth;
    }
    
    private Queue<Integer> result = new Queue<Integer>();
    private String allStrings;
    
    public Q2(String[] list) {
        allStrings = String.join(" ", list);
        int index = 0;
        for(String s: list)
            put(s, index++);
        for(String s: list)
            result.enqueue(depth(s));
    }
    
    public int depth(String key) {
        if (key == null) throw new NullPointerException();
        if (key.length() == 0) throw new IllegalArgumentException("key must have length >= 1");
        Node x = get(root, key, 0);
        if (x == null) return -1;
        return x.depth;
    }

    public boolean contains(String key) {
        return get(key) != -1;
    }

    public int get(String key) {
        if (key == null) throw new NullPointerException();
        if (key.length() == 0) throw new IllegalArgumentException("key must have length >= 1");
        Node x = get(root, key, 0);
        if (x == null) return -1;
        return x.val;
    }

    // return subtrie corresponding to given key
    private Node get(Node x, String key, int d) {
        if (key == null) throw new NullPointerException();
        if (key.length() == 0) throw new IllegalArgumentException("key must have length >= 1");
        if (x == null) return null;
        char c = key.charAt(d);
        if      (c < x.c)              return get(x.left,  key, d);
        else if (c > x.c)              return get(x.right, key, d);
        else if (d < key.length() - 1) return get(x.mid,   key, d+1);
        else                           return x;
    }

    public void put(String key, int val) {
        root = put(root, key, val, 0, 0);
    }

    private Node put(Node x, String key, int val, int d, int depth) {
        char c = key.charAt(d);
        if (x == null) {
            x = new Node();
            x.c = c;
            x.depth = depth;
        }
        if      (c < x.c)               x.left  = put(x.left,  key, val, d, depth + 1);
        else if (c > x.c)               x.right = put(x.right, key, val, d, depth + 1);
        else if (d < key.length() - 1)  x.mid   = put(x.mid,   key, val, d+1, depth + 1);
        else                            x.val   = val;
        return x;
    }
    
    public Iterable<Integer> getResult() {
        return result;
    }
    
    public String toString() {
        return allStrings;
    }
    
    public static void main(String[] args) {
        Q2 q2 = new Q2("312 255 415 314 431 311 421".split(" "));
        StdOut.println(q2.toString());
        StdOut.println(q2.getResult());
    }
}
