import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PercolationTest
{
    Percolation perc;

    @Before
    public void setUp() throws Exception
    {
        perc = new Percolation(3);
    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void testOpen() throws Exception
    {
        perc.open(1,1);
        perc.open(1,2);
        Assert.assertTrue(perc.isOpen(1,1));
        Assert.assertTrue(perc.isOpen(1,2));
    }

    @Test
    public void testIsOpen() throws Exception
    {
        Assert.assertFalse(perc.isOpen(1,1));
        perc.open(1,1);
        Assert.assertTrue(perc.isOpen(1,1));
    }

    @Test
    public void testIsFull() throws Exception
    {
        perc.open(1,1);
        perc.open(2,1);
        Assert.assertTrue(perc.isFull(2,1));
    }

    @Test
    public void testPercolates() throws Exception
    {
        perc.open(1,1);
        perc.open(2,1);
        perc.open(3,1);
        Assert.assertTrue(perc.percolates());
    }
}