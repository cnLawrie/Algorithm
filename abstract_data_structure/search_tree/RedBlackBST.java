package ADT.ST;

import ADT.Queue;
import edu.princeton.cs.algs4.StdIn;
import java.util.NoSuchElementException;

public class RedBlackBST<Key extends Comparable, Value> {
    private static final boolean RED = true;
    private static final boolean BLACK = false;
    private Node root;

    private class Node{
        Key key;
        Value value;
        Node left,right;
        int N;
        boolean color;

        public Node(Key key, Value value, int N, boolean color) {
            this.key = key;
            this.value = value;
            this.N = N;
            this.color = color;
        }
    }

    public Value get(Key key){
        return get(root, key);
    }

    public Value get(Node x, Key key){
        if(x == null)
            return null;
        int cmp = key.compareTo(x.key);
        if     (cmp < 0) return get(x.left, key);
        else if(cmp > 0) return get(x.right, key);
        else             return x.value;
    }

    public void put(Key key, Value value){
        root = put(root, key, value);
        root.color = BLACK;
    }

    private Node put(Node h, Key key, Value value){
        if(h == null) return new Node(key, value, 1, RED);

        int cmp = key.compareTo(h.key);
        if      (cmp < 0) h.left = put(h.left, key, value);
        else if (cmp > 0) h.right = put(h.right, key, value);
        else              h.value = value;

        if(isRed(h.right) && !isRed(h))         h = rotateLeft(h);
        if(isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
        if(isRed(h.left) && isRed(h.right))     flipColor(h);

        h.N = size(h.left) + size(h.right) + 1;
        return h;
    }

    /***************************************************************************
     *  Red-black tree deletion.
     ***************************************************************************/
    public void deleteMin(){
        if(isEmpty()) throw new NoSuchElementException("BST underflow");
        if(!isRed(root.left) && !isRed(root.right))
            root.color = RED;
        root = deleteMin(root);
        if(!isEmpty()) root.color = BLACK;
    }

    private Node deleteMin(Node h){
        if(h.left == null)
            return null;  //进行删除
        if(!isRed(root.left) && !isRed(root.left.left)) //当前节点和其左孩子不是3-结点
            h = moveRedLeft(h);
        h.left = deleteMin(h.left);
        return balance(h);   //递归返回时自下向上修正
    }

    public void deleteMax() {
        if (!isRed(root.left) && !isRed(root.right)) {
            root.color = RED;
        }

        root = deleteMax(root);
        if (!isEmpty()) root.color = BLACK;
    }

    private Node deleteMax(Node h){
        //h红链接右斜
        if(isRed(h.left))
            h = rotateRight(h);
        if(h.right == null)
            return null;
        if(!isRed(h.right) && !isRed(h.right.left))
            h = moveRedRight(h);
        h.right = deleteMax(h.right);
        return balance(h);
    }

    public void delete(Key key){
        if (!isRed(root.left) && !isRed(root.right)) {
            root.color = RED;
        }

        root = delete(root, key);
        if (!isEmpty()) root.color = BLACK;
    }

    public Node delete(Node h, Key key){
        if(key.compareTo(h.key) < 0){
            if(!isRed(h.left) && !isRed(h.left.left)) //当前节点和其左孩子不是3-结点,则融和为4-节点
                h = moveRedLeft(h);
        } else {
            //保证链接右斜
            if(isRed(h.left)) h = rotateRight(h);
            if(key.compareTo(h.key) == 0 && (h.right == null))
                return null;
            //右子树遍历保证节点非2-结点
            if(!isRed(h.right) && !isRed(h.right.left))
                h = moveRedRight(h);
            //找到了，将节点替换为后继结点
            if(key.compareTo(h.key) == 0){
                Node x = min(h.right);
                h.key = x.key;
                h.value = x.value;
                h.right = deleteMin(h.right);
            } else {
                h.right = delete(h.right, key);
            }
        }
        return balance(h);
    }

    private boolean isRed(Node x){
        if(x == null) return false;
        return x.color == RED;
    }

    private Node rotateLeft(Node h){
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color =  h.color;
        h.color = RED;
        x.N = h.N;
        h.N = size(h.left) + size(h.right) + 1;
        return x;
    }

    private Node rotateRight(Node h){
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = h.color;
        h.color = RED;
        x.N = h.N;
        h.N = size(h.left) + size(h.right) + 1;
        return x;
    }

    private void flipColor(Node h){
        h.color = !h.color;
        h.left.color = !h.left.color;
        h.right.color = !h.right.color;
    }

    //融合当前节点、左孩子、右孩子为新的4-节点
    private Node moveRedLeft(Node h){
        flipColor(h);
        if (isRed(h.right.left)){     //如果右孩子是3-节点，借键给左孩子
            h.right = rotateRight(h.right);
            h = rotateLeft(h);
        }
        return h;
    }

    private Node moveRedRight(Node h){
        flipColor(h);
        if(!isRed(h.left.left)) h = rotateRight(h);
        return h;
    }

    private Node balance(Node h){
        //右斜红链接调整
        if(isRed(h.right))                      h = rotateLeft(h);
        //连续的红链接调整
        if(isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
        //4-结点调整
        if(isRed(h.left) && isRed(h.right))     flipColor(h);

        return h;
    }

    public int size(){
        return size(root);
    }

    public int size(Node x){
        if(x == null) return 0;
        else          return x.N;
    }

    public boolean isEmpty(){ return size() == 0; }

    public boolean contains(Key key) {
        return get(key) != null;
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
        if(cmplo < 0)                keys(x.left, queue, lo, hi);
        if(cmplo <= 0 && cmphi >= 0) queue.enqueue(x.key);
        if(cmphi > 0)                keys(x.right, queue, lo, hi);
    }

    public static void main(String[] args) {
        edu.princeton.cs.algs4.RedBlackBST<String, Integer> bst = new edu.princeton.cs.algs4.RedBlackBST<>();
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
    }

}
