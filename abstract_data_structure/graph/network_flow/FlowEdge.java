package ADT.Graph.NetworkFlow;

import edu.princeton.cs.algs4.FlowNetwork;

public class FlowEdge {
    private final int v;            //边的起点
    private final int w;            //边的终点
    private final double capacity;  //容量
    private double flow;            //流量

    public FlowEdge(int v, int w, double capacity) {
        this.v = v;
        this.w = w;
        this.capacity = capacity;
        this.flow = 0;
    }

    public int from()        { return v; }
    public int to()          { return w; }
    public double capacity() { return capacity; }
    public double flow()     { return flow; }

    public int other(int vertex) {
        if (vertex == v)     return w;
        else if(vertex == w) return v;
        else throw new RuntimeException("inconsistent edge");
    }

    public double residualCapacityTo(int vertex) {
        if (vertex == v)     return flow;
        else if(vertex == w) return capacity - flow;
        else throw new RuntimeException("inconsistent edge");
    }


    public void addResidualFlowTo(int vertex, double delta) {
        if (vertex ==  v)    flow -= delta;
        else if(vertex == w) flow += delta;
    }

    public String toString() {
        return String.format("%d->%d %.2f %.2f", v, w, capacity, flow);
    }
}
