import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final boolean[] grid;
    private final int number;
    private final int topIndex;
    private final int bottomIndex;
    private int openSites;
    private final WeightedQuickUnionUF uf;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("number " + n + "is less then zero");
        number = n;
        topIndex = n * n;
        bottomIndex = n * n + 1;
        grid = new boolean[n * n];
        uf = new WeightedQuickUnionUF(n * n + 2);
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        isInRange(row, col);
        if (isOpen(row, col)) return;
        int index = calculateIndex(row, col);
        unionWithNeighborns(index, row, col);
        grid[index] = true;
        openSites++;
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        isInRange(row, col);
        int index = calculateIndex(row, col);
        return grid[index];
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        isInRange(row, col);
        int index = calculateIndex(row, col);
        if (isOpen(row, col) && uf.connected(index, topIndex)) return true;
        return false;
    }

    // number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        for (int col = 1; col <= number; col++) {
            if (isFull(number, col)) return true;
        }
        return false;
    }

    private int calculateIndex(int row, int col) {
        return (row - 1) * number + (col - 1);
    }

    private void unionWithNeighborns(int index, int row, int col) {
        if (row == 1 && !uf.connected(index, topIndex)) {
            uf.union(index, topIndex);
        }
        if (row == number && isFull(row, col) && !uf.connected(index, bottomIndex)) {

            uf.union(index, bottomIndex);
        }
        if (row - 1 > 0 && isOpen(row - 1, col)) {
            unionIfNotConnected(index, row - 1, col);
        }
        if (row + 1 <= number && isOpen(row + 1, col)) {
            unionIfNotConnected(index, row + 1, col);
        }
        if (col - 1 > 0 && isOpen(row, col - 1)) {
            unionIfNotConnected(index, row, col - 1);
        }
        if (col + 1 <= number && isOpen(row, col + 1)) {
            unionIfNotConnected(index, row, col + 1);
        }
    }

    private void unionIfNotConnected(int index, int row, int col) {
        int neighbornIndex = calculateIndex(row, col);
        if (!uf.connected(index, neighbornIndex)) {
            uf.union(index, neighbornIndex);
        }
    }

    private void isInRange(int row, int col) {
        if (row <= 0 || row > number) {
            throw new IllegalArgumentException(
                    "row " + row + " is not between 1 and " + number);
        }
        if (col <= 0 || col > number) {
            throw new IllegalArgumentException(
                    "col " + col + " is not between 1 and " + number);
        }
    }
}
