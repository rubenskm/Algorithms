public class KdTree {

    public KdTree() {
        // construct an empty set of points
    }

    public boolean isEmpty() {
        // is the set empty?
        //todo
        return true;
    }

    public int size() {
        // number of points in the set
        //todo
        return 0;
    }

    public void insert(Point2D p) {
        // add the point p to the set (if it is not already in the set)
        //todo
    }

    public boolean contains(Point2D p) {
        // does the set contain the point p?
        //todo
        return false;
    }

    public void draw() {
        // draw all of the points to standard draw
        //todo
    }

    public Iterable<Point2D> range(RectHV rect) {
        // all points in the set that are inside the rectangle
        //todo
        return null;
    }

    public Point2D nearest(Point2D p) {
        // a nearest neighbor in the set to p; null if set is empty
        //todo
        return null;
    }

    private static class Node {
        private Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree
    }
}