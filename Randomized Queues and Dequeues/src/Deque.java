import java.util.Iterator;
import java.util.NoSuchElementException;

//Dequeue. A double-ended queue or deque (pronounced "deck") is a generalization of a stack and a queue
// that supports inserting and removing items from either the front or the back of the data structure.
// Create a generic data type Deque that implements the following API:

public class Deque<Item> implements Iterable<Item> {

    private Node head;
    private Node tail;
    private int size;

    // construct an empty deque
    public Deque() {
        size = 0;
        head = null;
        tail = null;
    }

    // unit testing
    public static void main(String[] args) {

    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // insert the item at the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }

        if (isEmpty()) {
            FillInitialNode(item);
        } else {
            Node old = head;
            head = new Node();
            head.item = item;
            head.next = old;
            size++;
        }
    }

    // insert the item at the end
    public void addLast(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }

        if (isEmpty()) {
            FillInitialNode(item);
        } else {
            Node old = tail;
            tail = new Node();
            tail.item = item;
            tail.prev = old;
            size++;
        }
    }

    // delete and return the item at the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Node first = head;
        head = first.next;
        size--;

        return first.item;
    }

    // delete and return the item at the end
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Node last = tail;
        tail = last.prev;
        size--;

        return last.item;
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private void FillInitialNode(Item item) {
        head = new Node();
        head.item = item;
        head.next = null;
        head.prev = null;
        tail = head;
        size++;
    }

    private class Node {
        Item item;
        Node next;
        Node prev;
    }

    public class ListIterator implements Iterator<Item> {

        //Your deque implementation must support each deque operation in constant worst-case time and use space proportional
        // to the number of items currently in the deque. Additionally, your iterator implementation must support
        // the operations next() and hasNext() (plus construction) in constant worst-case time and use a constant amount of extra space per iterator.

        private Node current = head;

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public boolean hasNext() {
            return current.next != null;
        }

        public Item next() {

            if (hasNext()) {
                Item item = current.item;
                current = current.next;
                return item;
            } else {
                throw new NoSuchElementException();
            }
        }
    }
}

