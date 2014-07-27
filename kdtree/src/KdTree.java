public class KdTree {

    private Node root;
    private Point2D minDistancePoint;

    public KdTree() {
        // construct an empty set of points
        root = null;
    }

    public boolean isEmpty() {
        // is the set empty?
        return size() == 0;
    }

    public int size() {
        // number of points in the set
        return size(root);
    }

    private int size(Node x) {
        if (x == null) return 0;
        else return x.N;
    }

    public void insert(Point2D p) {
        // add the point p to the set (if it is not already in the set)
        if (p == null) return;
        if (contains(p)) return;

        root = insert(root, p, (byte) 0);
    }

    private Node insert(Node node, Point2D p, byte level) {
        if (node == null) return new Node(p, new RectHV(0, 0, 1, 1), level, 1);

        int comp = compare(node, p, level);
        if (comp < 0) node.left = insert(node.left, p, (byte) (level + 1));
        else if (comp > 0) node.right = insert(node.right, p, (byte) (level + 1));

        node.N = 1 + size(node.left) + size(node.right);
        return node;
    }

    private int compare(Node node, Point2D p, byte level) {
        int cmp;
        if (level % 2 == 0) {
            cmp = Point2D.X_ORDER.compare(node.point, p);
        } else {
            cmp = Point2D.Y_ORDER.compare(node.point, p);
        }

        if (cmp != 0)
            return cmp;
        else {
            if (level % 2 == 0) {
                cmp = Point2D.Y_ORDER.compare(node.point, p);
            } else {
                cmp = Point2D.X_ORDER.compare(node.point, p);
            }
            return cmp;
        }
    }

    public boolean contains(Point2D p) {
        // does the set contain the point p?
        return search(p, root);
    }

    private boolean search(Point2D point, Node node) {
        if (node == null) return false;
        if (node.point.equals(point)) return true;

        int cmp = compare(node, point, node.level);
        if (cmp > 0)
            return search(point, node.right);
        else if (cmp < 0)
            return search(point, node.left);
        else
            return true;
    }

    public void draw() {
        // draw all of the points to standard draw
        draw(root);
    }

    private void draw(Node node) {
        if (node == null)
            return;
        node.point.draw();
        draw(node.left);
        draw(node.right);
    }

    public Iterable<Point2D> range(RectHV rect) {
        // all points in the set that are inside the rectangle
        return range(root, rect);
    }

    private Iterable<Point2D> range(Node node, RectHV rect) {

        if (node == null) return null;
        Queue<Point2D> points = new Queue<Point2D>();

        if (rect.contains(node.point))
            points.enqueue(node.point);

        if (node.level % 2 == 0) {
            if (rect.xmin() <= node.point.x())
                range(node.left, rect);
            if (rect.xmax() >= node.point.x())
                range(node.right, rect);
        } else {
            if (rect.ymin() <= node.point.y())
                range(node.left, rect);
            if (rect.ymax() >= node.point.y())
                range(node.right, rect);
        }

        return points;
    }

    public Point2D nearest(Point2D p) {
        // a nearest neighbor in the set to p; null if set is empty
        nearest(root, p, 1.0);
        return minDistancePoint;
    }

    private void nearest(Node node, Point2D p, double min) {

        if (node == null) return;
        double distance = node.point.distanceSquaredTo(p);
        if (distance < min) {
            minDistancePoint = node.point;
            min = distance;
            nearest(node.left, p, min);
            nearest(node.right, p, min);
        } else
            nearest(node.left, p, min);
    }

    private static class Node {
        private Point2D point;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node left;        // the left/bottom subtree
        private Node right;        // the right/top subtree
        private byte level;
        private int N;

        private Node(Point2D p, RectHV rect, byte level, int N) {
            this.point = p;
            this.rect = rect;
            this.left = null;
            this.right = null;
            this.level = level;
            this.N = N;
        }
    }
}
