package ADT.Graph.Directed;

import ADT.ST.RedBlackBST;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class SymbolDigraph {
    private RedBlackBST<String, Integer> st;
    private String[] keys; //反向索引
    private Digraph G;

    public SymbolDigraph(String stream, String sp){
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
        G = new Digraph(st.size());
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
    public Digraph G()                  { return G; }

}
