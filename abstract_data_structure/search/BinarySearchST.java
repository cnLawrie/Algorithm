package ADT.ST;

import ADT.Queue;
import edu.princeton.cs.algs4.StdIn;

//优点：最优的查找效率O(logn)和空间需求,能进行有序性的相关操作
//缺点：插入操作成本太高O(n)
public class BinarySearchST <Key extends Comparable<Key>, Value> {
    private Key[] keys;
    private Value[] values;
    private int N;
    public BinarySearchST(int capacity){
        keys = (Key[]) new Comparable[capacity];
        values = (Value[]) new Object[capacity];
    }

    public Value get(Key key){
        if(isEmpty()) return null;
        int i = rank(key);
        if(i < N && keys[i].compareTo(key) == 0)
            return values[i];
        else
            return null;
    }

    //返回小于等于key的键的数量
    public int rank(Key key){
        int lo = 0, hi = N - 1;
        while(lo <= hi){
            int mid = lo + (hi - lo) / 2;
            int cmp = key.compareTo(keys[mid]);
            if(cmp < 0)
                hi = mid - 1;
            else if (cmp > 0)
                lo = mid + 1;
            else
                return mid;
        }
        return lo;
    }

    public void put(Key key, Value value){
        int i = rank(key);
        if(i < N && keys[i].compareTo(key) == 0){
            values[i] = value;
            return;
        }

        for (int j = N; j > i ; i--) {
            keys[j] = keys[j-1];
            values[j] = values[j-1];
        }
        keys[i] = key;
        values[i] = value;
        N++;
    }

    public void delete(Key key){
        if(isEmpty())
            return;
        int i = rank(key);
        if(i >= N || keys[i].compareTo(key) != 0)
            return;

        for (int j = i; j < N - 1; j++) {
            keys[j] = keys[j+1];
            values[j] = values[j+1];
        }
        N--;
        keys[N] = null;
        values[N] = null;
    }

    public Key min(){
        return keys[0];
    }

    public Key max(){
        return keys[N-1];
    }

    public Key select(int k){
        return keys[k];
    }

    public Key ceiling(Key key){
        int i = rank(key);
        return keys[i];
    }

    public Key floor(Key key){
        int i = rank(key);
        if(i >= N)
            return null;

        if(select(i).compareTo(key) == 0)
            return keys[i];
        else
            return keys[i - 1];
    }

    public Iterable<Key> keys(Key lo, Key hi){
        Queue<Key> q = new Queue<>();
        for (int i = rank(lo); i < rank(hi); i++) {
            q.enqueue(keys[i]);
        }
        if(contains(hi))
            q.enqueue(keys[rank(hi)]);
        return q;
    }

    public Iterable<Key> keys(){
        return this.keys(min(), max());
    }

    public boolean contains(Key key){
        return get(key) != null;
    }

    public boolean isEmpty(){
        return N == 0;
    }

    public int size(){
        return N;
    }

    public int size(Key lo, Key hi){
        int i = rank(lo), j = rank(hi);
        if(contains(hi))
            return j - i + 1;
        else
            return j - i;
    }

    private boolean rankCheck(){
        for (int i = 0; i < size(); i++)
            if(i != rank(select(i)))
                return false;
        for (int i = 0; i < size(); i++)
            if(keys[i].compareTo(select(rank(keys[i]))) != 0)
                return false;
        return true;
    }

    public static void main(String[] args) {
        BinarySearchST<String, Integer> st = new BinarySearchST<>(1000000);
        for (int i = 0; !StdIn.isEmpty(); i++) {
            String key = StdIn.readString();
            st.put(key, i);
        }
        for (String s : st.keys()) {
            System.out.println(s + " " + st.get(s));
        }
        System.out.println("min(): " + st.min());
        System.out.println("max(): " + st.max());
        System.out.println("floor(\"F\"): " + st.floor("F"));
        System.out.println("ceiling(\"C\"): " + st.ceiling("C"));
        System.out.println("select(0): " + st.select(0));
        System.out.println("rank(\"R\"): " + st.rank("R"));
        System.out.println("deleteMin():");
        for (String s : st.keys()) {
            System.out.println(s + " " + st.get(s));
        }
        System.out.println("deleteMax():");
        for (String s : st.keys()) {
            System.out.println(s + " " + st.get(s));
        }
    }
}
