package ADT;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class Bag<E> implements Iterable<E>{
    private Node<E> first = null;

    private class Node<E>{
        E elem;
        Node<E> next;
    }

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

    public static void main(String[] args) {
        Bag<String> bag = new Bag<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if(item.equals("quit"))
                break;
            bag.add(item);
        }

        for (String s : bag) {
            StdOut.println(s);
        }
    }
}

