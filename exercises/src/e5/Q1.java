package e5;

import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;
import util.QUtils;


public class Q1 {
    private boolean[] marked;
    private FlowEdge[] edgeTo;
    Stack<String> result = new Stack<String>();
  
    public Q1(FlowNetwork G, int s, int t) {
        validate(s, G.V());
        validate(t, G.V());
        if (s == t)               throw new IllegalArgumentException("Source equals sink");
        int value = 0;
        while (hasAugmentingPath(G, s, t)) {

            // compute bottleneck capacity
            double bottle = Double.POSITIVE_INFINITY;
            for (int v = t; v != s; v = edgeTo[v].other(v)) {
                bottle = Math.min(bottle, edgeTo[v].residualCapacityTo(v));
            }

            // augment flow
            for (int v = t; v != s; v = edgeTo[v].other(v)) {
                result.push(String.format("%s", (char)('A' + v)));
                edgeTo[v].addResidualFlowTo(v, bottle); 
            }
            result.push(String.format("%s", (char)('A')));

            value += bottle;
        }
    }

    private void validate(int v, int V)  {
        if (v < 0 || v >= V)
            throw new IndexOutOfBoundsException("vertex " + v + " is not between 0 and " + (V-1));
    }

    private boolean hasAugmentingPath(FlowNetwork G, int s, int t) {
        edgeTo = new FlowEdge[G.V()];
        marked = new boolean[G.V()];

        // breadth-first search
        Queue<Integer> queue = new Queue<Integer>();
        queue.enqueue(s);
        marked[s] = true;
        while (!queue.isEmpty() && !marked[t]) {
            int v = queue.dequeue();

            for (FlowEdge e : G.adj(v)) {
                int w = e.other(v);

                // if residual capacity from v to w
                if (e.residualCapacityTo(w) > 0) {
                    if (!marked[w]) {
                        edgeTo[w] = e;
                        marked[w] = true;
                        queue.enqueue(w);
                    }
                }
            }
        }
        return marked[t];
    }
    
    public Iterable<String> getResult() {
        return result;
    }
    
    public static void main(String[] args) {
        FlowNetwork G1 = QUtils.readFlowNetworkFromFile("q1.txt");
        Q1 q1 = new Q1(G1, (int)('A'-'A'), (int)('J'-'A'));
        StdOut.println(G1);
        StdOut.println(q1.getResult());
    }
}
