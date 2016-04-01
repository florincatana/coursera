package util;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;

public class QDepthFirstOrder {
    private boolean[] marked;
    private Queue<Integer> postorder;

    public QDepthFirstOrder(QGraph G) {
        postorder = new Queue<Integer>();
        marked    = new boolean[G.V()];
        for (int v = 0; v < G.V(); v++)
            if (!marked[v]) dfs(G, v);
    }

    private void dfs(QGraph G, int v) {
        marked[v] = true;
        for (int w : G.adj(v)) {
            if (!marked[w]) {
                dfs(G, w);
            }
        }
        postorder.enqueue(v);
    }

    public Iterable<Integer> reversePost() {
        Stack<Integer> reverse = new Stack<Integer>();
        for (int v : postorder)
            reverse.push(v);
        return reverse;
    }
}
