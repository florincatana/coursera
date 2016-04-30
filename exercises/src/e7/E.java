package e7;


import edu.princeton.cs.algs4.StdOut;


public class E {
    public static void main(String[] args) {
        Q1 q1 = new Q1("131 2221 212 2323 331 1121 31".split(" "));
        StdOut.println(q1.toString());
        StdOut.println(q1.getResult());
        StdOut.println("-----------------------------------");
        Q2 q2 = new Q2("232 145 243 324 314 535 552".split(" "));
        StdOut.println(q2.toString());
        StdOut.println(q2.getResult());
        StdOut.println("-----------------------------------");
    }
}
