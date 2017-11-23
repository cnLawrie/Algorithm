package ADT.Graph.EdgeWeighted;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.IndexMinPQ;
import edu.princeton.cs.algs4.Queue;

public class PrimMST {
    private Edge[] edgeTo;
    private double[] distTo;
    private boolean[] marked;
    private IndexMinPQ<Double> pq;

    public PrimMST(EdgeWeightedGraph G) {
        edgeTo = new Edge[G.V()];
        distTo = new double[G.V()];
        marked = new boolean[G.V()];
        for (int v = 0; v < G.V(); v++)
            distTo[v] = Double.POSITIVE_INFINITY;
        pq = new IndexMinPQ<>(G.V());

        distTo[0] = 0;
        pq.insert(0, 0.0);
        while(!pq.isEmpty())
            visit(G, pq.delMin());
    }

    private void visit(EdgeWeightedGraph G, int v) {
        marked[v] = true;
        for (Edge e : G.adj(v)) {
            int w = e.other(v);
            if (marked[w])
                continue;
            if (e.weight() < distTo[w]) {
                edgeTo[w]  = e;
                distTo[w]  = e.weight();
                if(pq.contains(w))
                    pq.change(w, distTo[w]);
                else
                    pq.insert(w, distTo[w]);
            }
        }
    }

    public Iterable<Edge> edges() {
        Queue<Edge> mst = new Queue<>();
        for (int v = 0; v < edgeTo.length; v++) {
            Edge e = edgeTo[v];
            if (e != null) {
                mst.enqueue(e);
            }
        }
        return mst;
    }

    public static void main(String[] args) {
        In in = new In("data/mediumEWG.txt");
        EdgeWeightedGraph G = new EdgeWeightedGraph(in);
        PrimMST mst = new PrimMST(G);
        for (Edge e : mst.edges())
            System.out.println(e);
    }
}

