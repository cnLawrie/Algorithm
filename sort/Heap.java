package sort;

import edu.princeton.cs.algs4.In;

public class Heap {
    public static void sort(Comparable[] pq){
        int N = pq.length;
        for (int i = N/2; i >= 1 ; i--)
            sink(pq, i, N);
        while(N > 1){
            exch(pq, 1, N--);
            sink(pq, 1, N);
        }
    }

    private static void sink(Comparable[] pq, int k, int n){
        while(2 * k <= n){
            int j = 2 * k;
            if(j < n && less(pq, j, j+1)) j++;
            if(!less(pq, k, j)) break;
            exch(pq, k, j);
            k = j;
        }
    }

    private static boolean less(Comparable[] pq, int i, int j){
        return pq[i - 1].compareTo(pq[j - 1]) < 0;
    }

    private static void exch(Comparable[] pq, int i, int j){
        Comparable swap = pq[i - 1];
        pq[i - 1] = pq[j - 1];
        pq[j - 1] = swap;
    }

    public static void show(Comparable[] pq){
        for (int i = 0; i < pq.length; i++) {
            System.out.print(pq[i] + " ");
        }
    }

    public static void main(String[] args) {
        In in = new In("tiny.txt");
        String[] a = in.readAllStrings();
        sort(a);
        show(a);
    }
}
