/**
 * Name: Si Wang
 * Date: 01/17/2017
 * Purpose: Percolation
 * Execute: dependency: WeightedQuickUnionUF
 */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] opened;
    private WeightedQuickUnionUF weightedQuickUnionUF;
    private int virtualTop = 0;
    private int virtualBottom;
    private int n;
    private int openSitesCounter = 0;
    
    /**
     * Create n-by-n grid, with all sites blocked
     */
    public Percolation(int n) {
        if (n <= 0) {
            throw new java.lang.IllegalArgumentException("n should be greater than 0.");
        }
        opened = new boolean[n][n];
        weightedQuickUnionUF = new WeightedQuickUnionUF(n * n + 2);
        virtualBottom = n * n + 1;
        this.n = n;
    }
    
    /**
     * open site (row, col) if it is not open already
     */
    public void open(int row, int col) {
        if (row < 1 || col < 1 || row > n || col > n) {
            throw new java.lang.IndexOutOfBoundsException("row & col should be greater than 1");
        }
        
        opened[row - 1][col - 1] = true;
        openSitesCounter++;
        int currentIndex = getIndex(row, col);
        
        if (row == 1) {
            weightedQuickUnionUF.union(virtualTop, currentIndex);
        }
        if (row == n) {
            weightedQuickUnionUF.union(currentIndex, virtualBottom);
        }
        if (col > 1 && isOpen(row, col - 1)) {
            weightedQuickUnionUF.union(currentIndex, getIndex(row, col - 1));
        }
        if (col < n && isOpen(row, col + 1)) {
            weightedQuickUnionUF.union(currentIndex, getIndex(row, col + 1));
        }
        if (row > 1 && isOpen(row - 1, col)) {
            weightedQuickUnionUF.union(currentIndex, getIndex(row - 1, col));
        }
        if (row < n && isOpen(row + 1, col)) {
            weightedQuickUnionUF.union(currentIndex, getIndex(row + 1, col));
        }
    }
    
    /**
     * is site(row, col) open
     */
    public boolean isOpen(int row, int col) {
        if (row < 1 || col < 1 || row > n || col > n) {
            throw new java.lang.IndexOutOfBoundsException("row & col should be greater than 1");
        }
        return opened[row - 1][col - 1];
    }
    
    /**
     * is site(row, col) full? 
     * A full site is an open site that can be connected to an open site in the top row via a chain of neightboring open sites
     */
    public boolean isFull(int row, int col) {
        if (row < 1 || col < 1 || row > n || col > n) {
            throw new java.lang.IndexOutOfBoundsException("row & col should be greater than 1");
        } 
        return weightedQuickUnionUF.connected(getIndex(row, col), virtualTop);
    }
    
    /**
     * number of open sites
     */
    public int numberOfOpenSites() {
        return openSitesCounter;
    }
    
    /**
     * does the system percolate
     */
    public boolean percolates() {
        return weightedQuickUnionUF.connected(virtualTop, virtualBottom);
    }    
    
    /**
     * get the index of sites in WeightedQuickUnionUF
     */
    private int getIndex(int row, int col) {
        return (row - 1) * n + col;
    }        
                
}
