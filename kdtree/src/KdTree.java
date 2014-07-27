public class KdTree {

    private static final boolean VERTICAL = true;
    private Node root;

    public KdTree() {
        // construct an empty set of points
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
        if (p == null)
            return;
        root = insert(root, p, VERTICAL);
    }

    private Node insert(Node node, Point2D point, boolean isVertical) {
        if (node == null) return new Node(point, isVertical, 1);

        int cmp = compare(point, node.point, node.isVertical);
        if (cmp < 0) node.left = insert(node.left, point, !isVertical);
        else if (cmp > 0) node.right = insert(node.right, point, !isVertical);
        else return node;

        node.N = size(node.left) + size(node.right) + 1;

        return node;
    }

    private int compare(Point2D p1, Point2D p2, boolean axis) {
        if (axis == VERTICAL) {
            int cmp = Point2D.X_ORDER.compare(p1, p2);
            if (cmp != 0)
                return cmp;

            return Point2D.Y_ORDER.compare(p1, p2);
        } else {
            int cmp = Point2D.Y_ORDER.compare(p1, p2);
            if (cmp != 0)
                return cmp;
            return Point2D.X_ORDER.compare(p1, p2);
        }
    }

    public boolean contains(Point2D p) {
        // does the set contain the point p?
        if (p == null) return false;
        return contains(root, p);
    }

    // value associated with the given key in subtree rooted at x; null if no such key
    private boolean contains(Node node, Point2D p) {
        while (node != null) {
            int cmp = compare(p, node.point, node.isVertical);
            if (cmp < 0) node = node.left;
            else if (cmp > 0) node = node.right;
            else return true;
        }
        return false;
    }

    public void draw()                              // draw all of the points to standard draw
    {
        draw(root);
    }

    private void draw(Node node) {
        if (node == null) return;
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(.01);

        Point2D p = node.point;
        p.draw();

        StdDraw.setPenRadius();
        if (node.isVertical) {
            StdDraw.setPenColor(StdDraw.BLUE);

            //p.drawTo(new Point2D(node.rect.xmin(), p.y()));
            //p.drawTo(new Point2D(node.rect.xmax(), p.y()));
        } else {
            StdDraw.setPenColor(StdDraw.RED);

            //p.drawTo(new Point2D(p.x(), node.rect.ymin()));
            //p.drawTo(new Point2D(p.x(), node.rect.ymax()));
        }
        draw(node.left);
        draw(node.right);
    }

    public Iterable<Point2D> range(RectHV rect) {
        // all points in the set that are inside the rectangle
        return range(root, rect);
    }

    private Iterable<Point2D> range(Node node, RectHV rect) {
        if (node == null) return null;

        Stack<Point2D> result = new Stack<Point2D>();
        Iterable<Point2D> leftTreePoints = null;
        Iterable<Point2D> rightTreePoints = null;

        if (rect.contains(node.point))
            result.push(node.point);

        if (node.isVertical == VERTICAL) {
            if (node.point.x() >= rect.xmin() && node.point.x() <= rect.xmax()) {
                leftTreePoints = range(node.left, rect);
                rightTreePoints = range(node.right, rect);
            } else if (node.point.x() > rect.xmax()) {
                leftTreePoints = range(node.left, rect);
            } else {
                rightTreePoints = range(node.right, rect);
            }
        } else {
            if (node.point.y() >= rect.ymin() && node.point.y() <= rect.ymax()) {
                leftTreePoints = range(node.left, rect);
                rightTreePoints = range(node.right, rect);
            } else if (node.point.y() > rect.ymax()) {
                leftTreePoints = range(node.left, rect);
            } else {
                rightTreePoints = range(node.right, rect);
            }
        }

        if (leftTreePoints != null) {
            for (Point2D point : leftTreePoints)
                result.push(point);
        }

        if (rightTreePoints != null) {
            for (Point2D point : rightTreePoints)
                result.push(point);
        }

        if (result.size() != 0)
            return result;

        return null;
    }

    public Point2D nearest(Point2D p) {
        // a nearest neighbor in the set to p; null if set is empty
        if (root == null) return null;

        return nearest(root, p, root.point, new RectHV(0.0, 0.0, 1.0, 1.0));
    }

    private Point2D nearest(Node node, Point2D p, Point2D currentNearestPoint, RectHV parentRect) {
        if (node == null)
            return null;

        Point2D point;
        double currentNearestLength = p.distanceTo(currentNearestPoint);
        double minDistance = node.point.distanceTo(p);
        if (minDistance < currentNearestLength) {
            currentNearestLength = minDistance;
            currentNearestPoint = node.point;
        }
        double axisLength;
        RectHV leftRect, rightRect, firstRect, secondRect;
        if (node.isVertical == VERTICAL) {
            axisLength = p.x() - node.point.x();
            leftRect = new RectHV(parentRect.xmin(), parentRect.ymin(), node.point.x(), parentRect.ymax());
            rightRect = new RectHV(node.point.x(), parentRect.ymin(), parentRect.xmax(), parentRect.ymax());
        } else {
            axisLength = p.y() - node.point.y();
            leftRect = new RectHV(parentRect.xmin(), parentRect.ymin(), parentRect.xmax(), node.point.y());
            rightRect = new RectHV(parentRect.xmin(), node.point.y(), parentRect.xmax(), parentRect.ymax());
        }

        Node first, second;
        if (axisLength < 0) {
            first = node.left;
            second = node.right;
            firstRect = leftRect;
            secondRect = rightRect;
        } else {
            first = node.right;
            second = node.left;
            firstRect = rightRect;
            secondRect = leftRect;
        }

        point = nearest(first, p, currentNearestPoint, firstRect);
        if (point != null) {
            minDistance = point.distanceTo(p);
            if (minDistance < currentNearestLength) {
                currentNearestLength = minDistance;
                currentNearestPoint = point;
            }
        }

        if (currentNearestLength > secondRect.distanceTo(p)) {
            point = nearest(second, p, currentNearestPoint, secondRect);
            if (point != null) {
                minDistance = point.distanceTo(p);
                if (minDistance < currentNearestLength) {
                    currentNearestLength = minDistance;
                    currentNearestPoint = point;
                }
            }
        }

        return currentNearestPoint;
    }

    private class Node {
        private Point2D point;
        private boolean isVertical;
        private Node left, right;
        private int N;

        public Node(Point2D point, boolean axis, int N) {
            this.point = point;
            this.isVertical = axis;
            this.N = N;
        }
    }

}