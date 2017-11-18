package sort;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdRandom;

import java.util.PriorityQueue;

public class Quick {
    private static final int M = 15;
    public static void sort(Comparable[] a){
        sort(a, 0, a.length-1);
    }

    public static void sort(Comparable[] a, int lo, int hi){
        if(hi <= lo + M){
            Insertion.sortRank(a, lo, hi);
            return;
        }
        int j = partition(a, lo, hi);
        sort(a, lo, j-1);
        sort(a, j+1, hi);
    }

    public static int partition(Comparable[] a,int lo, int hi){
        int i = lo, j = hi + 1;
        int rank = lo;
        if(j - i > 10){ //数组超过一定规模后进行三取样
            rank = 0;
            for (int k = 0; k < 3; k++) {
                rank += StdRandom.uniform(lo,hi);
            }
            rank = rank/3;
        }
        Comparable v = a[rank];
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

    public static Comparable select(Comparable[] a, int k){
        int lo = 0, hi = a.length - 1;
        while(lo < hi){
            int j = partition(a, lo, hi);
            if(j > k) hi = j - 1;
            else if(j < k) lo = j + 1;
            else return a[j];
        }
        return a[k];
    }

    public static void main(String[] args) {
        In in = new In("tiny.txt");
        String[] a = in.readAllStrings();
        sort(a);
        assert isSorted(a);
        show(a);

        Integer[] b = {2,5,23,6,7,3,9};
        System.out.println(select(b, 3));

    }
}
