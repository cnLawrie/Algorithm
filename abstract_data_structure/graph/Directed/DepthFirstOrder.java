package ADT.Graph.Directed;

import ADT.Graph.EdgeWeightedDirected.DirectedEdge;
import ADT.Graph.EdgeWeightedDirected.EdgeWeightedDigraph;
import ADT.Queue;
import ADT.Stack;
//深度优先的顶点排序
public class DepthFirstOrder {
    private boolean[] marked;
    private int[] pre;
    private int[] post;                // post[v]   = postorder number of v
    private Queue<Integer> preorder;
    private Queue<Integer> postorder;
    private Stack<Integer> reversePost;
    private int preCounter;
    private int postCounter;

    public DepthFirstOrder(Digraph G){
        pre    = new int[G.V()];
        post   = new int[G.V()];
        reversePost = new Stack<>();
        marked      = new boolean[G.V()];

        for (int i = 0; i < G.V(); i++)
            if(!marked[i])
                dfs(G, i);
    }

    public DepthFirstOrder(EdgeWeightedDigraph G) {
        pre    = new int[G.V()];
        post   = new int[G.V()];
        postorder = new Queue<Integer>();
        preorder  = new Queue<Integer>();
        marked    = new boolean[G.V()];
        for (int v = 0; v < G.V(); v++)
            if (!marked[v]) dfs(G, v);
    }
    private void dfs(Digraph G, int v) {
        marked[v] = true;
        pre[v] = preCounter++;
        preorder.enqueue(v);
        for (int w : G.adj(v)) {
            if (!marked[w]) {
                dfs(G, w);
            }
        }
        postorder.enqueue(v);
        post[v] = postCounter++;
    }


    private void dfs(EdgeWeightedDigraph G, int v) {
        marked[v] = true;
        pre[v] = preCounter++;
        preorder.enqueue(v);
        for (DirectedEdge e : G.adj(v)) {
            int w = e.to();
            if (!marked[w]) {
                dfs(G, w);
            }
        }
        postorder.enqueue(v);
        post[v] = postCounter++;
    }

    public Iterable<Integer> pre(){
        return preorder;
    }

    public Iterable<Integer> post(){
        return postorder;
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
