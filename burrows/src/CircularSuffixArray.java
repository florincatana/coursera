public class CircularSuffixArray {
    private int[] indexArray = new int[0];
    
    // circular suffix array of s
    public CircularSuffixArray(String s) {
        if (s == null)
            throw new NullPointerException("The input string is null!");
        
        indexArray = java.util.stream.IntStream.range(0, s.length()).boxed().sorted(
            new java.util.Comparator<Integer>() {
                public int compare(Integer a, Integer b) {
                    for (int i = 0; i < s.length(); i++) {
                        char first = s.charAt((i + a) % s.length());
                        char second = s.charAt((i + b) % s.length());
                        if (first != second) {
                            return first - second;
                        }
                    }
                    return 0;
                }
            }).mapToInt(Integer::intValue).toArray();
        
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
    
    public static void main(String[] args) {
        CircularSuffixArray csa = new CircularSuffixArray("ABRACADABRA!");
        for (int i = 0; i < csa.length(); i++) {
            System.out.println(csa.index(i) + " ");
        }
    }
}
