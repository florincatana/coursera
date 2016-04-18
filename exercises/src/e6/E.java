package e6;


import edu.princeton.cs.algs4.StdOut;


public class E {
    public static void main(String[] args) {
        Q1 q1 = new Q1("q1.txt", 2);
        StdOut.println(q1.getResult());
        StdOut.println("-----------------------------------");
        Q2 q2 = new Q2("q2.txt", 3);
        StdOut.println(q2.getResult());
        StdOut.println("-----------------------------------");
        Q3 q3 = new Q3("q3.txt", 1);
        StdOut.println(q3.getResult());
        StdOut.println("-----------------------------------");
    }
}
