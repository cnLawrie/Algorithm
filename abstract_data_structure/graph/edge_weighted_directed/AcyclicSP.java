package ADT.Graph.ShortestPath;


import ADT.Graph.Directed.Topological;
import ADT.Graph.EdgeWeightedDirected.DirectedEdge;
import ADT.Graph.EdgeWeightedDirected.EdgeWeightedDigraph;
import ADT.Stack;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

//如果加权有向图中无环，可以利用拓扑排序得到更快的算法,无环能极大简化问题的论断
//线性时间解决单点最短路径问题，能处理负权重的边
public class AcyclicSP {
    private DirectedEdge[] edgeTo;
    private double[] distTo;

    public AcyclicSP(EdgeWeightedDigraph G, int s) {
        edgeTo = new DirectedEdge[G.V()];
        distTo = new double[G.V()];

        for (int v = 0; v < G.V(); v++)
            distTo[v] = Double.POSITIVE_INFINITY;
        distTo[s] = 0;

        Topological top = new Topological(G);
        for (int v : top.order())
            relax(G, v);
    }

    private void relax(DirectedEdge e) {
        int v = e.from(), w = e.to();
        if(distTo[v] + e.weight() < distTo[w]) {
            distTo[w] = distTo[v] + e.weight();
            edgeTo[w] = e;
        }
    }

    private void relax(EdgeWeightedDigraph G, int v) {
        for (DirectedEdge e : G.adj(v))
            relax(e);
    }

    public double distTo(int v) {
        return distTo[v];
    }

    public boolean hasPathTo(int v) {
        return distTo[v] != Double.POSITIVE_INFINITY;
    }

    public Iterable<DirectedEdge> pathTo(int v) {
        if (!hasPathTo(v)) return null;
        Stack<DirectedEdge> path = new Stack<>();
        for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()])
            path.push(e);
        return path;
    }


    public static void main(String[] args) {
        In in = new In("data/tinyEWDAG.txt");
        int s = 5;
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(in);

        // find shortest path from s to each other vertex in DAG
        AcyclicSP sp = new AcyclicSP(G, s);
        for (int v = 0; v < G.V(); v++) {
            if (sp.hasPathTo(v)) {
                StdOut.printf("%d to %d (%.2f)  ", s, v, sp.distTo(v));
                for (DirectedEdge e : sp.pathTo(v)) {
                    StdOut.print(e + "   ");
                }
                StdOut.println();
            }
            else {
                StdOut.printf("%d to %d         no path\n", s, v);
            }
        }

        //最长路径问题
        //要处理无环有向加权图的最长路径问题，即给定一副图和一个起点，是否存在
        //起点到顶点v的路径，如果有就求最长路径，这个问题只要将原图权值取相反数
        //利用原图的最短路径算法，即可得到最长路径

    }
}
