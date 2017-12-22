package ADT.ST;

import ADT.Queue;
import edu.princeton.cs.algs4.StdIn;

public class BST<Key extends Comparable, Value> {
    private Node root;

    private class Node{
        Key key;
        Value value;
        Node left,right;
        int N;

        public Node(Key key, Value value, int n) {
            this.key = key;
            this.value = value;
            this.N = n;
        }
    }

    public void put(Key key, Value value){
        root = put(root, key, value);
    }
    
    public Node put(Node x, Key key, Value value){
        if(x == null) return new Node(key, value, 1);
        int cmp = key.compareTo(x.key);
        if     (cmp < 0) x.left = put(x.left, key, value);
        else if(cmp > 0) x.right = put(x.right, key, value);
        else             x.value = value;
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }
    
    public Value get(Key key){
        return get(root, key);
    }
    
    public Value get(Node x, Key key){
        if(x == null)
        return null;
        int cmp = key.compareTo(x.key);
        if(cmp < 0)      return get(x.left, key);
        else if(cmp > 0) return get(x.right, key);
        else             return x.value;
    }
    

    public void delete(Key key){
        root = delete(root, key);
    }

    public Node delete(Node x, Key key){
        if(x == null)
            return null;
        int cmp = key.compareTo(x.key);
        if(cmp < 0)
            x.left = delete(x.left, key);
        else if(cmp > 0)
            x.right = delete(x.right, key);
        else{
            if(x.right == null) return x.left;
            if(x.left == null) return x.right;
            Node t = x;
            x = min(t.right);
            x.right = deleteMin(t.right);
            x.left = t.left;
        }
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    boolean contains(Key key) {
        return get(key) != null;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size(){
        return size(root);
    }

    public int size(Node x){
        if(x == null)
            return 0;
        else
            return x.N;
    }
    public Key min(){
        return min(root).key;
    }
    
    private Node min(Node x){
        if(x == null)      return null;
        if(x.left == null) return x;
        return min(x.left);
    }

    public Key max(){
        return max(root).key;
    }

    private Node max(Node x){
        if(x.right == null) return x;
        else                return max(x.right);
    }

    public Key floor(Key key){
        Node x = floor(root, key);
        if(x == null)
            return null;
        return x.key;
    }

    private Node floor(Node x, Key key){
        if(x == null) return null;
        int cmp = key.compareTo(x.key);
        if(cmp < 0)       return floor(x.left, key);
        else if(cmp == 0) return x;
        Node t = floor(x.right, key);
        if(t != null)     return t;
        else              return x;
    }

    public Key ceiling(Key key){
        Node x =ceiling(root, key);
        if(x == null) return null;
        return x.key;
    }

    private Node ceiling(Node x, Key key){
        if(x == null) return null;
        int cmp = key.compareTo(x.key);
        if     (cmp > 0)  return ceiling(x.right, key);
        else if(cmp == 0) return x;
        Node t = ceiling(x.left, key);
        if(t !=null)      return t;
        else              return x;
    }

    //找到排名为k的键
    public Key select(int k){
        return select(root, k).key;
    }

    private Node select(Node x, int k) {
        if (x == null) {
            return null;
        }
        int t = size(x.left);
        if (t > k)      return select(x.left, k);
        else if (t < k) return select(x.right, k - t - 1);
        else            return x;

    }

    public int rank(Key key){
        return rank(root, key);
    }

    private int rank(Node x, Key key){
        if(x == null)
            return 0;
        int cmp = key.compareTo(x.key);
        if     (cmp < 0) return rank(x.left, key);
        else if(cmp > 0) return 1 + size(x.left) + rank(x.right, key);
        else             return size(x.left);
    }

    public void deleteMin(){
        root = deleteMin(root);
    }

    private Node deleteMin(Node x){
        if(x.left == null)
            return x.right;
        x.left = deleteMin(x.left);
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    public void deleteMax(){
        root = deleteMax(root);
    }

    private Node deleteMax(Node x){
        if(x.right == null)
            return x.left;
        x.right = deleteMax(x.right);
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }


    public Iterable<Key> keys(){
        if(isEmpty()) return new Queue<Key>();
        return keys(min(),max());
    }

    public Iterable<Key> keys(Key lo, Key hi){
        Queue<Key> queue = new Queue<>();
        keys(root, queue, lo, hi);
        return queue;
    }

    private void keys(Node x, Queue<Key> queue, Key lo, Key hi){
        if(x == null) return;
        int cmplo = lo.compareTo(x.key);
        int cmphi = hi.compareTo(x.key);
        if(cmplo < 0) keys(x.left, queue, lo, hi);
        if(cmplo <= 0 && cmphi >= 0) queue.enqueue(x.key);
        if(cmphi > 0) keys(x.right, queue, lo, hi);
    }

    public static void main(String[] args) {

        BST<String, Integer> bst = new BST<>();
        for (int i = 0; !StdIn.isEmpty(); i++) {
            String key = StdIn.readString();
            if(key.equals("quit"))
                break;
            bst.put(key, i);
        }
        for (String s : bst.keys()) {
            System.out.println(s + " " + bst.get(s));
        }
        System.out.println("min(): " + bst.min());
        System.out.println("max(): " + bst.max());
        System.out.println("floor(\"F\"): " + bst.floor("F"));
        System.out.println("ceiling(\"C\"): " + bst.ceiling("C"));
        System.out.println("select(1): " + bst.select(1));
        System.out.println("rank(\"R\"): " + bst.rank("R"));
        System.out.println("delete(\"D\"):");
        bst.delete("D");
        for (String s : bst.keys()) {
            System.out.println(s + " " + bst.get(s));
        }
        System.out.println("deleteMin():");
        bst.deleteMin();
        for (String s : bst.keys()) {
            System.out.println(s + " " + bst.get(s));
        }
        System.out.println("deleteMax():");
        bst.deleteMax();
        for (String s : bst.keys()) {
            System.out.println(s + " " + bst.get(s));
        }
    }

}

