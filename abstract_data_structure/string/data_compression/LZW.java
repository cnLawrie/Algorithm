package dataCompression;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.TST;

public class LZW {
    private static final int R = 256;  //输入字符数
    private static final int L = 4096; //编码总数
    private static final int W = 12;   //编码宽度

    //接受8位字节流输入，并产生12位编码的输出流
    public static void compress() {
        String input = BinaryStdIn.readString();
        TST<Integer> st = new TST<>();

        for (int i = 0; i < R; i++)
            st.put("" + (char)i, i);
        int code = R + 1; //R为文件结束编码

        while (input.length() > 0) {
            String s = st.longestPrefixOf(input);
            BinaryStdOut.write(st.get(s), W);

            int t = s.length();
            if (t < input.length() && code < L)
                st.put(input.substring(0, t + 1), code++);
            input = input.substring(t);
        }

        BinaryStdOut.write(R, W);
        BinaryStdOut.close();
    }

    public static void expand() {
        String[] st = new String[L];

        int i;              //下一个待补全的编码值

        for (i = 0; i < R; i++)
            st[i] = "" + (char)i;
        st[i++] = " ";      //文件结束标记的前瞻字符（未使用）

        int codeword = BinaryStdIn.readInt();
        String val = st[codeword];
        while (true) {
            BinaryStdOut.write(val);                        //输出当前字符串
            codeword = BinaryStdIn.readInt();
            if (codeword == R) break;                       //遇到文件结束标记
            String s = st[codeword];                        //获得下一个编码
            if (i == codeword) s = val + val.charAt(0);
            else if (i < L)    st[i++] = val + s.charAt(0); //为编译表添加新条目
            val = s;
        }
        BinaryStdOut.close();
    }
}
