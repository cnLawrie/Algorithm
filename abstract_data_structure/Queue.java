package ADT;

import edu.princeton.cs.algs4.IndexMinPQ;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Queue<E> implements Iterable<E>{
    private int N;
    private Node<E> first;
    private Node<E> last;
    private class Node<E>{
        E elem;
        Node<E> next;
    }

    public boolean isEmpty(){return first == null;}

    public int size(){return N;}

    public void enqueue(E elem){
        Node oldlast = last;
        last = new Node<E>();
        last.elem = elem;
        last.next = null;
        if(isEmpty()) first = last;
        else  oldlast.next = last;
        N++;
    }

    public E dequeue(){
        if(N == 0)
            return null;
        E elem = first.elem;
        first = first.next;
        if(isEmpty())
            last = null;
        N--;
        return elem;
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @NotNull
    @Override
    public Iterator<E> iterator() {
        return new ListIterator<E>(first);
    }
    private class ListIterator<E> implements Iterator<E>{
        private Node<E> current;

        public ListIterator(Node<E> first) {
            this.current = first;
        }

        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            return current != null;
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public E next() {
            if(!hasNext()) throw new NoSuchElementException();
            E elem = current.elem;
            current = current.next;
            return elem;
        }
    }

    public static void main(String[] args) {
        // insert a bunch of strings
        String[] strings = { "it", "was", "the", "best", "of", "times", "it", "was", "the", "worst" };

        IndexMinPQ<String> pq = new IndexMinPQ<String>(strings.length);
        for (int i = 0; i < strings.length; i++) {
            pq.insert(i, strings[i]);
        }

        // delete and print each key
        while (!pq.isEmpty()) {
            int i = pq.delMin();
            StdOut.println(i + " " + strings[i]);
        }
        StdOut.println();

        // reinsert the same strings
        for (int i = 0; i < strings.length; i++) {
            pq.insert(i, strings[i]);
        }

        // print each key using the iterator
        for (int i : pq) {
            StdOut.println(i + " " + strings[i]);
        }
        while (!pq.isEmpty()) {
            pq.delMin();
        }

    }

}
