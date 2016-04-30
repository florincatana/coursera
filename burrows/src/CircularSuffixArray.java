import edu.princeton.cs.algs4.StdOut;

public class CircularSuffixArray {
    private static final int R = 256;
    private int[] indexArray = new int[0];
    
    // circular suffix array of s
    public CircularSuffixArray(String s) {
        if (s == null)
            throw new NullPointerException("The input string is null!");
        
        indexArray = java.util.stream.IntStream.range(0, s.length()).toArray();
        for(int i = 0; i < indexArray.length; i++)
            StdOut.print(indexArray[i] + "-");
        StdOut.println();
        int[] aux = new int[indexArray.length];
        //sort(s, 0, indexArray.length - 1, 0, aux);
    }
    
    // length of s
    public int length() {
        return indexArray.length;
    }
    
    // returns index of ith sorted suffix
    public int index(int i) {
        if (i < 0 || i >= length())
            throw new IndexOutOfBoundsException("Invalid index " + i);
        
        return indexArray[i];
    }
    
    private void sort(String s, int lo, int hi, int d, int[] aux) {
        // compute frequency counts
        int[] count = new int[R+2];
        for (int i = lo; i <= hi; i++) {
            int c = charAt(s, indexArray[i], d);
            count[c+2]++;
        }

        // transform counts to indicies
        for (int r = 0; r < R+1; r++)
            count[r+1] += count[r];

        // distribute
        for (int i = lo; i <= hi; i++) {
            int c = charAt(s, indexArray[i], d);
            aux[count[c+1]++] = indexArray[i];
        }

        // copy back
        for (int i = lo; i <= hi; i++) 
            indexArray[i] = aux[i - lo];


        // recursively sort for each character (excludes sentinel -1)
        for (int r = 0; r < R; r++)
            sort(s, lo + count[r], lo + count[r+1] - 1, d + 1, aux);
    }
    
    // return dth character of s, -1 if d = length of string
    private int charAt(String s, int i, int d) {
        //return s.charAt((i + d) % s.length());
        if (d < s.length()-i) return s.charAt(d + i);        
        else return s.charAt(d - s.length() + i);  
    }
    
    public static void main(String[] args) {
        CircularSuffixArray csa = new CircularSuffixArray("ABRACADABRA!");
        for (int i = 0; i < csa.length(); i++) {
            System.out.println(csa.index(i) + " ");
        }
    }
}
