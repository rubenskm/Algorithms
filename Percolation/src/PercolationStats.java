public class PercolationStats {
    private int times;
    private double[] x;

    // perform T independent computational experiments on an N-by-N grid
    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("N or T should be > 0");
        }

        //Monte Carlo simulation.
        times = T;
        x = new double[T];

        for (int i = 0; i < times; i++) {
            int openedSites = 0;
            //Initialize all sites to be blocked.
            Percolation percolation = new Percolation(N);

            /*
            Repeat the following until the system percolates:
                Choose a site (row i, column j) uniformly at random among all blocked sites.
                Open the site (row i, column j).
            */
            while (!percolation.percolates()) {

                int randomI = StdRandom.uniform(N) + 1;
                int randomJ = StdRandom.uniform(N) + 1;

                if (!percolation.isOpen(randomI, randomJ)) {
                    openedSites++;
                    percolation.open(randomI, randomJ);
                }
            }

            //The fraction of sites that are opened when the system percolates provides an estimate of the percolation threshold.
            x[i] = (double) openedSites / (N * N);
        }
    }

    public static void main(String[] args) {

        int nArg = Integer.parseInt(args[0]);
        int tArg = Integer.parseInt(args[1]);

        PercolationStats percolationStats = new PercolationStats(nArg, tArg);
        System.out.println(String.format("mean                    = %f", percolationStats.mean()));
        System.out.println(String.format("stddev                  = %f", percolationStats.stddev()));
        System.out.println(String.format("95%% confidence interval = %f, %f", percolationStats.confidenceLo(), percolationStats.confidenceHi()));
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(x);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(x);
    }

    // returns lower bound of the 95% confidence interval
    public double confidenceLo() {
        return StdStats.mean(x) - (1.96 * StdStats.stddev(x))
                / Math.sqrt(times);
    }

    // returns upper bound of the 95% confidence interval
    public double confidenceHi() {
        return StdStats.mean(x) - (1.96 * StdStats.stddev(x))
                / Math.sqrt(times);
    }
}
