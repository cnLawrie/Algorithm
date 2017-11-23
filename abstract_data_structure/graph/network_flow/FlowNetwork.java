package ADT.Graph.NetworkFlow;

import ADT.Bag;
import edu.princeton.cs.algs4.In;

public class FlowNetwork {
    private final int V;
    private int E;
    private Bag<FlowEdge>[] adj; //与EdgeWeightedDigraph仅有此处不同

    public FlowNetwork(int V) {
        this.V = V;
        this.E = 0;
        adj = new Bag[V];
        for (int v = 0; v < V; v++)
            adj[v] = new Bag<>();
    }

    public FlowNetwork(In in) {
        this.V = in.readInt();
        this.E = in.readInt();
        adj = new Bag[V];
        for (int v = 0; v < V; v++)
            adj[v] = new Bag<>();

        for (int i = 0; i < E; i++) {
            int v = in.readInt();
            int w = in.readInt();
            double weight = in.readDouble();
            FlowEdge edge = new FlowEdge(v, w, weight);
            adj[v].add(edge);
        }
    }

    public void addEdge(FlowEdge edge) {
        adj[edge.from()].add(edge);
        E++;
    }

    public Iterable<FlowEdge> adj(int v) {
        return adj[v];
    }

    public Iterable<FlowEdge> edges() {
        Bag<FlowEdge> bag = new Bag<>();
        for (int v = 0; v < V; v++)
            for (FlowEdge e : adj[v])
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
