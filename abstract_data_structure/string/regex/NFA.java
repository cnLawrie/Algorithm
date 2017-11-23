package ADT.String;

import ADT.Bag;
import ADT.Graph.Directed.Digraph;
import ADT.Graph.Directed.DirectedDFS;
import ADT.Graph.EdgeWeightedDirected.DirectedEdge;
import ADT.Stack;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;

//RegExp matching by Nondeterministic Finite Automata 用于正则的非确定有限自动机
public class NFA {
    private char[] re; //匹配转换
    private Digraph G; //epsilon 转换(无匹配时的状态转换，可以转换到另一个状态而不扫描文本中的任何字符，所对应的“匹配”是一个空字符串)
    private int M;     //状态数量

    //构造对应的epsilon转换有向图
    public NFA(String regexp) {
        Stack<Integer> ops = new Stack<>();
        re = regexp.toCharArray();
        M = re.length;
        G = new Digraph(M + 1);

        for (int i = 0; i < M; i++) {
            int lp = i;                 //lp:left parenthesis 左括号
            if (re[i] == '(' || re[i] == '|')
                ops.push(i);
            else if (re[i] == ')') {    //假设现在栈里存放着(:0,|:1，或者(:0
                int or = ops.pop();     //or获得栈首元素
                if (re[or] == '|') {    //如果or是 | ,
                    lp = ops.pop();     //则再次pop获得 ( ,并将索引赋值给lp
                    G.addEdge(lp, or + 1);
                    G.addEdge(or, i);
                }
                else                    //如果or是 ( ，将索引赋值给lp
                    lp = or;
            }

            if (i < M-1 && re[i+1] == '*') { //查看下一个字符
                G.addEdge(lp, i+1);
                G.addEdge(i+1, lp);
            }

            if (re[i] == '(' || re[i] == '*' || re[i] == ')')
                G.addEdge(i, i+1);
        }
    }

    //正则能否识别txt
    public boolean recognized(String txt) {
        Bag<Integer> pc = new Bag<>();
        DirectedDFS dfs = new DirectedDFS(G, 0);
        for (int v = 0; v < G.V(); v++)
            if (dfs.marked(v))
                pc.add(v);

        for (int i = 0; i < txt.length(); i++) {
            Bag<Integer> match = new Bag<>();
            for (int v : pc)
                if (v < M)
                    if (re[v] == txt.charAt(i) || re[v] == '.')
                        match.add(v + 1);
            pc = new Bag<>();
            dfs = new DirectedDFS(G, match);
            for (int v = 0; v < G.V(); v++)
                if (dfs.marked(v))
                    pc.add(v);
        }

        for (int v : pc)
            if (v == M)
                return true;
        return false;
    }

    public static void main(String[] args) {
        String regexp = "(.*" + StdIn.readString() + ".*)";
        NFA nfa = new NFA(regexp);
        In in = new In("data/tinyTale.txt");
        while(!in.isEmpty()){
            String txt = in.readLine();
            if (nfa.recognized(txt))
                System.out.println(txt);
        }
    }
}
