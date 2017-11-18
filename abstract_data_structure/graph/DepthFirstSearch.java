package ADT.Graph;

import ADT.Stack;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class DepthFirstSearch {
    private boolean[] marked;
    private int count;

    public DepthFirstSearch(Graph G, int s){
        marked = new boolean[G.V()];
        dfs(G, s);
    }

    private void dfs(Graph G, int v){
        marked[v] = true;
        count++;
        for (int w: G.adj(v))
            if(!marked[w])
                dfs(G, w);
    }

    public boolean marked(int w){
        return marked[w];
    }

    public int count(){
        return count;
    }

    public static void main(String[] args) {
        Graph G = new Graph(new In("Graph/tinyG.txt"));
        int s = 0;
        DepthFirstSearch search = new DepthFirstSearch(G, s);

        for (int v = 0; v < G.V(); v++)
            if(search.marked(v))
                System.out.print(v + " ");
        System.out.println();

        if(search.count() != G.V())
            System.out.println("NOT ");
        System.out.println(search.count() + " connected");
    }
}
