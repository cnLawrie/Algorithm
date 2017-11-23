package ADT.Graph.EdgeWeighted;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;

public class EdgeWeightedGraph {
    private final int V; //顶点总数
    private int E;       //边总数
    private Bag<Edge>[] adj; //邻接表

    public EdgeWeightedGraph(int V) {
        this.V = V;
        this.E = 0;
        adj = new Bag[V];
        for (int i = 0; i < V; i++)
            adj[i] = new Bag<>();
    }

    public EdgeWeightedGraph(In in) {
        this.V = in.readInt();
        this.E = in.readInt();
        adj = new Bag[V];
        for (int i = 0; i < V; i++)
            adj[i] = new Bag<>();

        while(!in.isEmpty()) {
            int v = in.readInt();
            int w = in.readInt();
            double weight = in.readDouble();
            Edge e = new Edge(v, w, weight);
            this.addEdge(e);
        }

    }

    public int V() { return V; }
    public int E() { return E; }

    public void addEdge(Edge e) {
        int v = e.either(), w = e.other(v);
        adj[v].add(e);
        adj[w].add(e);
        E++;
    }

    public Iterable<Edge> adj(int v) {
        return adj[v];
    }

    public Iterable<Edge> edges() {
        Bag<Edge> b = new Bag<>();
        for (int v = 0; v < V; v++)
            for (Edge e : adj[v])
                if(e.other(v) > v)
                    b.add(e);
        return b;
    }

}
