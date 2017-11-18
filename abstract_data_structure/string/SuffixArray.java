package ADT.String;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.SuffixArrayX;
import sort.Quick3way;

//实现效率取决于java的String的不可变性，提取自字符串只需要常数时间
public class SuffixArray {
    private final String[] suffixes;
    private final int N;

    public SuffixArray(String s) {
        N = s.length();
        suffixes = new String[N];
        for (int i = 0; i < N; i++)
            suffixes[i] = s.substring(i);
        Quick3way.sort(suffixes);
    }

    private int lcp(String s, String t) {
        int N = Math.min(s.length(), t.length());
        for (int i = 0; i < N; i++)
            if(s.charAt(i) != t.charAt(i))
                return i;
        return N;
    }

    private int lcp(int i) {
        return lcp(suffixes[i], suffixes[i-1]);
    }

    public int length() {
        return N;
    }

    public String select(int i) {
        return suffixes[i];
    }

    public int index(int i) {
        return N - suffixes[i].length();
    }

    public int rank(String key) {
        int lo = 0, hi = N - 1;
        while(lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            int cmp = key.compareTo(suffixes[mid]);
            if (cmp > 0) lo = mid + 1;
            else if (cmp < 0) hi = mid - 1;
            else return mid;
        }
        return lo;
    }

    public static void main(String[] args) {
        //最长重复子字符串用例
        In in = new In("data/tinyTale.txt");
        String text = in.readAll();
        int N = text.length();
        SuffixArray sa = new SuffixArray(text);
        String lrs = "";
        for (int i = 1; i < N; i++) {
            int length = sa.lcp(i);
            if (length > lrs.length())
                lrs = sa.select(i).substring(0, length);
        }
        System.out.println(lrs);

        //查找关键词并打印出其上下文,KWIC(keyword-in-context)
        int context = 15;
        text = text.replaceAll("\\s+", " "); //多个空格替换成一个空格

        while (StdIn.hasNextLine()) {
            String q = StdIn.readLine();
            for (int i = sa.rank(q); i < N && sa.select(i).startsWith(q); i++) {
                int from = Math.max(0, sa.index(i) - context);
                int to = Math.min(N - 1, from + q.length() + 2*context);
                System.out.println(text.substring(from, to));
            }

        }
    }
}
