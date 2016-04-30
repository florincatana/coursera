import java.util.LinkedList;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;


public class MoveToFront {
    private static final int N = 256;
    
    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
        LinkedList<Integer> charList = createCharList();
        BinaryStdIn.readString().chars().forEach(c -> {
            int index = charList.indexOf(c);
            BinaryStdOut.write((char) index, 8);
            charList.remove(index);
            charList.addFirst(c);
        });
        BinaryStdOut.close();
    }
    
    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
        LinkedList<Integer> charList = createCharList();
        BinaryStdIn.readString().chars().forEach(index -> {
            charList.addFirst(charList.remove(index));
            BinaryStdOut.write((char) charList.getFirst().intValue(), 8);
        });
        BinaryStdOut.close();
    }
    
    private static LinkedList<Integer> createCharList() {
        LinkedList<Integer> charList = java.util.stream.IntStream.range(0, N).boxed().collect(
            java.util.stream.Collectors.toCollection(LinkedList::new));
        
        return charList;
    }
    
    // if args[0] is '-', apply move-to-front encoding
    // if args[0] is '+', apply move-to-front decoding
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
