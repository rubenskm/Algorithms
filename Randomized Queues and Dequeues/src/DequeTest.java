import org.junit.Assert;

import java.util.Iterator;

public class DequeTest {

    private Deque<Integer> deque;

    @org.junit.Before
    public void setUp() throws Exception {
        deque = new Deque<Integer>();
    }

    @org.junit.After
    public void tearDown() throws Exception {

    }

    @org.junit.Test
    public void testIsEmpty() throws Exception {
        Assert.assertTrue(deque.isEmpty());
    }

    @org.junit.Test
    public void testSize() throws Exception {
        Assert.assertEquals(deque.size(), 0);
    }

    @org.junit.Test
    public void testAddFirst() throws Exception {
        deque.addFirst(1);
        Assert.assertEquals(deque.size(), 1);
    }

    @org.junit.Test
    public void testAddLast() throws Exception {
        deque.addLast(1);
        Assert.assertEquals(deque.size(), 1);
    }

    @org.junit.Test
    public void testRemoveFirst() throws Exception {
        deque.addFirst(3);
        deque.addFirst(2);
        deque.addFirst(1);
        Assert.assertEquals(deque.size(), 3);
        int item = deque.removeFirst();
        Assert.assertEquals(item, 1);
        Assert.assertEquals(deque.size(), 2);
    }

    @org.junit.Test
    public void testRemoveLast() throws Exception {
        deque.addLast(1);
        deque.addLast(2);
        deque.addLast(3);
        Assert.assertEquals(deque.size(), 3);
        int item = deque.removeLast();
        Assert.assertEquals(item, 3);
        Assert.assertEquals(deque.size(), 2);
    }

    @org.junit.Test
    public void testAddFirstRemoveLast() throws Exception {
        deque.addFirst(1);
        deque.addFirst(2);
        deque.addFirst(3);
        Assert.assertEquals((int) deque.removeLast(), 1);
        Assert.assertEquals((int) deque.removeLast(), 2);
        Assert.assertEquals((int) deque.removeLast(), 3);
    }

    @org.junit.Test
    public void testIteratorSequence() throws Exception {
        deque.addFirst(1);
        deque.removeFirst();
        deque.addFirst(1);
        deque.removeLast();
        deque.addLast(2);
        deque.removeFirst();

        deque.addFirst(1);
        deque.addFirst(2);
        deque.addFirst(3);
        deque.addFirst(4);

        Iterator iterator = deque.iterator();
        int x = 0;
        while (iterator.hasNext()) {
            x = (Integer) iterator.next();
        }
    }


    public void testIteratorSequence2() throws Exception {
        deque.addFirst(1);
        deque.addFirst(2);
        deque.addFirst(3);
        deque.addFirst(4);
        deque.addLast(0);

        int item = deque.removeFirst();
        deque.addFirst(item);

        item = deque.removeLast();
        deque.addLast(item);

        Iterator iterator = deque.iterator();
        int i = 4;
        while (iterator.hasNext()) {
            Assert.assertEquals(iterator.next(), i);
            i--;
        }

        Assert.assertEquals(iterator.hasNext(), false);
    }

    @org.junit.Test
    public void testIterator() throws Exception {
        deque.addFirst(3);
        deque.addFirst(2);
        deque.addFirst(1);

        Iterator iterator = deque.iterator();
        Assert.assertEquals(iterator.hasNext(), true);

        int i = 1;
        while (iterator.hasNext()) {
            Assert.assertEquals(iterator.next(), i);
            i++;
        }
    }
}