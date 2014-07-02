public class Percolation {
    private static int virtualTopIndex = 0;
    private boolean[][] grid;
    private int size;
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF ufVirtual;
    private int virtualBottomIndex;

    // create totalLength-by-totalLength grid, with all sites blocked
    public Percolation(int N) {

        if (N <= 0) {
            throw new IllegalArgumentException("N should be > 0");
        }

        grid = new boolean[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                grid[i][j] = false;
            }
        }

        size = N;
        virtualBottomIndex = N * N + 1;
        uf = new WeightedQuickUnionUF(N * N + 2);
        ufVirtual = new WeightedQuickUnionUF(N * N + 2);
    }

    // open site (row i, column j) if it is not already
    public void open(int i, int j) {
        validateInputs(i, j);

        if (!isOpen(i, j)) {
            grid[i - 1][j - 1] = true;
            checkForConnections(i, j);
        }
    }

    // is site (row i, column j) open?
    public boolean isOpen(int i, int j) {
        validateInputs(i, j);
        return grid[i - 1][j - 1];
    }

    // is site (row i, column j) full?
    public boolean isFull(int i, int j) {
        validateInputs(i, j);
        return ufVirtual.connected(virtualTopIndex, xyTo1D(i, j));
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.connected(virtualTopIndex, virtualBottomIndex);
    }

    private void validateInputs(int i, int j) {
        isValidIndex(i);
        isValidIndex(j);
    }

    private void checkForConnections(int i, int j) {
        //left
        if (j > 1) {
            checkAdjacentSites(i, j, i, j - 1);
        }

        //right
        if (j < size) {
            checkAdjacentSites(i, j, i, j + 1);
        }

        //top
        if (i > 1) {
            //up
            checkAdjacentSites(i, j, i - 1, j);
        } else {
            //If it' top connect to virtual site
            uf.union(xyTo1D(i, j), virtualTopIndex);
            ufVirtual.union(xyTo1D(i, j), virtualTopIndex);
        }

        //botton
        if (i < size) {
            //down
            checkAdjacentSites(i, j, i + 1, j);
        } else {
            //If it' botton connect to virtual site
            uf.union(xyTo1D(i, j), virtualBottomIndex);
        }
    }

    private void checkAdjacentSites(int p1X, int p1Y, int p2X, int p2Y) {
        if (isOpen(p2X, p2Y)) {
            uf.union(xyTo1D(p1X, p1Y), xyTo1D(p2X, p2Y));
            ufVirtual.union(xyTo1D(p1X, p1Y), xyTo1D(p2X, p2Y));
        }
    }

    //scheme for uniquely mapping 2D coordinates to 1D coordinates
    private int xyTo1D(int x, int y) {
        return (x - 1) * size + y;
    }

    private void isValidIndex(int i) {
        if (i < 1 || i > size) {
            throw new IndexOutOfBoundsException("row index i out of bounds");
        }
    }
}
