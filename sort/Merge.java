package sort;

import edu.princeton.cs.algs4.In;

public class Merge {

    public static void sort(Comparable[] a){
        Comparable[]aux = new Comparable[a.length];
        sort(a, aux, 0,a.length - 1);
    }

    public static void sort(Comparable[] a,Comparable[]aux,  int lo, int hi){
        if(hi <= lo)
            return;
        int mid = lo + (hi-lo)/2;
        sort(a, aux, lo, mid);
        sort(a, aux, mid + 1, hi);
        if(less(a[mid + 1], a[mid])){  //若有序则不进行归并
            merge(a, aux, lo, mid, hi);
        }
    }

    public static void merge(Comparable[]a, Comparable[]aux, int lo, int mid, int hi){
        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k];
        }
        for (int k = lo; k <= hi; k++) {
            if(i > mid)
                a[k] = aux[j++];
            else if(j > hi)
                a[k] = aux[i++];
            else if(less(aux[j], aux[i]))
                a[k] = aux[j++];
            else
                a[k] = aux[i++];
        }
    }

//    public static void sortBU(Comparable[] a){ //自底向上
//        int N = a.length;
//        aux = new Comparable[N];
//        for (int sz = 1; sz < N; sz += sz) {
//            for (int lo = 0; lo < N - sz; lo += sz+sz) {
//                merge(a, lo, lo+sz-1, Math.min(lo+sz+sz-1, N-1));
//            }
//        }
//    }

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
//        Integer[] a = {2,5,62,3,67,3,2,4};
        sort(a);
        assert isSorted(a);
        show(a);
    }
}
