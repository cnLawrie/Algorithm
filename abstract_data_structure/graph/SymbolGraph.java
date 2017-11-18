package ADT.Graph;


import ADT.ST.RedBlackBST;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SymbolGraph {
    private RedBlackBST<String, Integer> st;
    private String[] keys; //反向索引
    private Graph G;

    public SymbolGraph(String stream, String sp){
        st = new RedBlackBST<>();
        //第一遍：构建符号表
        In in = new In(stream);
        while(in.hasNextLine()){
            String[] a = in.readLine().split(sp);
            for (int i = 0; i < a.length; i++)
                if(!st.contains(a[i]))
                    st.put(a[i], st.size());
        }
        StdOut.println("Done reading " + stream);

        //构造反向索引
        keys = new String[st.size()];
        for (String name: st.keys())
            keys[st.get(name)] = name;

        //第二遍：构建图
        G = new Graph(st.size());
        in = new In(stream);
        while(in.hasNextLine()){
            String[] a = in.readLine().split(sp);
            int v = st.get(a[0]);
            for (int i = 1; i < a.length; i++) {
                G.addEdge(v, st.get(a[i]));
            }
        }
    }

    public boolean contains(String s) { return st.get(s) != null; }
    public int index(String s)        { return st.get(s); }
    public String name(int v)         { return keys[v]; }
    public Graph G()                  { return G; }

    public static void main(String[] args) {
        String filename = "Graph/movies.txt";
        String delim = "/";
        SymbolGraph sg = new SymbolGraph(filename, delim);

        Graph G = sg.G();

        //间隔的度数
        String source = "Animal House (1978)";
        int s = sg.index(source);
        BreadthFirstPaths bfs = new BreadthFirstPaths(G, s);

        //sample input:Titanic (1997)
        while(StdIn.hasNextLine()){
            source = StdIn.readLine();
            if(!sg.contains(source) || source.equals("quit"))
                break;

            int t = sg.index(source);
            if(bfs.hasPathTo(t))
                for (int v : bfs.pathTo(t))
                    System.out.println(sg.name(v));
//            for (int i: G.adj(sg.index(source)))
//                System.out.print(" " + sg.name(i));
        }



    }
}
