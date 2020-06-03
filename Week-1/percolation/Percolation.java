import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final int length;
    private int openSites;

    private boolean[] sites;

    private final WeightedQuickUnionUF uf;
    private final WeightedQuickUnionUF uf1; // for Checking Fullness

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("Invalid Value");
        }

        length = n;
        int size = (n*n)+2;
        sites = new boolean[size];
        openSites = 0;

        uf = new WeightedQuickUnionUF(size);
        uf1 = new WeightedQuickUnionUF(size-1);
    }

    private int getIndex(int row, int col) {
        return ((row-1)*length) + col;
    }

    private void isValid(int row, int col) {
        if (row > length || row < 1)
            throw new IllegalArgumentException("Invalid row index");

        if (col > length || col < 1)
            throw new IllegalArgumentException("Invalid column index");
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        isValid(row, col);
        int index = getIndex(row, col);

        if (sites[index])
            return;

        sites[index] = true;
        openSites++;

        if (row == 1) {
            uf.union(index, 0);
            uf1.union(index, 0);
        }

        if (row == length)
            uf.union(index, (length*length)+1);

        if (col > 1 && isOpen(row, col-1)) {
            int leftIndex = getIndex(row, col-1);
            uf.union(index, leftIndex);
            uf1.union(index, leftIndex);
        }

        if (col < length && isOpen(row, col+1)) {
            int rightIndex = getIndex(row, col+1);
            uf.union(index, rightIndex);
            uf1.union(index, rightIndex);
        }

        if (row > 1 && isOpen(row-1, col)) {
            int topIndex = getIndex(row-1, col);
            uf.union(index, topIndex);
            uf1.union(index, topIndex);
        }

        if (row < length && isOpen(row+1, col)) {
            int bottomIndex = getIndex(row+1, col);
            uf.union(index, bottomIndex);
            uf1.union(index, bottomIndex);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        isValid(row, col);
        return sites[getIndex(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        isValid(row, col);
        int index = getIndex(row, col);
        boolean ans = uf1.connected(0, index);
        return ans;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        boolean ans = uf.connected(0, (length*length)+1);
        return ans;
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation percolation = new Percolation(10);
    }
}