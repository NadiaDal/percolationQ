/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[] grid;
    private int number;
    private int openSites;
    private WeightedQuickUnionUF uf;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        number = n;
        grid = new boolean[n * n];
        uf = new WeightedQuickUnionUF(n * n);
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        if (isOpen(row, col)) return;
        int index = calculateIndex(row, col);
        grid[index] = true;
        openSites++;
        // uf.union(index, neighborn);
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        int index = calculateIndex(row, col);
        return grid[index];
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        int index = calculateIndex(row, col);
        for (int i = 0; i < number; i++) {
            if (uf.connected(index, i)) return true;
        }
        return false;
    }

    // number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return false;
    }

    private int calculateIndex(int row, int col) {
        return (row - 1) * number + (col - 1);
    }

    public static void main(String[] args) {

    }
}
