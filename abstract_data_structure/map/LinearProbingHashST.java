package ADT.Map;

import ADT.Queue;
import edu.princeton.cs.algs4.SET;

import java.util.HashSet;

public class LinearProbingHashST<Key, Value> {
    private int N;
    private int M = 16;
    private Key[] keys;
    private Value[] values;
    public LinearProbingHashST(){
        keys = (Key[]) new Object[M];
        values = (Value[]) new Object[N];
    }

    public LinearProbingHashST(int M){
        keys = (Key[]) new Object[M];
        values = (Value[]) new Object[N];
        this.M = M;
    }

    private int hash(Key key){
        return (key.hashCode() & 0x7fffffff);
    }

    private void resize(int cap){
        LinearProbingHashST<Key, Value> t;
        t = new LinearProbingHashST<>(cap);
        for (int i = 0; i < M; i++) {
            if(keys[i] != null)
                t.put(keys[i], values[i]);
        }
        keys = t.keys;
        values = t.values;
        M = t.M;
    }

    public void put(Key key, Value value){
        if(N >= M/2) resize(2*M);
        int i;
        for(i = hash(key); keys[i] != null; i = (i + 1) % M)
            if(keys[i].equals(key)){
                values[i] = value;
                return;
            }
        keys[i] = key;
        values[i] = value;
        N++;
    }

    public Value get(Key key){
        for (int i = hash(key); keys[i] != null; i = (i + 1) % M) {
            if(keys[i].equals(key))
                return values[i];
        }
        return null;
    }

    public void delete(Key key){
        if(!contains(key)) return;
        int i = hash(key);
        while (!key.equals(keys[i]))
            i = (i + 1) % M;
        keys[i] = null;
        values[i] = null;
        i = (i + 1) % M;
        while(keys[i] != null){
            Key keyToRedo = keys[i];
            Value valToRedo = values[i];
            keys[i] = null;
            values[i] = null;
            N--;
            put(keyToRedo,valToRedo);
            i = (i + 1) % M;
        }
        N--;
        if(N > 0 && N == M/8)
            resize(M/2);
    }

    public boolean contains(Key key){
        return get(key) != null;
    }

    public Iterable<Key> keys(){
        Queue<Key> queue = new Queue<>();
        for (int i = 0; i < M; i++) {
            if(keys[i] != null)
                queue.enqueue(keys[i]);
        }
        return queue;
    }

}
