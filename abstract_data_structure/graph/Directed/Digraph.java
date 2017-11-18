package ADT.Graph.Directed;

import ADT.Bag;
import edu.princeton.cs.algs4.In;

public class Digraph {
    private final int V;        //顶点数
    private int E;              //边数
    private Bag<Integer>[] adj; //邻接表
    public Digraph(int V){
        this.V = V;
        this.E = 0;
        adj = new Bag[V];
        for (int i = 0; i < V; i++) {
            adj[i] = new Bag<>();
        }
    }

    public Digraph(In in){
        this(in.readInt());
        int E = in.readInt();
        for (int i = 0; i < E; i++) {
            int v = in.readInt();
            int w = in.readInt();
            addEdge(v, w);
        }
    }

    public int V() { return this.V; }

    public int E() { return this.E; }

    public void addEdge(int v, int w){
        adj[v].add(w);
        E++;
    }

    public Iterable<Integer> adj(int v){
        return adj[v];
    }

    public Digraph reverse(){
        Digraph R = new Digraph(V);
        for (int v = 0; v < V; v++)
            for (int w: adj(v))
                R.addEdge(w, v);
        return R;
    }

    public String toString(){
        String s = V + " vertices, " + E + " edges\n";
        for (int i = 0; i < V; i++) {
            s += i + ": ";
            for(int w: this.adj(i))
                s += w + " ";
            s += "\n";
        }
        return s;
    }
}
