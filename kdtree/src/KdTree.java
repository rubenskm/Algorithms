public class KdTree {

    private Node root;

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
        if (contains(p)) {
            int i = 0;
            return;
        }

        root = insert(root, p, true, new RectHV(0, 0, 1, 1));

    }

    private Node insert(Node node, Point2D p, boolean isVertical, RectHV rect) {
        if (node == null) return new Node(p, rect, isVertical, 1);

        if (compare(node, p) < 0) {
            if (node.isVertical) {
                rect = new RectHV(node.rect.xmin(), node.rect.ymin(), node.point.x(), node.rect.ymax());
            } else {
                rect = new RectHV(node.rect.xmin(), node.rect.ymin(), node.rect.xmax(), node.point.y());
            }
            node.left = insert(node.left, p, !node.isVertical, rect);
        } else {
            if (node.isVertical) {
                rect = new RectHV(node.point.x(), node.rect.ymin(), node.rect.xmax(), node.rect.ymax());
            } else {
                rect = new RectHV(node.rect.xmin(), node.point.y(), node.rect.xmax(), node.rect.ymax());
            }

            node.right = insert(node.right, p, !node.isVertical, rect);
        }

        node.N = 1 + size(node.left) + size(node.right);
        return node;
    }

    private int compare(Node node, Point2D p) {
        if (node.isVertical) {
            return Point2D.X_ORDER.compare(p, node.point);
        } else {
            return Point2D.Y_ORDER.compare(p, node.point);
        }
    }

    public boolean contains(Point2D p) {
        // does the set contain the point p?
        return search(p, root);
    }

    private boolean search(Point2D point, Node node) {
        if (node == null) return false;
        if (node.point.equals(point)) return true;

        int cmp = compare(node, point);
        if (cmp < 0)
            return search(point, node.left);
        else
            return search(point, node.right);
    }

    public void draw() {
        // draw all of the points to standard draw
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

            p.drawTo(new Point2D(p.x(), node.rect.ymin()));
            p.drawTo(new Point2D(p.x(), node.rect.ymax()));
        } else {
            StdDraw.setPenColor(StdDraw.RED);

            p.drawTo(new Point2D(node.rect.xmin(), p.y()));
            p.drawTo(new Point2D(node.rect.xmax(), p.y()));
        }
        draw(node.left);
        draw(node.right);
    }

    public Iterable<Point2D> range(RectHV rect) {
        // all points in the set that are inside the rectangle
        Queue<Point2D> points = new Queue<Point2D>();
        return range(root, rect, points);
    }

    private Iterable<Point2D> range(Node node, RectHV rect, Queue<Point2D> points) {
        /*
        To find all points contained in a given query rectangle, start at the root and recursively search for points
        in both subtrees using the following pruning rule: if the query rectangle does not intersect the rectangle
        corresponding to a node, there is no need to explore that node (or its subtrees).
        A subtree is searched only if it might contain a point contained in the query rectangle.
         */
        if (node == null) return null;

        if (rect.intersects(node.rect)) {
            if (rect.contains(node.point))
                points.enqueue(node.point);

            if (node.isVertical) {
                if (rect.xmin() <= node.point.x())
                    range(node.left, rect, points);
                if (rect.xmax() >= node.point.x())
                    range(node.right, rect, points);
            } else {
                if (rect.ymin() <= node.point.y())
                    range(node.left, rect, points);
                if (rect.ymax() >= node.point.y())
                    range(node.right, rect, points);
            }
        }

        return points;
    }

    public Point2D nearest(Point2D p) {
        // a nearest neighbor in the set to p; null if set is empty
        return nearest(root, p, root.point.distanceSquaredTo(p), root.point);
    }

    private Point2D nearest(Node node, Point2D p, double min, Point2D nearestPoint) {
        /*
        Nearest neighbor search.
        To find a closest point to a given query point, start at the root and recursively search in both subtrees
        using the following pruning rule:
        if the closest point discovered so far is closer than the distance between the query point and the rectangle
        corresponding to a node, there is no need to explore that node (or its subtrees). That is, a node is searched only if it might contain a point that is
        closer than the best one found so far. The effectiveness of the pruning rule depends on quickly finding a
        nearby point.
        To do this, organize your recursive method so that when there are two possible subtrees to go down, you always
         choose the subtree that is on the same side of the splitting line as the query point as the first subtree to
         explore—the closest point found while exploring the first subtree may enable pruning of the second subtree.
         */

        if (node == null) return nearestPoint;
        double distance = node.point.distanceSquaredTo(p);
        if (distance < min) {
            nearestPoint = node.point;
            min = distance;
        }

        double left = 1;
        double right = 1;

        if (node.left != null)
            left = node.left.point.distanceSquaredTo(p);

        if (node.right != null)
            right = node.right.point.distanceSquaredTo(p);

        if (right < left)
            return nearest(node.right, p, min, nearestPoint);
        else
            return nearest(node.left, p, min, nearestPoint);
    }

    private static class Node {
        private Point2D point;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node left;        // the left/bottom subtree
        private Node right;        // the right/top subtree
        private boolean isVertical;
        private int N;

        private Node(Point2D p, RectHV rect, boolean isVertical, int N) {
            this.point = p;
            this.rect = rect;
            this.left = null;
            this.right = null;
            this.isVertical = isVertical;
            this.N = N;
        }
    }
}
