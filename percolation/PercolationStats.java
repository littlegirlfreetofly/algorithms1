/**
 * Name: Si Wang
 * Date: 01/17/2017
 * Purpose: Percolation Monte Carlo simulation
 * Execute: dependency: StdRandom, StdStats
 */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] threshold;
    private int n;
    private int trials;
    
    public PercolationStats(int n, int trials) {
        // Perform trials independent experiments on an n-by-n grid
        if (n <= 0 || trials <= 0) {
            throw new java.lang.IllegalArgumentException("n & trials should be greater than 0.");
        }
        this.n = n;
        this.trials = trials;
        threshold = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);
            int openSites = 0;
            while (!percolation.percolates()) {
                openRandomSites(percolation);
                openSites++;
            }
            threshold[i] = (double) openSites / (n * n);
        }    
    }
    
    public double mean() {
        // sample mean of percolation threshold
        return StdStats.mean(threshold);
    }
    
    public double stddev() {
        // sample standard deviation of percolation threshold
        return StdStats.stddev(threshold);
    }
    
    public double confidenceLo() {
        // low  endpoint of 95% confidence interval
        return mean() - (1.96 * stddev()) / Math.sqrt(trials);
    }
    
    public double confidenceHi() {
        // high endpoint of 95% confidence interval
        return mean() + (1.96 * stddev()) / Math.sqrt(trials);
    }
    
    private void openRandomSites(Percolation percolation) {
        boolean isOpen = true;
        int randomRow = 0;
        int randomColumn = 0;
        while (isOpen) {
            randomRow = StdRandom.uniform(1, n + 1);
            randomColumn = StdRandom.uniform(1, n + 1);
            isOpen = percolation.isOpen(randomRow, randomColumn);
        }
        percolation.open(randomRow, randomColumn);
    }
    
    public static void main(String[] args) {
        // test client (described below)
        int n = new Integer(args[0]);
        int t = new Integer(args[1]);
         
        PercolationStats stats = new PercolationStats(n, t);
        
        System.out.println("mean                       = " + stats.mean());
        System.out.println("stddev                     = " + stats.stddev());
        System.out.println("95% confidence interval    = " + stats.confidenceLo() + ", " + stats.confidenceHi());
    }
}


            
            
    
    
    
    
    