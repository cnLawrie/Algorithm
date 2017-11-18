package ADT.String.searchSub;

//Rabin-Karp指纹字符串查找算法
public class RabinKarp {
    private String pat;   //模式字符串
    private long patHash; //模式字符串的散列值
    private int M;        //模式字符串的长度
    private long Q;       //一个很大的素数
    private int R = 256;  //字母表大小
    private long RM;      //R^(m-1) % Q

    public RabinKarp(String pat) {
        this.pat = pat;
        this.M   = pat.length();
        Q = 997;
        RM = 1;
        for (int i = 1; i <= M-1; i++)
            RM = (R * RM) % Q;
        patHash = hash(pat, M);
    }

    private long hash(String key, int M) {
        long h = 0;
        for (int i = 0; i < M; i++)
            h = (h * R + key.charAt(i)) % Q;
        return h;
    }

    private int search(String txt) {
        int N = txt.length();
        long txtHash = hash(txt , M);
        if (patHash == txtHash) return 0;
        for (int i = M; i < N; i++) {
            txtHash = (txtHash + Q - RM * txt.charAt(i-M) % Q) % Q; //减去上一次的第一个字母的计算
            txtHash = (txtHash * R + txt.charAt(i)) % Q; //加上新字母的计算
            if(patHash == txtHash)
                return i - M + 1;
        }
        return N;
    }

    public static void main(String[] args) {
        String pat = "AACAA";
        String txt = "AABRAACADABRAACAADABRA";
        RabinKarp kmp = new RabinKarp(pat);
        System.out.println("text:    " + txt);
        int offset = kmp.search(txt);
        System.out.print("pattern: ");
        for (int i = 0; i < offset; i++) {
            System.out.print(" ");
        }
        System.out.println(pat);
    }
}
