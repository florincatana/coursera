public class CircularSuffixArray {
    private static final int R = 256;
    private static final int CUTOFF = 15;
    private int[] indexArray = new int[0];
    
    public CircularSuffixArray(String s) {
        if (s == null)
            throw new NullPointerException("The input string is null!");
        
        indexArray = java.util.stream.IntStream.range(0, s.length()).toArray();
        sort(s, 0, indexArray.length - 1, 0, new int[indexArray.length]);
    }
    
    public int length() {
        return indexArray.length;
    }
    
    public int index(int i) {
        if (i < 0 || i >= length())
            throw new IndexOutOfBoundsException("Invalid index " + i);
        
        return indexArray[i];
    }
    
    private void sort(String s, int lo, int hi, int d, int[] aux) {
        if (hi <= lo + CUTOFF) {
            insertion(s, lo, hi, d);
            return;
        }
        
        int[] count = new int[R+2];
        for (int i = lo; i <= hi; i++) {
            int c = charAt(s, indexArray[i], d);
            count[c+2]++;
        }

        for (int r = 0; r < R+1; r++)
            count[r+1] += count[r];

        for (int i = lo; i <= hi; i++) {
            int c = charAt(s, indexArray[i], d);
            aux[count[c+1]++] = indexArray[i];
        }

        for (int i = lo; i <= hi; i++) 
            indexArray[i] = aux[i - lo];


        for (int r = 0; r < R; r++)
            sort(s, lo + count[r], lo + count[r+1] - 1, d + 1, aux);
    }
    
    private int charAt(String s, int i, int d) {
        return s.charAt((i + d) % s.length());
    }
    
    private void insertion(String s, int lo, int hi, int d) {
        for (int i = lo; i <= hi; i++)
            for (int j = i; j > lo && less(s, indexArray[j], indexArray[j-1], d); j--)
                exch(j, j-1);
    }

    private void exch(int i, int j) {
        int temp = indexArray[i];
        indexArray[i] = indexArray[j];
        indexArray[j] = temp;
    }

    private boolean less(String s, int v, int w, int d) {
        for (int i = d; i < s.length(); i++)
            if (charAt(s, v, i) != charAt(s, w, i))
                return charAt(s, v, i) < charAt(s, w, i);
        return false;
    }

    public static void main(String[] args) {
        CircularSuffixArray csa = new CircularSuffixArray("ABRACADABRA!");
        for (int i = 0; i < csa.length(); i++) {
            edu.princeton.cs.algs4.StdOut.println(csa.index(i) + " ");
        }
    }
}
