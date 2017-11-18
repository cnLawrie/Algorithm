package ADT.Graph.EdgeWeightedDirected;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.EdgeWeightedGraph;
import edu.princeton.cs.algs4.In;

public class EdgeWeightedDigraph {
    private final int V;
    private int E;
    private Bag<DirectedEdge>[] adj;

    public EdgeWeightedDigraph(int V) {
        this.V = V;
        this.E = 0;
        adj = new Bag[V];
        for (int v = 0; v < V; v++)
            adj[v] = new Bag<>();
    }

    public EdgeWeightedDigraph(In in) {
        this.V = in.readInt();
        this.E = in.readInt();
        adj = new Bag[V];
        for (int v = 0; v < V; v++)
            adj[v] = new Bag<>();

        for (int i = 0; i < E; i++) {
            int v = in.readInt();
            int w = in.readInt();
            double weight = in.readDouble();
            DirectedEdge edge = new DirectedEdge(v, w, weight);
            adj[v].add(edge);
        }
    }

    public void addEdge(DirectedEdge edge) {
        adj[edge.from()].add(edge);
        E++;
    }

    public Iterable<DirectedEdge> adj(int v) {
        return adj[v];
    }

    public Iterable<DirectedEdge> edges() {
        Bag<DirectedEdge> bag = new Bag<>();
        for (int v = 0; v < V; v++)
            for (DirectedEdge e : adj[v])
                bag.add(e);
        return bag;
    }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

}
