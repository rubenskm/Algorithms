import java.util.Arrays;

public class Fast {

    public static void main(String[] args) {

        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);

        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();

        Point[] points = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
            points[i].draw();
        }
        Arrays.sort(points);
        /*
        A faster, sorting-based solution. Remarkably, it is possible to solve the problem much faster than the brute-force
         solution described above. Given a point p, the following method determines whether p participates in a set of 4 or
         more collinear points.

        1)Think of p as the origin.
        2)For each other point q, determine the slope it makes with p.
        3)Sort the points according to the slopes they makes with p.
        4)Check if any 3 (or more) adjacent points in the sorted order have equal slopes with respect to p.
        If so, these points, together with p, are collinear.

        Applying this method for each of the N points in turn yields an efficient algorithm to the problem.
         The algorithm solves the problem because points that have equal slopes with respect to p are collinear,
         and sorting brings such points together. The algorithm is fast because the bottleneck operation is sorting.
        */

        //Point[] pointsCopy = points.clone();
        for (int i = 0; i < N - 3; i++) {

            Point p = points[i];
            Arrays.sort(points, i + 1, N, p.SLOPE_ORDER);
            int count = 1;
            double prevSlope = Double.NaN;
            int j;
            for (j = i + 1; j < N; j++) {
                double currSlope = p.slopeTo(points[j]);
                if (currSlope == prevSlope) {
                    count++;
                } else {
                    findCollinear(points, i, p, count, j);
                    count = 1;
                    prevSlope = currSlope;
                }
            }
            findCollinear(points, i, p, count, j);
            Arrays.sort(points, i + 1, N);
        }

        StdDraw.show(0);
    }

    private static void findCollinear(Point[] points, int i, Point p, int count, int j) {
        if (count >= 3) {
            if (unique(points, i, p, points[j - 1])) {
                printPoints(p, points, j - count, j);
                p.drawTo(points[j - 1]);
            }
        }
    }

    private static boolean unique(Point[] points, int prev, Point a, Point b) {
        for (int i = 0; i < prev; i++) {
            if (a.SLOPE_ORDER.compare(b, points[i]) == 0)
                return false;
        }
        return true;
    }

    private static void printPoints(Point p, Point[] points, int lo, int hi) {
        System.out.print(p);
        for (int i = lo; i < hi; i++) {
            System.out.print(" -> " + points[i]);
        }
        System.out.println("");
    }
}