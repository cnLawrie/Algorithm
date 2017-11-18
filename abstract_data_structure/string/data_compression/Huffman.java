package dataCompression;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.MinPQ;
import org.jetbrains.annotations.NotNull;

//霍夫曼是一个两轮算法，第一次得到频率，第二次才能压缩它
public class Huffman {
    private static int R = 256;

    private static class Node implements Comparable<Node> {
        private char ch;  //内部结点不使用
        private int freq; //展开过程不使用
        private final Node left, right;

        private Node(char ch, int freq, Node left, Node right) {
            this.ch = ch;
            this.freq = freq;
            this.left = left;
            this.right = right;
        }

        public boolean isLeaf() {
            return left == null && right == null;
        }

        @Override
        public int compareTo(@NotNull Huffman.Node o) {
            return this.freq - o.freq;
        }
    }

    //压缩需要7步
    public static void compress() {
        //1.读取输入
        String s = BinaryStdIn.readString();
        char[] input = s.toCharArray();

        //2.统计频率
        int[] freq = new int[R];
        for (int i = 0; i < input.length; i++)
            freq[input[i]]++;

        //3.构造编码树
        Node root = buildTrie(freq);

        //4.构造编译表
        String[] st = buildCode(root);

        //5.写入解码用的单词查找树
        writeTrie(root);

        //6.打印字符总数
        BinaryStdOut.write(input.length);

        //7.使用编码树处理输入
        for (int i = 0; i < input.length; i++) {
            String code = st[input[i]];
            for (int j = 0; j < code.length(); j++)
                if (code.charAt(j) == '1') BinaryStdOut.write(true);
                else                       BinaryStdOut.write(false);
        }

        BinaryStdOut.close();
    }

    //解码：前缀码的展开
    private static void expand() {
        Node root = readTrie();
        int N = BinaryStdIn.readInt(); //编码数
        for (int i = 0; i < N; i++) {  //第i个编码对应的字符
            Node x = root;
            while (!x.isLeaf())
                if (BinaryStdIn.readBoolean()) x = x.right;
                else                           x = x.left;
            BinaryStdOut.write(x.ch);
        }
        BinaryStdOut.close();
    }

    //构造哈夫曼编码树
    private static Node buildTrie(int[] freq) {
        MinPQ<Node> pq = new MinPQ<>();
        for (char c = 0; c < R; c++)
            if (freq[c] > 0)
                pq.insert(new Node(c , freq[c], null, null));

        while (pq.size() > 1) {
            Node x = pq.delMin();
            Node y = pq.delMin();
            pq.insert(new Node('\0', x.freq + y.freq, x, y)); //合并成一个父节点
        }

        return pq.delMin();
    }

    //使用编码树构造编译表:将字符与比特字符串相关联
    private static String[] buildCode(Node root) {
        String[] st = new String[R];
        buildCode(st, root, "");
        return st;
    }

    private static void buildCode(String[] st, Node x, String s) {
        if (x.isLeaf()) {
            st[x.ch] = s;
            return;
        }
        buildCode(st, x.left, s + '0');
        buildCode(st, x.right, s + '1');
    }

    //写入单词查找树:压缩时将树写入比特流并在展开时读取它
    private static void writeTrie(Node x) {
        if (x.isLeaf()) {
            BinaryStdOut.write(true);
            BinaryStdOut.write(x.ch);
            return;
        }
        BinaryStdOut.write(false);
        writeTrie(x.left);
        writeTrie(x.right);
    }

    //读取单词查找树
    private static Node readTrie() {
        if (BinaryStdIn.readBoolean())
            return new Node(BinaryStdIn.readChar(), 0, null, null);
        return new Node('\0', 0, null, null);
    }



}
