package e6;


import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;
import util.QUtils;


public class Q3 {
    Queue<String> result = new Queue<String>();
    static boolean isFirst = true;
    
    public Q3(String fileName, int W) {
        String[] a = QUtils.readStringArray(fileName);
        sort(a, W);
        for(int i = 0; i < a.length; i++) {
            result.enqueue(a[i]);
        }
    }
    
    /**  
     * Rearranges the array of strings in ascending order.
     *
     * @param a the array to be sorted
     */
    public static void sort(String[] a, int W) {
        //StdRandom.shuffle(a);
        sort(a, 0, a.length-1, 0);
        assert isSorted(a);
    }

    // return the dth character of s, -1 if d = length of s
    private static int charAt(String s, int d) { 
        assert d >= 0 && d <= s.length();
        if (d == s.length()) return -1;
        return s.charAt(d);
    }


    // 3-way string quicksort a[lo..hi] starting at dth character
    private static void sort(String[] a, int lo, int hi, int d) { 
        int lt = lo, gt = hi;
        int v = charAt(a[lo], d);
        int i = lo + 1;
        while (i <= gt) {
            StdOut.println(String.format("%d %d %d", lt, i, gt));
            int t = charAt(a[i], d);
            if      (t < v) exch(a, lt++, i++);
            else if (t > v) exch(a, i, gt--);
            else              i++;
        }
       /* if (!isFirst)
            return;
        isFirst = false;
        // a[lo..lt-1] < v = a[lt..gt] < a[gt+1..hi]. 
        sort(a, lo, lt-1, d);
        if (v >= 0) sort(a, lt, gt, d+1);
        sort(a, gt+1, hi, d);*/
        
    }
    
    // exchange a[i] and a[j]
    private static void exch(String[] a, int i, int j) {
        String temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    // is the array sorted
    private static boolean isSorted(String[] a) {
        for (int i = 1; i < a.length; i++)
            if (a[i].compareTo(a[i-1]) < 0) return false;
        return true;
    }
    
    public Iterable<String> getResult() {
        
        return result;
    }
    
    public static void main(String[] args) {
        Q3 q3 = new Q3("q3.txt", 1);
        StdOut.println(q3.getResult());
    }
}
