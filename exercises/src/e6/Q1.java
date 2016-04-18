package e6;


import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;
import util.QUtils;


public class Q1 {
    Queue<String> result = new Queue<String>();
    
    public Q1(String fileName, int W) {
        String[] a = QUtils.readStringArray(fileName);
        sort(a, a[0].length(), W);
        for(int i = 0; i < a.length; i++) {
            result.enqueue(a[i]);
        }
    }
    
    public static void sort(String[] a, int W,  int Z) {
        int N = a.length;
        int R = 256;   // extend ASCII alphabet size
        String[] aux = new String[N];

        for (int d = W-1; d >= W - Z; d--) {
            // sort by key-indexed counting on dth character

            // compute frequency counts
            int[] count = new int[R+1];
            for (int i = 0; i < N; i++)
                count[a[i].charAt(d) + 1]++;

            // compute cumulates
            for (int r = 0; r < R; r++)
                count[r+1] += count[r];

            // move data
            for (int i = 0; i < N; i++)
                aux[count[a[i].charAt(d)]++] = a[i];

            // copy back
            for (int i = 0; i < N; i++)
                a[i] = aux[i];
        }
    }

    
    public Iterable<String> getResult() {

        return result;
    }
    
    public static void main(String[] args) {
        Q1 q1 = new Q1("q1.txt", 2);
        StdOut.println(q1.getResult());
    }
}
