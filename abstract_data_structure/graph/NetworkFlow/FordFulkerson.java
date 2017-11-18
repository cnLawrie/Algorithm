package ADT.Graph.NetworkFlow;

import ADT.Queue;
import edu.princeton.cs.algs4.In;

//算法会在剩余网络中寻找最短增广路径，找出路径上的最小残量并增大，如此往复直到不存在增广路径
//最短增广路径的最大流量算法
public class FordFulkerson {
    private boolean[] marked;
    private FlowEdge[] edgeTo;
    private double value;

    public FordFulkerson(FlowNetwork G, int s, int t) { //找到从s到t的流量网络G的最大配置
        while (hasAugmentingPath(G, s, t)) {            //利用所有增广路径
            double bottle = Double.POSITIVE_INFINITY;
            //寻找最小残量
            for (int v = t; v != s; v = edgeTo[v].other(v))
                bottle = Math.min(bottle, edgeTo[v].residualCapacityTo(v));
            //增大流量
            for (int v = t; v != s; v = edgeTo[v].other(v))
                edgeTo[v].addResidualFlowTo(v, bottle);
            value += bottle;
        }
    }

    //bfs寻找增广路径
    private boolean hasAugmentingPath(FlowNetwork G, int s, int t) {
        marked = new boolean[G.V()];
        edgeTo = new FlowEdge[G.E()];
        Queue<Integer> q = new Queue<>();

        marked[s] = true;
        q.enqueue(s);
        while(!q.isEmpty()) {
            int v = q.dequeue();
            for (FlowEdge e : G.adj(v)) {
                int w = e.other(v);
                if (e.residualCapacityTo(w) > 0 && !marked[w]) { //对于连接到未标记顶点的任意边
                    edgeTo[w] = e;    //保存路径上的最后一条边
                    marked[w] = true; //标记w，并入列
                    q.enqueue(w);
                }
            }
        }
        return marked[t];
    }

    public double value() {
        return value;
    }

    public boolean inCut(int v) {
        return marked[v];
    }

    public static void main(String[] args) {
        In in = new In("data/tinyFN.txt");
        FlowNetwork G = new FlowNetwork(in);
        int s = 0, t = G.V() - 1;
        FordFulkerson maxflow = new FordFulkerson(G, s, t);

        System.out.println("Max flow from " + s + " to " + t);
        for (int v = 0; v < G.V(); v++)
            for (FlowEdge e : G.adj(v))
                if (v == e.from() && e.flow() > 0)
                    System.out.println(" " + e);
        System.out.println("Max flow value = " + maxflow.value());
    }
}
