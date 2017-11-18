package sort;

import edu.princeton.cs.algs4.In;

public class Insertion {
    public static void sort(Comparable[] a){
        int N = a.length;
        for (int i = 1; i < N; i++) {
            for (int j = i; j > 0 && less(a[j],a[j-1]); j--) {
                exch(a, j, j-1);
            }
        }
    }

    public static void sortRank(Comparable[] a, int lo, int hi){
        for (int i = lo+1; i <= hi; i++) {
            if(less(a[i],a[i-1])){
                Comparable tmp = a[i];
                int j;
                for (j = i; j > 0 && less(tmp,a[j-1]) ; j--) {
                    a[j] = a[j-1];
                }
                a[j] = tmp;
            }
        }
    }

//    Insertion2 use times: 5.482000
//    Insertion use times: 9.965000
//    Insertion2 is 1.8 times faster than Insertion
    public static void sortFaster(Comparable[] a){
        int N = a.length;
        int j;
        for(int i = 1; i < N; i++)//先寻找无序区的开头
            if (less(a[i],a[i - 1])){ //a[i]<a[i-1],找到a[i]是无序区开头，将a[i]插入有序区
                Comparable temp = a[i];
                for (j = i; j > 0 && less(temp, a[j - 1]); j--)//a[j-1]>temp,形象地说j在自己身后挖了一个坑，然后j去试探当前的元素是否比自己大，如果是就把自己的坑让给这个比自己大的元素，如果不是就把自己放入坑内。
                    a[j] = a[j - 1];
                a[j] = temp;//把自己放入坑内
            }
    }

    private static boolean less(Comparable a, Comparable b){
        return a.compareTo(b) < 0;
    }

    private static void exch(Comparable[] a, int i, int j){
        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    private static void show(Comparable[] a){
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i]+" ");
        }
        System.out.println();
    }

    public static boolean isSorted(Comparable[] a){
        for (int i = 1; i < a.length; i++) {
            if(a[i].compareTo(a[i-1]) < 0)
                return false;
        }
        return true;
    }

    public static void main(String[] args) {
        In in = new In("tiny.txt");
        String[] a = in.readAllStrings();
        sort(a);
        assert isSorted(a);
        show(a);
    }
}
