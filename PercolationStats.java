/* *****************************************************************************
 *  Name:              Praven Moorthy
 *  Coursera User ID:  3b1d25db3859311c9d8ae7cfdd0aabd1
 *  Last modified:     07/01/2024
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private static final double CONFIDENCE_95 = 1.96;

    private int n;

    private int trials;

    private double[] results;

    public PercolationStats(int n, int trials) {

        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException(
                    "The provided n and trails must be positive numbers greater than 0");
        }

        this.n = n;
        this.trials = trials;

        this.results = new double[trials];

        simulate();
    }

    private void simulate() {

        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);

            while (!percolation.percolates()) {
                int row = StdRandom.uniformInt(1, n + 1);
                int col = StdRandom.uniformInt(1, n + 1);

                percolation.open(row, col);
            }

            results[i] = (double) percolation.numberOfOpenSites() / (n * n);
        }
    }

    public double mean() {

        return StdStats.mean(results);
    }

    public double stddev() {
        return StdStats.stddev(results);
    }

    public double confidenceLo() {
        return mean() - ((CONFIDENCE_95 * stddev()) / Math.sqrt(trials));
    }

    public double confidenceHi() {
        return mean() + ((CONFIDENCE_95 * stddev()) / Math.sqrt(trials));
    }

    public static void main(String[] args) {

        if (args.length != 2) {
            StdOut.println("No arguments provided.");
        }

        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats stats = new PercolationStats(n, trials);
        StdOut.println("mean = " + stats.mean());
        StdOut.println("stddev = " + stats.stddev());
        StdOut.println("95% confidence interval = " + stats.confidenceLo());
    }
}
