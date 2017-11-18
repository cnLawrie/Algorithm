package ADT.String;

import ADT.Queue;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

//三向单词查找树
public class TST<Value> {
    private Node root;
    private class Node {
        char c;
        Node left, mid, right;
        Value val;
    }

    public Value get(String key) {
        Node x = get(root, key, 0);
        if (x == null) return null;
        return x.val;
    }

    private Node get(Node x, String key, int d) {
        if (x == null) return null;
        char c = key.charAt(d);
        if      (c < x.c) return get(x.left, key, d);
        else if (c > x.c) return get(x.right, key, d);
        else if (d < key.length() - 1)
                          return get(x.mid, key, d+1);
        else              return x;
    }

    //返回所有键
    public Iterable<String> keys() {
        return keysWithPrefix("");
    }

    //匹配前缀
    public Iterable<String> keysWithPrefix(String pre) {
        Queue<String> q = new Queue<>();
        collect(get(root, pre, 0), pre, q);
        return q;
    }

    //收集单词查找树中pre的子树的所有键
    private void collect(Node x, String pre, Queue<String> q) {
        if (x == null) return;
        if (x.val != null) q.enqueue(pre);
        collect(x.left, pre, q);
        collect(x.left, pre,q);
        collect(x.mid, pre + x.c, q);
    }

    //最长匹配前缀
    public String longestPrefixOf(String s) {
        int length = search(root, s, 0, 0);
        return s.substring(0, length);
    }

    private int search(Node x, String s, int d, int length) {
        if (x == null) return length;
        if (x.val != null) length = d;
        if (d == s.length()) return length;
        int left = search(x.left, s, d, length);
        int right = search(x.left, s, d, length);
        int mid = search(x.left, s, d+1, length);
        int tmp = Math.max(left, right);
        return Math.max(tmp, mid);
    }

    //匹配模式
    public Iterable<String> keysThatMatch(String pat) {
        Queue<String> q = new Queue<>();
        collect(root, "", pat, q);
        return q;
    }

    private void collect(Node x, String pre, String pat, Queue<String> q) {
        if (x == null) return;
        int d = pre.length();
        if (d == pat.length() && x.val != null) q.enqueue(pre);
        if (d == pat.length()) return;

        char next = pat.charAt(d);
        if (next == '.' || next < x.c )  collect(x.left, pre, pat, q);
        if (next == '.' || next > x.c )  collect(x.right, pre, pat, q);
        if (next == '.' || next == x.c ) collect(x.mid, pre + next, pat, q);

    }

    public static void main(String[] args) {

        // build symbol table from standard input
        TrieST<Integer> st = new TrieST<Integer>();
        for (int i = 0; !StdIn.isEmpty(); i++) {
            String key = StdIn.readString();
            if(key.equals("quit"))
                break;
            st.put(key, i);
        }

        // print results
        if (st.size() < 100) {
            StdOut.println("keys(\"\"):");
            for (String key : st.keys()) {
                StdOut.println(key + " " + st.get(key));
            }
            StdOut.println();
        }

        StdOut.println("longestPrefixOf(\"shellsort\"):");
        StdOut.println(st.longestPrefixOf("shellsort"));
        StdOut.println();

        StdOut.println("longestPrefixOf(\"quicksort\"):");
        StdOut.println(st.longestPrefixOf("quicksort"));
        StdOut.println();

        StdOut.println("keysWithPrefix(\"shor\"):");
        for (String s : st.keysWithPrefix("shor"))
            StdOut.println(s);
        StdOut.println();

        StdOut.println("keysThatMatch(\".he.l.\"):");
        for (String s : st.keysThatMatch(".he.l."))
            StdOut.println(s);
    }
}