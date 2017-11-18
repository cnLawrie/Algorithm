package ADT.Graph.Directed;

import ADT.Queue;
import ADT.Stack;
//深度优先的顶点排序
public class DepthFirstOrder {
    private boolean[] marked;
    private Queue<Integer> pre;
    private Queue<Integer> post;
    private Stack<Integer> reversePost;

    public DepthFirstOrder(Digraph G){
        pre         = new Queue<>();
        post        = new Queue<>();
        reversePost = new Stack<>();
        marked      = new boolean[G.V()];

        for (int i = 0; i < G.V(); i++)
            if(!marked[i])
                dfs(G, i);

    }

    private void dfs(Digraph G, int v){
        pre.enqueue(v);
        marked[v] = true;
        for (int w: G.adj(v))
            if(!marked[w])
                dfs(G, w);
        post.enqueue(v);
        reversePost.push(v);
    }

    public Iterable<Integer> pre(){
        return pre;
    }

    public Iterable<Integer> post(){
        return post;
    }

    public Iterable<Integer> reversePost(){
        return reversePost;
    }

    //Topo logical
    public static void main(String[] args) {
        String filename = "Graph/jobs.txt";
        String separator = "/";
        SymbolDigraph sg = new SymbolDigraph(filename, separator);

        DirectedCycle cyclefinder = new DirectedCycle(sg.G());
        //拓扑排序一定是DAG,Directed Acyclic Graph即有向无环图,拓扑排序有严格的偏序关系
        if(!cyclefinder.hasCycle()){
            Iterable<Integer> order;
            DepthFirstOrder dfo = new DepthFirstOrder(sg.G());
            order = dfo.reversePost;
            for (int v: order){
                System.out.println(sg.name(v));;
            }
        }

    }
}
