import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DequeTest {

    Deque<Integer> deque;

    @org.junit.Before
    public void setUp() throws Exception {
        deque = new Deque<Integer>();
    }

    @org.junit.After
    public void tearDown() throws Exception {

    }

    @org.junit.Test
    public void testIsEmpty() throws Exception {
        assertTrue(deque.isEmpty());
    }

    @org.junit.Test
    public void testSize() throws Exception {
        assertEquals(deque.size(), 0);
    }

    @org.junit.Test
    public void testAddFirst() throws Exception {
        deque.addFirst(1);
        assertEquals(deque.size(), 1);
    }

    @org.junit.Test
    public void testAddLast() throws Exception {
        deque.addLast(1);
        assertEquals(deque.size(), 1);
    }

    @org.junit.Test
    public void testRemoveFirst() throws Exception {
        deque.addFirst(3);
        deque.addFirst(2);
        deque.addFirst(1);
        assertEquals(deque.size(), 3);
        int item = deque.removeFirst();
        assertEquals(item, 1);
        assertEquals(deque.size(), 2);
    }

    @org.junit.Test
    public void testRemoveLast() throws Exception {
        deque.addLast(1);
        deque.addLast(2);
        deque.addLast(3);
        assertEquals(deque.size(), 3);
        int item = deque.removeLast();
        assertEquals(item, 3);
        assertEquals(deque.size(), 2);
    }

    @org.junit.Test
    public void testIterator() throws Exception {
        deque.addFirst(3);
        deque.addFirst(2);
        deque.addFirst(1);

        Iterator iterator = deque.iterator();
        assertEquals(deque.iterator().hasNext(), true);

        int i = 1;
        while (iterator.hasNext()) {
            assertEquals(iterator.next(), i);
            i++;
        }
    }
}