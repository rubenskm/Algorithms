import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] a;         // array of items
    private int N;            // number of elements on stack

    /**
     * construct an empty randomized queue
     */
    public RandomizedQueue() {
        a = (Item[]) new Object[2];
    }

    // unit testing
    public static void main(String[] args) {

    }

    /**
     * Is this stack empty?
     *
     * @return true if this stack is empty; false otherwise
     */
    public boolean isEmpty() {
        return N == 0;
    }

    /**
     * Returns the number of items in the stack.
     *
     * @return the number of items in the stack
     */
    public int size() {
        return N;
    }

    // resize the underlying array holding the elements
    private void resize(int capacity) {
        assert capacity >= N;
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < N; i++) {
            temp[i] = a[i];
        }
        a = temp;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }

        if (N == a.length) resize(2 * a.length);    // double size of array if necessary
        a[N++] = item;                            // add item
    }

    // delete and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        int index = StdRandom.uniform(N);

        Item item = a[index];
        a[index] = a[N - 1];
        a[N - 1] = null; // to avoid loitering
        N--;
        // shrink size of array if necessary
        if (N > 0 && N == a.length / 4) resize(a.length / 2);
        return item;
    }

    // return (but do not delete) a random item
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        return a[StdRandom.uniform(N)];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private int i;
        private int[] randPosition;

        public ListIterator() {
            i = 0;
            randPosition = new int[N];
            for (int j = 0; j < N; j++) {
                randPosition[j] = j;
            }

            StdRandom.shuffle(randPosition);
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public boolean hasNext() {
            return i < N;
        }

        public Item next() {

            if (hasNext()) {
                return a[randPosition[i++]];
            } else {
                throw new NoSuchElementException();
            }
        }
    }
}
