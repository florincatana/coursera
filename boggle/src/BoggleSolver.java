import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;


public class BoggleSolver {
    private BoogleTrie dictionary;
    
    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(final String[] dictionary) {
        this.dictionary = new BoogleTrie();
        for (String s : dictionary) {
            this.dictionary.add(s);
        }
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        Set<String> words = new TreeSet<String>();
        
        if (board == null)
            throw new java.lang.IllegalArgumentException("Null board!");
        
        for (int r = 0; r < board.rows(); r++)
            for (int c = 0; c < board.cols(); c++)
                backtracking(board, r, c, new LinkedHashSet<Integer>(), concat("", board.getLetter(r, c)), words);
        
        return words;
    }
    
    private void backtracking(BoggleBoard board, int row, int col, Set<Integer> cells, String currentWord,
                              Set<String> words) {
        // now words with this prefix
        if (currentWord.length() == 1 && !dictionary.startsWith(currentWord)) {
            return;
        }
        
        int currentIndex = row * board.cols() + col;
        cells.add(currentIndex);
        
        if (isValid(currentWord))
            words.add(currentWord);
        
        for (int i = -1; i <= 1; i++) {
            int r = row + i;
            if (r < 0 || r >= board.rows())
                continue;
            for (int j = -1; j <= 1; j++) {
                int c = col + j;
                if (c < 0 || c >= board.cols())
                    continue;
                int thisIndex = r * board.cols() + c;
                if (cells.contains(thisIndex))
                    continue;
                String newWord = concat(currentWord, board.getLetter(r, c));
                if (dictionary.startsWith(newWord)) {
                    backtracking(board, r, c, cells, newWord, words);
                }
            }
        }
        cells.remove(currentIndex);
    }
    
    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        if (word == null) {
            throw new IllegalArgumentException("Word is mandatory");
        }
        if (word.length() <= 2 || !dictionary.contains(word)) {
            return 0;
        }
        
        switch (word.length()) {
            case 3:
            case 4:
                return 1;
            case 5:
                return 2;
            case 6:
                return 3;
            case 7:
                return 5;
            case 8:
            default:
                return 11;
        }
    }
    
   /* private String concat(String word, char letter) {
        return word + (letter == 'Q' ? "QU" : letter);
    }*/
    
    private String concat(String word, char letter) {
        StringBuilder sb = new StringBuilder(); 
        sb.append(word);
        sb.append(letter == 'Q' ? "QU" : letter);
        return sb.toString();
    }
    
    private boolean isValid(String word) {
        if (word == null || word.length() < 3)
            return false;
        return dictionary.contains(word);
    }
    
    public static void main(String[] args) {
        String dictionaryFile = "dictionary-yawl.txt";
        String boardFile = "board-q.txt";
        In in = new In(dictionaryFile);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(boardFile);
        long startTime = System.currentTimeMillis();
        int score = 0;
        for(int i = 0; i< 3000; i++)
        for (String word : solver.getAllValidWords(board)) {
            //StdOut.println(word);
            score += solver.scoreOf(word);
        }
        long endTime = System.currentTimeMillis();
        long seconds = (endTime - startTime) / 10;
        StdOut.println("Score = " + score);
        StdOut.println("Time = " + seconds);
    }
}
