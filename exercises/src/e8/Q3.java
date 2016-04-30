package e8;


import edu.princeton.cs.algs4.StdOut;


public class Q3 {
    private long result = 0;
    
    public Q3(long R, long Q, long H, long Ph, long first, long second) {
        long h = (((H - first * Ph)*R + second)%Q + Q) % Q;
        result = h;
    }
    
    public long getResult() {
        return result;
    }
    
    public static void main(String[] args) {
        Q3 q3 = new Q3(10, 83, 4, 77, 6, 8);
        StdOut.println(q3.getResult());
    }
}
