package e5;


import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;
import util.QUtils;


public class Q2 {
        private boolean[] marked;
        private FlowEdge[] edgeTo;
      
        public Q2(FlowNetwork G, int s, int t) {
            validate(s, G.V());
            validate(t, G.V());
            if (s == t)               throw new IllegalArgumentException("Source equals sink");

            findAugmentingPath(G, s, t);
        }

        private void validate(int v, int V)  {
            if (v < 0 || v >= V)
                throw new IndexOutOfBoundsException("vertex " + v + " is not between 0 and " + (V-1));
        }

        private void findAugmentingPath(FlowNetwork G, int s, int t) {
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
        }

    public Iterable<String> getResult() {
        Queue<String> result = new Queue<String>();
        for(int i = 0; i < marked.length; i++) {
            if (marked[i])
                result.enqueue(String.format("%s", (char)('A' + i)));
        }

        return result;
    }
    
    public static void main(String[] args) {
        FlowNetwork G2 = QUtils.readFlowNetworkFromFile("q2.txt");
        Q2 q2 = new Q2(G2, (int)('A'-'A'), (int)('J'-'A'));
        StdOut.println(q2.getResult());
    }
}
