public class PointSET {

    private final SET<Point2D> set;

    public PointSET() {
        // construct an empty set of points
        set = new SET<Point2D>();
    }

    public boolean isEmpty() {
        // is the set empty?
        return set.isEmpty();
    }

    public int size() {
        // number of points in the set
        return set.size();
    }

    public void insert(Point2D p) {
        // add the point p to the set (if it is not already in the set)
        set.add(p);
    }

    public boolean contains(Point2D p) {
        // does the set contain the point p?
        return set.contains(p);
    }

    public void draw() {
        // draw all of the points to standard draw
        for (Point2D point : set) {
            point.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        // all points in the set that are inside the rectangle
        Queue<Point2D> points = new Queue<Point2D>();
        for (Point2D point : set) {
            if (rect.contains(point))
                points.enqueue(point);
        }
        return points;
    }

    public Point2D nearest(Point2D p) {
        // a nearest neighbor in the set to p; null if set is empty
        double minLength = 1.0;
        Point2D minDistancePoint = null;

        for (Point2D point : set) {
            final double distance = point.distanceSquaredTo(p);
            if (distance < minLength) {
                minLength = distance;
                minDistancePoint = point;
            }
        }

        return minDistancePoint;
    }
}