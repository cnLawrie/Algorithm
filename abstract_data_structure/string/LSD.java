package ADT.String;

//低位优先的字符串排序
public class LSD {
    public static void sort(String[] a, int W) {
        int N = a.length;
        int R = 256;

        String[] aux = new String[N];
        //根据第d个字符用建索引计数法排序
        for (int d = W - 1; d >= 0; d--) {
            int[] count = new int[R + 1];
            for (int i = 0; i < N; i++) {
                count[a[i].charAt(d) + 1]++;
            }

            for (int r = 0; r < R; r++) {
                count[r + 1] += count[r];
            }

            for (int i = 0; i < N; i++) {
                aux[count[a[i].charAt(d)]++] = a[i];
            }

            for (int i = 0; i < N; i++) {
                a[i] = aux[i];
            }

        }
    }

    public static void main(String[] args) {
        String stu1 = new String("cac");
        String stu2 = new String("fnc");
        String stu3 = new String("edb");
        String stu4 = new String("oel");
        String stu5 = new String("akf");
        String stu6 = new String("erk");

        String[] stus = { stu1, stu2, stu3, stu4, stu5, stu6 };
        sort(stus, 3);

        for (int i = 0; i < stus.length; i++) {
            System.out.println(stus[i].toString());
        }
    }
}

