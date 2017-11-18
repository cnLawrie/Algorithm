package ADT.String;


import edu.princeton.cs.algs4.Alphabet;

//高位优先的字符串排序
public class MSD {
    private static int R = 256;     //基数
    private static final int M = 15;//小数组的切分阈值
    private static String[] aux;    //数据分类的辅助数组

    private static int charAt(String s, int d) {
        return d < s.length() ? s.charAt(d) : -1;
    }

    public static void sort(String[] a) {
        int N = a.length;
        aux = new String[N];
        sort(a, 0, N-1, 0);
    }

    //以第d个字符为键将a[lo]至a[hi]排序
    public static void sort(String[] a, int lo, int hi, int d) {
        if(hi < lo + M) insertion(a, lo, hi ,d);

        int[] count = new int[R+2];
        for (int i = lo; i <= hi; i++)      //计算频率
            count[charAt(a[i], d) + 2]++;

        for (int r = 0; r < R+1; r++)       //频率转化为索引
            count[r+1] += count[r];

        for (int i = lo; i <= hi; i++)      //数据分类
            aux[count[charAt(a[i], d) + 1]++] = a[i];

        for (int i = lo; i <= hi; i++)      //回写
            a[i] = aux[i - lo];

        for (int r = 0; r < R; r++)         //递归地以每个字符为键进行排序
            sort(a, lo + count[r], lo + count[r+1] - 1, d+1);

    }

    public static void insertion(String[] a, int lo, int hi, int d) {
        for (int i = lo; i < hi; i++)
            for (int j = i; j > lo && less(a[j], a[j-1], d); j--)
                exch(a, j, j-1);
    }

    private static void exch(String[] a, int i, int j){
        String temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    private static boolean less(String v, String w, int d) {
        return v.substring(d).compareTo(w.substring(d)) < 0;
    }

    public static void main(String[] args) {
        String stu1 = new String("cac");
        String stu2 = new String("fnc");
        String stu3 = new String("edb");
        String stu4 = new String("oel");
        String stu5 = new String("akf");
        String stu6 = new String("erk");

        String[] stus = { stu1, stu2, stu3, stu4, stu5, stu6 };
        edu.princeton.cs.algs4.MSD.sort(stus);

        for (int i = 0; i < stus.length; i++) {
            System.out.println(stus[i].toString());
        }
    }
}
