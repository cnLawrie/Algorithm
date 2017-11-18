package ADT.Graph;

import ADT.Queue;
import ADT.Stack;
import edu.princeton.cs.algs4.In;

public class BreadthFirstPaths {

    private boolean[] marked;
    private int[] edgeTo;
    private final int s;

    public BreadthFirstPaths(Graph G, int s){
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        this.s = s;
        bfs(G, s);
    }

    private void bfs(Graph G, int s) {
        Queue<Integer> queue = new Queue<>();
        marked[s] = true;
        queue.enqueue(s);
        while(!queue.isEmpty()) {
            int v = queue.dequeue();
            for (int w : G.adj(v))
                if (!marked[w]) {
                    edgeTo[w] = v;
                    marked[w] = true;
                    queue.enqueue(w);
                }
        }
    }

    public boolean hasPathTo(int v){
        return marked[v];
    }

    public Iterable<Integer> pathTo(int v){
        if(!hasPathTo(v))
            return null;
        Stack<Integer> path = new Stack<>();
        for (int x = v; x != s; x = edgeTo[x])
            path.push(x);
        path.push(s);
        return path;
    }

    public static void main(String[] args) {
        Graph G = new Graph(new In("Graph/tinyG.txt"));
        int s = 0;
        BreadthFirstPaths paths = new BreadthFirstPaths(G, s);

        for (int v = 0; v < G.V(); v++){
            System.out.print(s + " to " + v + " : ");
            if(paths.hasPathTo(v))
                for (int x : paths.pathTo(v))
                    if(x == s) System.out.print(s);
                    else System.out.print("-" + x);
            System.out.println();
        }
        for (int i = 0; i < paths.edgeTo.length; i++) {
            System.out.print(paths.edgeTo[i] + " ");
        }
    }
}
