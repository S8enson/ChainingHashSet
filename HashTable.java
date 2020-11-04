/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hashsetwithchaining;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *
 * @author Sam
 */
public class HashTable<E> {

    /**
     * @param args the command line arguments
     */
    private Node[] array;
    private int capacity;
    private int numElements;
    protected int numTakenHash;
    private double loadFactor;

    public HashTable() {
        capacity = 10;
        array = new Node[capacity];
        loadFactor = 0.75;
        numElements = 0;
        numTakenHash = 0;

    }

    public HashTable(int initialCapacity, int loadFactor) {
        this.loadFactor = (double) loadFactor / 100;

        numElements = 0;
        numTakenHash = 0;
        capacity = initialCapacity;
        array = new Node[capacity];
    }

    public int getHash(E element) {
        int hash = element.hashCode();
        if (hash < 0) {
            hash = hash * -1;
        }
        if (hash > capacity) {
            hash = hash % capacity;
        }
        return hash;
    }

    public void add(E element) {
        Node newNode = new Node(element);
        // check if above load factor

//        if (numTakenHash >= (capacity * loadFactor)) {
//            expandCapacity();
//        }
        int hash = getHash(element);

        if (array[hash] == null) {
            array[hash] = newNode;
            numElements++;
            numTakenHash++;

        } else {
            newNode.next = array[hash];
            array[hash] = newNode;
            numElements++;
        }
    }

    public void remove(E element) {
        int hash = getHash(element);
        Node current = array[hash];
        if (current.element == element) {
            if (current.next == null) {
                array[hash] = null;
                numTakenHash--;
                numElements--;

                return;
            } else {
                array[hash] = current.next;
                numElements--;

            }
        } else {
            while (current.next.element != element) {
                current = current.next;
            }

            current.next = current.next.next;
            numElements--;

        }
    }


    public int size() {
        return numElements;
    }

    public boolean contains(E element) {
        int hash = getHash(element);
        if (numElements == 0) {
            return false;
        } else if (array[hash] != null) {
            Node curr = array[hash];
            while (curr != null) {
                if (curr.element == element) {
                    return true;
                }
                curr = curr.next;
            }
        }

        return false;

    }

    public String indexString(int index) {
        String s = "";
        int num = 0;
        Node curr = array[index];

        while (curr != null) {
            if (num > 0) {
                s += "-->";
            }
            s += curr.element;
            curr = curr.next;
            num++;
        }

        return s;
    }

    public Iterator<E> iterator() {
        Node curr;

        return new ChainingIterator<>();
    }

    private class ChainingIterator<E> implements Iterator<E> {

        private Node<E> nextNode;
        private int i;
        // next node to use for the iterator

        // constructor which accepts a reference to first node in list
        // and prepares an iterator which will iterate through the
        // entire linked list
        public ChainingIterator() {
            i = 0;
            find();
            // start with first node in list
        }

        private void find() {
            while (i < capacity) {
                nextNode = array[i];
                if (nextNode != null) {
                    break;
                }
                i++;
            }
            if (i < capacity) {
                nextNode = array[i];
            } else {
                nextNode = null;
            }
        }

        // returns whether there is still another element
        public boolean hasNext() {
            return (nextNode != null);
        }

        // returns the next element or throws a NoSuchElementException
        // it there are no further elements
        public E next() throws NoSuchElementException {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            E element = nextNode.element;
            if (nextNode.next != null) {
                nextNode = nextNode.next;
            } else {
                i++;
                find();
            }
            return element;
        }
    }

    protected class Node<E> {

        public E element;
        public Node<E> next;

        public Node(E element) {
            this.element = element;
            next = null;
        }
    }
}
