import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE = 1.96;
    private final int t;
    private final double mean;
    private final double stddev;


    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException("n or trials is less then zero");
        t = trials;
        double[] thresholds = new double[trials];

        for (int i = 0; i < trials; i++) {
            Percolation perc = new Percolation(n);
            int threshold = 0;
            while (!perc.percolates()) {
                int row = StdRandom.uniform(n) + 1;
                int col = StdRandom.uniform(n) + 1;
                if (!perc.isOpen(row, col)) {
                    perc.open(row, col);
                    threshold++;
                }
            }
            thresholds[i] = threshold * 1.0 / (n * n);
        }
        mean = StdStats.mean(thresholds);
        stddev = StdStats.stddev(thresholds);

    }

    // sample mean of percolation threshold
    public double mean() {
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return stddev;
    }

    // low  endpoint of 95% CONFIDENCE interval
    public double confidenceLo() {
        return mean() - CONFIDENCE * stddev() / Math.sqrt(t);
    }

    // high endpoint of 95% CONFIDENCE interval
    public double confidenceHi() {
        return mean() + CONFIDENCE * stddev() / Math.sqrt(t);
    }

    public static void main(String[] args) {
        PercolationStats ps = new PercolationStats(200, 300);
        System.out.println("mean = " + ps.mean());
        System.out.println("stddev = " + ps.stddev());
        System.out.println(
                "95% CONFIDENCE interval = " + ps.confidenceLo() + ", " + ps.confidenceHi());
    }
}
