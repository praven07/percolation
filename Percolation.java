/* *****************************************************************************
 *  Name:              Praven Moorthy
 *  Coursera User ID:  3b1d25db3859311c9d8ae7cfdd0aabd1
 *  Last modified:     07/01/2024
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private WeightedQuickUnionUF unionFind;

    private WeightedQuickUnionUF fullSite;

    private boolean[][] sites;

    private int n;

    private int numOfSitesOpen;

    private int topVirtualSite;

    private int bottomVirtualSite;

    public Percolation(int n) {

        if (n < 1) {
            throw new IllegalArgumentException("The provided n must be a positive number.");
        }

        this.n = n;

        this.sites = new boolean[n][n];

        this.unionFind = new WeightedQuickUnionUF(n * n + 2);

        this.fullSite = new WeightedQuickUnionUF(n * n + 1);

        // Last two indexes are virtual sites
        topVirtualSite = unionFind.count() - 2;
        bottomVirtualSite = unionFind.count() - 1;

    }

    public void open(int row, int col) {

        validate(row, col);

        if (isOpen(row, col)) {
            return;
        }

        sites[row - 1][col - 1] = true;
        numOfSitesOpen++;

        int p = indexOf(row, col);

        // Connect to top or bottom virtual site when in top or bottom row.
        if (row - 1 == 0) {
            unionFind.union(p, topVirtualSite);
            fullSite.union(p, topVirtualSite);
        }

        if (row == n) {
            unionFind.union(p, bottomVirtualSite);
        }

        // Left
        if (col - 1 >= 1 && isOpen(row, col - 1)) {
            unionFind.union(p, indexOf(row, col - 1));
            fullSite.union(p, indexOf(row, col - 1));
        }

        // Right
        if (col + 1 <= n && isOpen(row, col + 1)) {
            unionFind.union(p, indexOf(row, col + 1));
            fullSite.union(p, indexOf(row, col + 1));
        }

        // Top
        if (row - 1 >= 1 && isOpen(row - 1, col)) {
            unionFind.union(p, indexOf(row - 1, col));
            fullSite.union(p, indexOf(row - 1, col));
        }

        // Bottom
        if (row + 1 <= n && isOpen(row + 1, col)) {
            unionFind.union(p, indexOf(row + 1, col));
            fullSite.union(p, indexOf(row + 1, col));
        }

    }

    public boolean isOpen(int row, int col) {

        validate(row, col);

        return sites[row - 1][col - 1];
    }

    public boolean isFull(int row, int col) {

        validate(row, col);

        return fullSite.find(indexOf(row, col)) == fullSite.find(topVirtualSite);
    }

    public int numberOfOpenSites() {
        return numOfSitesOpen;
    }

    public boolean percolates() {
        return unionFind.find(topVirtualSite) == unionFind.find(bottomVirtualSite);
    }

    private void validate(int row, int col) {

        if (row < 1 || row > n || col < 1 || col > n) {
            throw new IllegalArgumentException("The provided row and col is out of bounds.");
        }
    }

    private int indexOf(int row, int col) {

        return (row - 1) * n + (col - 1);
    }
}