package ADT;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.Stopwatch;

public class UnionFind {
    private int[] id;
    private int count;
    private int[] sz; //对应的分量大小（权值）

    public UnionFind(int N) {
        this.count = N;
        id = new int[N];
        sz = new int[N];
        for (int i = 0; i < N; i++) {
            id[i] = i;
        }
    }

    public int count(){return count;}

    public boolean connected(int p, int q){
        return find(p) == find(q);
    }

    public int find(int p){
        return id[p] == p ? p : (id[p] = find(id[p]));
    }

    public void union(int p, int q){
        int i = find(p);
        int j = find(q);
        if(i == j) return;
        if(sz[i] <= sz[j]){
            id[i] = j;
            sz[j] += sz[i];
        } else {
            id[j] = i;
            sz[i] += sz[j];
        }
        count--;
    }

    public static void main(String[] args) {
        In in = new In("largeUF.txt");
        int N = in.readInt();
        UnionFind uf = new UnionFind(N);
        Stopwatch time = new Stopwatch();
        while(!in.isEmpty()){
            int p = in.readInt();
            int q = in.readInt();
            uf.union(p,q);
        }
        System.out.println(uf.count() + " components");
        System.out.println("use time: " + time.elapsedTime());
    }
}
