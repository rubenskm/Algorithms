import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;

public class RandomizedQueueTest {

    private RandomizedQueue<Integer> randomizedQueue;

    @Before
    public void setUp() throws Exception {
        randomizedQueue = new RandomizedQueue<Integer>();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testIsEmpty() throws Exception {
        Assert.assertTrue(randomizedQueue.isEmpty());
    }

    @Test
    public void testSize() throws Exception {

        Assert.assertEquals(randomizedQueue.size(), 0);
    }

    @Test
    public void testEnqueue() throws Exception {

        randomizedQueue.enqueue(1);
        Assert.assertFalse(randomizedQueue.isEmpty());
        Assert.assertEquals(randomizedQueue.size(), 1);
    }

    @Test
    public void testDequeue() throws Exception {

        randomizedQueue.enqueue(1);
        randomizedQueue.dequeue();
        Assert.assertTrue(randomizedQueue.isEmpty());
        Assert.assertEquals(randomizedQueue.size(), 0);
    }

    @Test
    public void testSequence() throws Exception {

        randomizedQueue.enqueue(5);
        randomizedQueue.enqueue(6);
        randomizedQueue.dequeue();
        randomizedQueue.enqueue(7);
        randomizedQueue.enqueue(8);
        randomizedQueue.dequeue();
        Assert.assertEquals(randomizedQueue.size(), 2);
    }

    @Test
    public void testSample() throws Exception {

        randomizedQueue.enqueue(1);
        int value = randomizedQueue.dequeue();
        Assert.assertEquals(value, 1);
    }

    @Test
    public void testIterator() throws Exception {

        randomizedQueue.enqueue(1);
        randomizedQueue.enqueue(2);
        randomizedQueue.enqueue(3);
        Assert.assertEquals(randomizedQueue.iterator().hasNext(), true);
        int iterations = 0;
        for (Iterator<Integer> iter = randomizedQueue.iterator(); iter.hasNext(); ) {
            iter.next();
            iterations++;
        }
        Assert.assertEquals(iterations, 3);
    }

    @Test
    public void testDifferentIterator() throws Exception {

        randomizedQueue = new RandomizedQueue<Integer>();
        for (int i = 0; i < 1000; i++) {
            randomizedQueue.enqueue(i);
        }

        for (Iterator<Integer> first = randomizedQueue.iterator(); first.hasNext(); ) {
            for (Iterator<Integer> second = randomizedQueue.iterator(); second.hasNext(); ) {
                int one = first.next();
                int sec = second.next();
                Assert.assertNotEquals(first.next(), second.next());
            }
        }
    }
}
