package ADT.Graph;

import ADT.Bag;

public class Graph {
    private final int V;        //顶点数
    private int E;              //边数
    private Bag<Integer>[] adj; //邻接表
    public Graph(int V){
        this.V = V;
        this.E = 0;
        adj = new Bag[V];
        for (int i = 0; i < V; i++) {
            adj[i] = new Bag<>();
        }
    }

    public Graph(In in){
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
        adj[w].add(v);
        E++;
    }

    public Iterable<Integer> adj(int v){
        return adj[v];
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
