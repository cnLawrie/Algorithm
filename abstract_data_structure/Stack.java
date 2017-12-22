package ADT;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class Stack<E> implements Iterable<E>{
    private int N;
    private Node first;

    private class Node{
        E elem;
        Node next;
    }

    public boolean isEmpty(){return first == null;}

    public int size(){return N;}

    public void push(E elem){
        Node oldfirst = first;
        first = new Node();
        first.elem = elem;
        first.next = oldfirst;
        N++;
    }

    public E pop(){
        E elem = first.elem;
        first = first.next;
        N--;
        return elem;
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator iterator() {
        return new ListIterator();
    }
    private class ListIterator implements Iterator<E>{
        private Node current = first;
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
            if(!hasNext())
                throw new NoSuchElementException();
            E elem = current.elem;
            current = current.next;
            return elem;
        }
    }
}
