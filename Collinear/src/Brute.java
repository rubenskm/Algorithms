import java.util.Arrays;

public class Brute {
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

        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                for (int k = j + 1; k < N; k++) {
                    for (int l = k + 1; l < N; l++) {
                        if (i != j && j != k && k != l) {
                            double slopeij = points[i].slopeTo(points[j]);
                            double slopeik = points[i].slopeTo(points[k]);
                            double slopeil = points[i].slopeTo(points[l]);
                            if (slopeij == slopeik && slopeik == slopeil) {
                                System.out.println(points[i] + " -> " + points[j]
                                        + " -> " + points[k] + " -> " + points[l]);
                                points[i].drawTo(points[l]);
                            }
                        }
                    }
                }
            }
        }

        StdDraw.show(0);
    }
}
