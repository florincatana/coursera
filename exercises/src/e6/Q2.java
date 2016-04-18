package e6;


import java.util.Arrays;
import java.util.stream.IntStream;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;
import util.QUtils;


public class Q2 {
    private static final int R             = 256;   // extended ASCII alphabet size
    Queue<String> result = new Queue<String>();
    
    private static boolean firstTime = true;
    
    public Q2(String fileName, int W) {
        String[] a = QUtils.readStringArray(fileName);
        sort(a, W);
        for(int i = 0; i < a.length; i++) {
            result.enqueue(a[i]);
        }
    }
    
    public static void sort(String[] a, int W) {
        int N = a.length;
        String[] aux = new String[N];
        sort(a, 0, N-1, 0, aux, W);
    }

    // return dth character of s, -1 if d = length of string
    private static int charAt(String s, int d) {
        assert d >= 0 && d <= s.length();
        if (d == s.length()) return -1;
        return s.charAt(d);
    }

    // sort from a[lo] to a[hi], starting at the dth character
    private static void sort(String[] a, int lo, int hi, int d, String[] aux, int W) {
        
        //StdOut.println(String.format("%d, %d, %d", lo, hi, d));
        if (W == d) return;
        // compute frequency counts
        int[] count = new int[R+2];
        for (int i = lo; i <= hi; i++) {
            int c = charAt(a[i], d);
            count[c+2]++;
        }

        // transform counts to indicies
        for (int r = 0; r < R+1; r++)
            count[r+1] += count[r];

        // distribute
        for (int i = lo; i <= hi; i++) {
            int c = charAt(a[i], d);
            aux[count[c+1]++] = a[i];
        }

        // copy back
        for (int i = lo; i <= hi; i++) 
            a[i] = aux[i - lo];
        
        count = Arrays.stream(count)
              .filter(v -> v > 0)
              .toArray();
        
        if (d == 0) {
            sort(a, lo, lo + count[0] - 1, d+1, aux, W);
        } else {
            sort(a, lo + count[0], lo + count[1] - 1, d+1, aux, W);
        }
    }

    public Iterable<String> getResult() {
        return result;
    }
    
    public static void main(String[] args) {
        Q2 q2 = new Q2("q2.txt", 3);
        StdOut.println(q2.getResult());
    }
}
