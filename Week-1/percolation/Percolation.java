import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int length;
    private int openSites;

    private boolean[] sites;

    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF uf1; // for Checking Fullness

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if(n<1) {
            throw new IllegalArgumentException("Invalid Value");
        }

        length = n;
        int size = (n*n)+2;
        sites = new boolean[size];
        openSites = 0;
        sites[0] = true;

        uf = new WeightedQuickUnionUF(size);
        uf1 = new WeightedQuickUnionUF(size);

        //connect to top and bottom with virtual sites
        for(int i=1;i<=n;i++) {
            int top = 1;
            int index = getIndex(top, i);
            uf.union(0, index);
            uf1.union(0,index);

            int bottom = n;
            index = getIndex(bottom, i);
            uf.union(size-1,index);
        }
    }

    private int getIndex(int row, int col) {
        return ((row-1)*length) + col;
    }

    private void isValid(int row, int col) {
        if (row > length || row < 1)
            throw new IndexOutOfBoundsException("Invalid row index");

        if (col > length || col < 1)
            throw new IndexOutOfBoundsException("Invalid column index");
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        isValid(row,col);
        int index = getIndex(row,col);

        if(sites[index])
            return;

        sites[index] = true;
        openSites++;

        if(col>1 && isOpen(row,col-1)) {
            int leftIndex = getIndex(row,col-1);
            uf.union(index, leftIndex);
            uf1.union(index, leftIndex);
        }

        if(col<length && isOpen(row,col+1)) {
            int rightIndex = getIndex(row,col+1);
            uf.union(index, rightIndex);
            uf1.union(index, rightIndex);
        }

        if(row>1 && isOpen(row-1,col)) {
            int topIndex = getIndex(row-1,col);
            uf.union(index, topIndex);
            uf1.union(index, topIndex);
        }

        if(row<length && isOpen(row+1,col)) {
            int bottomIndex = getIndex(row+1,col);
            uf.union(index, bottomIndex);
            uf1.union(index, bottomIndex);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        isValid(row,col);
        return sites[getIndex(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        int index = getIndex(row,col);
        return(uf1.connected(0, index));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.connected(0, (length*length)+1);
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation percolation = new Percolation(10);

        percolation.isOpen(4,5);
        percolation.open(4,5);
        percolation.open(6,5);
        percolation.open(5,5);
        percolation.open(3,5);
        percolation.open(3,5);
        percolation.open(1,5);
        percolation.isFull(6,5);
        percolation.open(6,5);
        percolation.open(7,5);
        percolation.open(8,5);
        percolation.percolates();
        percolation.open(9,5);
        percolation.open(10,5);
        percolation.percolates();

    }
}