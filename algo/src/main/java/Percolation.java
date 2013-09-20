/**
 *
 * Copyright (c) Baboune, 2013.
 *
 * All Rights Reserved. Reproduction in whole or in part is prohibited
 * without the written consent of the copyright owner.
 *
 * Baboune MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF
 * THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE, OR NON-INFRINGEMENT. Baboune SHALL NOT BE LIABLE FOR
 * ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 * DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
 *
 * User: Baboune
 * Date: 9/3/13
 */


/**
 * comments.
 */
public class Percolation {
    private static final int TOP = 0;
    private static final int BOTTOM = 1;
    private WeightedQuickUnionUF algo;
    private int nMinus1;
    private int n;

    private boolean[][] isOpens;
    private int[][] theSitesMap;

    // create N-by-N grid, with all sites blocked
    public Percolation(int N) {
        n = N;
        nMinus1 = N - 1;
        // N * N plus top and bottom size
        int gridSize = N * N;
        algo = new WeightedQuickUnionUF(gridSize + 2);
        isOpens = new boolean[N][N];
        theSitesMap = new int[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int tot = i * N + j;
                theSitesMap[i][j] = tot + 2;
            }
        }
    }

    // open site (row i, column j) if it is not already
    public void open(int i, int j) {
        validateIJ(i, j);
        if (!isOpen(i, j)) {
            int li = i - 1, lj = j - 1;
            // open it
            isOpens[li][lj] = true;

            // Check the unions -> connect with all open adjacent sites
            if (lj > 0 && lj < nMinus1) {
                unionizeBorder(li, lj);
                if (isOpenWithIJ0(li, lj + 1)) {
                    algo.union(theSitesMap[li][lj], theSitesMap[li][lj + 1]);
                }
                if (isOpenWithIJ0(li, lj - 1)) {
                    algo.union(theSitesMap[li][lj], theSitesMap[li][lj - 1]);
                }
            } else if (lj == nMinus1) {
                // Same except that we connect with TOP on j-1
                unionizeBorder(li, lj);
                algo.union(theSitesMap[li][lj], TOP);
                if (isOpenWithIJ0(li, lj - 1)) {
                    algo.union(theSitesMap[li][lj], theSitesMap[li][lj - 1]);
                }
            } else if (lj == 0) {
                // Same except that we connect with BOTTOM on j+1
                unionizeBorder(li, lj);
                if (isOpenWithIJ0(li, lj + 1)) {
                    algo.union(theSitesMap[li][lj], theSitesMap[li][lj + 1]);
                }
                algo.union(theSitesMap[li][lj], BOTTOM);
            }
        }
    }

    // is site (row i, column j) open?
    public boolean isOpen(int i, int j) {
        validateIJ(i, j);
        return isOpenWithIJ0(i - 1, j - 1);
    }

    // is site (row i, column j) full?
    public boolean isFull(int i, int j) {
        validateIJ(i, j);
        int li = i - 1, lj = j - 1;
        return !isOpens[li][lj] && algo.connected(BOTTOM, theSitesMap[li][lj]);
    }

    // does the system percolate?
    public boolean percolates() {
        return algo.connected(TOP, BOTTOM);
    }

    private void unionizeBorder(int i, int j) {
        if (i < (nMinus1) && isOpenWithIJ0(i + 1, j)) {
            algo.union(theSitesMap[i][j], theSitesMap[i + 1][j]);
        }
        if (i > 0 && isOpenWithIJ0(i - 1, j)) {
            algo.union(theSitesMap[i][j], theSitesMap[i - 1][j]);
        }
    }

    private boolean isOpenWithIJ0(final int i, final int j) {
        return isOpens[i][j];
    }

    private void validateIJ(final int i, int j) {
        if (i < 1 || j < 1) {
            throw new IndexOutOfBoundsException("i and j must be positive integers");
        }
        if (i > n || j > n) {
            throw new IndexOutOfBoundsException("i and j must be less than " + n);
        }
    }
}
