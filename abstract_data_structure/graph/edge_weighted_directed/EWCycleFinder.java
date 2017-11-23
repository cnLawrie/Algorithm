package ADT.Graph.ShortestPath;

import ADT.Graph.Directed.Digraph;
import ADT.Graph.EdgeWeightedDirected.DirectedEdge;
import ADT.Graph.EdgeWeightedDirected.EdgeWeightedDigraph;
import ADT.Stack;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class EWCycleFinder {
    private boolean[] marked;        // marked[v] = has vertex v been marked?
    private DirectedEdge[] edgeTo;            // edgeTo[v] = previous vertex on path to v
    private boolean[] onStack;       // onStack[v] = is vertex on the stack?
    private Stack<DirectedEdge> cycle;    // directed cycle (or null if no such cycle)

    public EWCycleFinder(EdgeWeightedDigraph G) {
        marked = new boolean[G.V()];
        onStack = new boolean[G.V()];
        edgeTo = new DirectedEdge[G.V()];
        for (int v = 0; v < G.V(); v++)
            if (!marked[v])
                dfs(G, v);
    }

    // check that algorithm computes either the topological order or finds a directed cycle
    private void dfs(EdgeWeightedDigraph G, int v) {
        onStack[v] = true;
        marked[v] = true;
        for (DirectedEdge e : G.adj(v)) {
            int w = e.to();

            if (this.hasCycle())
                return;
            else if (!marked[w]) {
                edgeTo[w] = e;
                dfs(G, w);
            } else if (onStack[w]) {
                cycle = new Stack<DirectedEdge>();
                DirectedEdge x = e;
                for (; x.from() != w; x = edgeTo[x.from()]) {
                    cycle.push(x);
                }
                cycle.push(x);
                return;
            }
        }
        onStack[v] = false;
    }

    public boolean hasCycle() {
        return cycle != null;
    }

    public Iterable<DirectedEdge> cycle() {
        return cycle;
    }
}
