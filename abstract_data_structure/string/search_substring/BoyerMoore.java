package ADT.String.searchSub;

public class BoyerMoore {
    private int[] right;
    private String pat;

    public BoyerMoore(String pat) {
        this.pat = pat;
        int M = pat.length();
        int R = 256;
        right = new int[R];
        for (int i = 0; i < R; i++)
            right[i] = -1;
        for (int j = 0; j < M; j++)
            right[pat.charAt(j)] = j; //在模式串中出现的最右位置
    }

    public int search(String txt) {
        int N = txt.length();
        int M = pat.length();
        int skip;
        for (int i = 0; i <= N-M; i += skip) {
            skip = 0;
            for (int j = M-1; j >= 0; j--)
                if (pat.charAt(j) != txt.charAt(i+j)) {
                    skip = j - right[txt.charAt(i+j)]; //可以向右滑动的距离
                    if (skip < 1) skip = 1;
                    break;
                }
            if (skip == 0) return i; //找到匹配
        }
        return N;
    }

    public static void main(String[] args) {
        String pat = "AACAA";
        String txt = "AABRAACADABRAACAADABRA";
        BoyerMoore bm = new BoyerMoore(pat);
        System.out.println("text:    " + txt);
        int offset = bm.search(txt);
        System.out.print("pattern: ");
        for (int i = 0; i < offset; i++) {
            System.out.print(" ");
        }
        System.out.println(pat);
    }
}
