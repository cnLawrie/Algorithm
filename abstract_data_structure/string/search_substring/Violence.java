package ADT.String.searchSub;

public class Violence {

    //暴力算法（显式回退）
    public static int search(String pat, String txt) {
        int i, N = txt.length();
        int j, M = pat.length();
        for (i = 0, j = 0; i < N && j < M; i++) {
            if(txt.charAt(i) == pat.charAt(j))
                j++;
            else {
                i -= j;
                j = 0;
            }
        }
        if(j == M)
            return i - j;
        return N;
    }

    public static void main(String[] args) {
        String pat = "AACAA";
        String txt = "AABRAACADABRAACAADABRA";
        Violence violence = new Violence();
        System.out.println("text:    " + txt);
        int offset = violence.search(pat, txt);
        System.out.print("pattern: ");
        for (int i = 0; i < offset; i++) {
            System.out.print(" ");
        }
        System.out.println(pat);
    }
}
