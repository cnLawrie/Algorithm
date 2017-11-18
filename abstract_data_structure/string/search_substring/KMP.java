package ADT.String.searchSub;

public class KMP {
    private String pat;
    private int[][] dfa;
    public KMP(String pat) {
        this.pat = pat;
        int M = pat.length();
        int R = 256;
        dfa = new int[R][M];
        dfa[pat.charAt(0)][0] = 1;
        for (int x = 0, j = 1; j < M; j++) {
            for (int c = 0; c < R; c++)
                dfa[c][j] = dfa[c][x];     //复制匹配失败下的值
            dfa[pat.charAt(j)][j] = j + 1; //设置匹配成功下的值
            x = dfa[pat.charAt(j)][x];     //更新重启状态的起点
        }
    }

    public int search(String txt) {
        int i, j, N = txt.length(), M = pat.length();
        for (i = 0, j = 0; i < N && j < M; i++)
            j = dfa[txt.charAt(i)][j];
        if (j == M) return i - M;
        else        return N;
    }

    public static void main(String[] args) {
        String pat = "AACAA";
        String txt = "AABRAACADABRAACAADABRA";
        KMP kmp = new KMP(pat);
        System.out.println("text:    " + txt);
        int offset = kmp.search(txt);
        System.out.print("pattern: ");
        for (int i = 0; i < offset; i++) {
            System.out.print(" ");
        }
        System.out.println(pat);
    }

}
