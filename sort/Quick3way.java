package sort;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdRandom;
//三项切分
public class Quick3way {
    private static final int M = 15;
    public static void sort(Comparable[] a){
        sort(a, 0, a.length-1);
    }

    public static void sort(Comparable[] a, int lo, int hi){
        if(hi <= lo) return;
        int lt = lo, i = lo + 1, gt = hi;
        Comparable v = a[lo];
        while(i <= gt){
            int cmp = a[i].compareTo(v);
            if(cmp < 0) exch(a, lt++, i++);
            else if(cmp > 0) exch(a, i, gt--);
            else i++;
        }
        sort(a, lo, lt -1 );
        sort(a, gt+1, hi);
    }

    public static int partition(Comparable[] a,int lo, int hi){
        int i = lo, j = hi + 1;
        int rank = 0;
        for (int k = 0; k < 3; k++) {
            rank += StdRandom.uniform(lo,hi);
        }
        Comparable v = a[rank/3]; //三取样
        while(true){
            while(less(a[++i], v)) if(i == hi) break;
            while(less(v, a[--j])) if(j == lo) break;
            if(i >= j) break;
            exch(a, i, j);
        }
        exch(a, lo, j);
        return j;
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
