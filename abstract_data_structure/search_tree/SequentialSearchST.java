package ADT.ST;

import ADT.Queue;

public class SequentialSearchST<Key,Value> {
    private Node first;
    private int N = 0;
    private class Node{
        Key key;
        Value value;
        Node next;
        public Node(Key key, Value value, Node next){
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    public Value get(Key key){
        for (Node x = first; x != null; x = x.next)
            if (key.equals(x.key))
                return x.value;
        return null;
    }

    public void put(Key key, Value value){
        for (Node x = first; x != null; x = x.next)
            if(key.equals(x.key)){
                x.value = value;
                return;
            }
        first = new Node(key, value, first);
        N++;
    }

    public void delete(Key key){
        first = delete(first, key); //递归从链首查找要删除的节点
    }

    public Node delete(Node node, Key key){
        if(node == null)
            return null;
        if(node.key.equals(key)){
            N--;
            return node.next;
        }
        node.next = delete(node.next, key);
        return node;
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

    public Iterable<Key> keys(){
        Queue<Key> queue = new Queue<>();
        for(Node node = first; node != null; node = node.next)
            queue.enqueue(node.key);
        return queue;
    }
}
