/*************************************************************************
 * Name:
 * Email:
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import java.util.Comparator;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new SlopeOrder();

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // unit test
    public static void main(String[] args) {
        /* YOUR CODE HERE */
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
        /*
        The slopeTo() method should return the slope between the invoking point (x0, y0) and the argument point (x1, y1),
         which is given by the formula (y1 − y0) / (x1 − x0).
         Treat the slope of a horizontal line segment as positive zero; treat the slope of a vertical line segment
         as positive infinity; treat the slope of a degenerate line segment (between a point and itself) as negative infinity.
         */

        double result;

        if (x == that.x && y == that.y) {
            result = Double.NEGATIVE_INFINITY;
        } else if (x == that.x) {
            result = Double.POSITIVE_INFINITY;
        } else if (y == that.y) {
            result = 0;
        } else {
            result = ((double) that.y - (double) y) / ((double) that.x - (double) x);
        }

        return result;
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        /*
        The compareTo() method should compare points by their y-coordinates, breaking ties by their x-coordinates.
        Formally, the invoking point (x0, y0) is less than the argument point (x1, y1) if and only if either
         y0 < y1 or if y0 = y1 and x0 < x1.
        */
        int result;

        if (this.y == that.y && this.x == that.x) {
            result = 0;
        } else if (this.y < that.y || (this.y == that.y && this.x < that.x)) {
            result = -1;
        } else {
            result = 1;
        }

        return result;
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    private class SlopeOrder implements Comparator<Point> {

        @Override
        public int compare(Point p1, Point p2) {

            /*
            The SLOPE_ORDER comparator should compare points by the slopes they make with the invoking point (x0, y0).
            Formally, the point (x1, y1) is less than the point (x2, y2) if and only if the slope (y1 − y0) / (x1 − x0) is less
            than the slope (y2 − y0) / (x2 − x0).
             */
            int result;

            double p1Slope = slopeTo(p1);
            double p2Slope = slopeTo(p2);

            if (p1Slope < p2Slope)
                result = -1;
            else if (p1Slope > p2Slope)
                result = 1;
            else
                result = 0;

            return result;
        }
    }
}
