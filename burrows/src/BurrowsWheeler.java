import java.util.Arrays;
import java.util.LinkedList;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;


public class BurrowsWheeler {
    // apply Burrows-Wheeler encoding, reading from standard input and writing to standard output
    public static void encode() {
        String s = BinaryStdIn.readString();
        CircularSuffixArray csa = new CircularSuffixArray(s);
        
        int first = -1;
        for (int i = 0; i < csa.length() && first < 0; i++)
            if (csa.index(i) == 0)
                first = i;
        
        BinaryStdOut.write(first);
        
        for (int i = 0; i < csa.length(); i++) {
            char c = (first == i) ? s.charAt(s.length() - 1) : s.charAt(csa.index(i) - 1);
            BinaryStdOut.write(c);
        }
        
        BinaryStdOut.close();
    }
    
    // apply Burrows-Wheeler decoding, reading from standard input and writing to standard output
    public static void decode() {
        int first = BinaryStdIn.readInt();
        String s = BinaryStdIn.readString();
        BinaryStdIn.close();
        
        char[] sortedChars = s.toCharArray();
        Arrays.sort(sortedChars);
        
        @SuppressWarnings("unchecked")
        LinkedList<Integer>[] listArray = new LinkedList[256];
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (listArray[c] == null)
                listArray[c] = new LinkedList<Integer>();
            listArray[c].add(i);
        }
        
        int[] next = new int[s.length()];
        for (int i = 0; i < s.length(); i++)
            next[i] = listArray[sortedChars[i]].removeFirst();
        
        int currentIndex = first;
        for (int i = 0; i < s.length(); i++) {
            BinaryStdOut.write(sortedChars[currentIndex]);
            currentIndex = next[currentIndex];
        }
        BinaryStdOut.close();
    }
    
    // if args[0] is '-', apply Burrows-Wheeler encoding
    // if args[0] is '+', apply Burrows-Wheeler decoding
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println(
                "Usage:\n" + "'-', apply move-to-front encoding\n" + "'+', apply move-to-front decoding\n");
            return;
        }
        if (args[0].equals("-"))
            encode();
        else if (args[0].equals("+"))
            decode();
    }
}
