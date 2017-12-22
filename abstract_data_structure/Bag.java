package ADT;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class Bag<E> implements Iterable<E>{
    private Node<E> first = null;

    private class Node<E>{
        E elem;
        Node<E> next;
    }

    //添加一个元素
    public void add(E elem){
        Node oldfirst = first;
        first = new Node();
        first.elem = elem;
        first.next = oldfirst;
    }

    public Iterator<E> iterator() {
        return new ListIterator<E>(first);
    }
    private class ListIterator<E> implements Iterator<E>{
        private Node<E> current;

        public ListIterator(Node<E> current) {
            this.current = current;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

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

