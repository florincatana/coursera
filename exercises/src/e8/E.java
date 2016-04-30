package e8;


import edu.princeton.cs.algs4.StdOut;


public class E {
    public static void main(String[] args) {
        Q1 q1 = new Q1("A C A C A B A A".replaceAll(" ", ""), 'C');
        StdOut.println(q1.getResult());
        StdOut.println("-----------------------------------");
        Q2 q2 = new Q2("Y A S Y O U S".replaceAll(" ", ""), 
            "S O O P P R E S S E D T O O A S Y O U S A Y A S Y O U S A Y".replaceAll(" ", ""));
        StdOut.println(q2.getResult());
        StdOut.println("-----------------------------------");
        Q3 q3 = new Q3(10, 53, 40, 28, 6, 7);
        StdOut.println(q3.getResult());
        StdOut.println("-----------------------------------");
    }
}
