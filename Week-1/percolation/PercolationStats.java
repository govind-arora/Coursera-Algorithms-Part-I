import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private double[] results;
    private int length;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if(n<1 || trials<1)
            throw new IllegalArgumentException("Invalid Values");

        results = new double[trials];
        length = trials;

        for(int i=0;i<trials;i++) {
            Percolation percolation = new Percolation(n);

            while (!percolation.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);

                percolation.open(row, col);
            }

            results[i] = (double) percolation.numberOfOpenSites() / (n*n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(results);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(results);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (1.96*stddev()/Math.sqrt(length));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (1.96*stddev()/Math.sqrt(length));
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats percolationStats = new PercolationStats(n, trials);
    }

}