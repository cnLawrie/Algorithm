package ADT.Graph.Directed;

import ADT.Bag;
import ADT.Graph.Graph;
import edu.princeton.cs.algs4.In;

//判断从给定的一个或一组顶点能到达哪些其他的顶点
public class DirectedDFS {
    private boolean[] marked;

    public DirectedDFS(Digraph G, int s){
        marked = new boolean[G.V()];
        dfs(G, s);
    }

    public DirectedDFS(Digraph G, Iterable<Integer> sources){
        marked = new boolean[G.V()];
        for (int s: sources)
            if(!marked[s])
                dfs(G, s);
    }

    public void dfs(Digraph G, int s){
        marked[s] = true;
        for (int w: G.adj(s))
            if(!marked[w])
                dfs(G, w);
    }

    public boolean marked(int v){
        return marked[v];
    }

    public static void main(String[] args) {
        Digraph G = new Digraph(new In("Graph/tinyDG.txt"));

        Bag<Integer> sources = new Bag<>();
        sources.add(1);
        sources.add(2);
        sources.add(6);

        DirectedDFS reachable = new DirectedDFS(G, sources);
        for (int i = 0; i < G.V(); i++)
            if(reachable.marked(i))
                System.out.print(i + " ");
            System.out.println();

    }
}
