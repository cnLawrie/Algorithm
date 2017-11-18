package ADT.Graph;

import edu.princeton.cs.algs4.In;

public class DepthFirstCycle {
    //判断是否为无环图
        private boolean[] marked;
        private boolean hasCycle = false;

        public DepthFirstCycle(Graph G) {
            marked = new boolean[G.V()];
            for (int s = 0; s <G.V() ; s++)
                if(!marked[s])
                    dfs(G, s, s);
        }

        private void dfs(Graph G, int v, int u){
            marked[v] = true;
            for(int w: G.adj(v)){
                if(!marked[w])
                    dfs(G, w, v);
                else if(w != u) hasCycle = true;
            }
        }

        public boolean hasCycle(){
            return hasCycle;
        }

        public static void main(String[] args) {
            Graph G = new Graph(new In("data/tinyG.txt"));
            int s = 0;
            DepthFirstCycle paths = new DepthFirstCycle(G);
            System.out.println(paths.hasCycle());
        }
}
