package ADT.Graph.Directed;

import ADT.Bag;
import edu.princeton.cs.algs4.In;
//strongly conntected component
public class KosarajuSCC {
    private boolean[] marked;
    private int[] id;
    private int count;

    public KosarajuSCC(Digraph G){
       marked = new boolean[G.V()];
       id = new int[G.V()];
       //下面两行就是与无向图ConnectedComponent的主要区别
       DepthFirstOrder order = new DepthFirstOrder(G.reverse());
       for (int s : order.reversePost())
           if(!marked[s]){
               dfs(G, s);
               count++;
           }

   }

    public void dfs(Digraph G, int v){
        marked[v] = true;
        id[v] = count;
        for (int w: G.adj(v))
            if(!marked[w])
                dfs(G, w);
    }

    public boolean connected(int v, int w){
        return id(v) == id(w);
    }

    public boolean stronglyConnected(int v, int w){
        return id[v] == id[w];
    }

    public int id(int v){
        return id[v];
    }

    public int count(){ return count; }

    public static void main(String[] args) {
        Digraph G = new Digraph(new In("Graph/tinyDG.txt"));
        KosarajuSCC cc = new KosarajuSCC(G);

        int M = cc.count();
        System.out.println(M + " components");

        Bag<Integer>[] components = new Bag[M];
        for (int i = 0; i < M; i++) {
            components[i] = new Bag<>();
        }
        for (int v = 0; v < G.V(); v++) {
            components[cc.id(v)].add(v);
        }

        for (int i = 0; i < M; i++) {
            for(int v: components[i])
                System.out.print(v + " ");
            System.out.println();
        }
    }
}
