package ADT.Graph.EdgeWeighted;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;

public class LazyPrimMST {
    private boolean[] marked;
    private Queue<Edge> mst;
    private MinPQ<Edge> pq;

    public LazyPrimMST(EdgeWeightedGraph G) {
        pq = new MinPQ<Edge>();
        marked = new boolean[G.V()];
        mst = new Queue<>();

        visit(G, 0);
        while(!pq.isEmpty()) {
            Edge e = pq.delMin();
            int v = e.either(), w = e.other(v);
            if(marked[v] && marked[w]) continue;
            mst.enqueue(e);
            if(!marked[v]) visit(G, v);
            if(!marked[w]) visit(G, w);
        }
    }

    private void visit(EdgeWeightedGraph G, int v) {
        marked[v] = true;
        for (Edge e : G.adj(v))
            if(!marked[e.other(v)])
                pq.insert(e);
    }

    public Iterable<Edge> edges() {
        return mst;
    }

    public double weight() {
        double t = 0;
        for (Edge e : edges())
            t += e.weight();
        return t;
    }

    public static void main(String[] args) {
        In in = new In("data/tinyEWG.txt");
        EdgeWeightedGraph G = new EdgeWeightedGraph(in);
        LazyPrimMST mst = new LazyPrimMST(G);
        for (Edge e : mst.edges())
            System.out.println(e);
        System.out.println(mst.weight());
    }

}
