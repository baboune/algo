/**
 *
 * Copyright (c) Ericsson AB, 2013.
 *
 * All Rights Reserved. Reproduction in whole or in part is prohibited
 * without the written consent of the copyright owner.
 *
 * ERICSSON MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF
 * THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE, OR NON-INFRINGEMENT. ERICSSON SHALL NOT BE LIABLE FOR
 * ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 * DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
 *
 * User: lmcnise
 * Date: 9/3/13
 */

import java.util.Arrays;

/**
 * comments.
 */
public class Percolation {
    private static final int TOP = 0;
    private static final int BOTTOM = 1;
    private WeightedQuickUnionUF algo;
    private int nMinus1;

    private int[] sites;
    private boolean[][] isOpens;
    private int[][] theMap;

    // create N-by-N grid, with all sites blocked
    public Percolation(int N) {
        nMinus1 = N - 1;
        // N * N plus top and bottom size
        int gridSize = N * N;
        algo = new WeightedQuickUnionUF(gridSize + 2);
        isOpens = new boolean[N][N];
        sites = new int[gridSize];
        theMap = new int[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int tot = i * N + j;
                sites[tot] = tot + 2;
                theMap[i][j] = tot + 2;
            }
        }
    }

    // open site (row i, column j) if it is not already
    public void open(int i, int j) {
        validateIJ(i, j);
        if (!isOpen(i, j)) {
            // open it
            isOpens[i][j] = true;

            // Check the unions -> connect with all open adjacent sites
            if (j > 0 && j < nMinus1) {
                unionizeBorder(i, j);
                if (isOpen(i, j + 1)) {
                    algo.union(theMap[i][j], theMap[i][j + 1]);
                }
                if (isOpen(i, j - 1)) {
                    algo.union(theMap[i][j], theMap[i][j - 1]);
                }
            } else if (j == nMinus1) {
                // Same except that we connect with TOP on j-1
                unionizeBorder(i, j);
                algo.union(theMap[i][j], TOP);
                if (isOpen(i, j - 1)) {
                    algo.union(theMap[i][j], theMap[i][j - 1]);
                }
            } else if (j == 0) {
                // Same except that we connect with BOTTOM on j+1
                unionizeBorder(i, j);
                if (isOpen(i, j + 1)) {
                    algo.union(theMap[i][j], theMap[i][j + 1]);
                }
                algo.union(theMap[i][j], BOTTOM);
            }
        }
    }

    // is site (row i, column j) open?

    public boolean isOpen(int i, int j) {
        validateIJ(i, j);
        return isOpens[i][j];
    }

    // is site (row i, column j) full?
    public boolean isFull(int i, int j) {
        validateIJ(i, j);
        return !isOpens[i][j];
    }

    // does the system percolate?
    public boolean percolates() {
        return algo.connected(TOP, BOTTOM);
    }

    private void unionizeBorder(int i, int j) {
        if (i < (nMinus1) && isOpen(i + 1, j)) {
            algo.union(theMap[i][j], theMap[i + 1][j]);
        }
        if (i > 0 && isOpen(i - 1, j)) {
            algo.union(theMap[i][j], theMap[i - 1][j]);
        }
    }

    private void validateIJ(final int i, int j) {
        if (i < 0 || j < 0) {
            throw new IllegalArgumentException("i and j must be positive integers");
        }
        if (i > nMinus1 || j > nMinus1) {
            throw new IllegalArgumentException("i and j must be less than N(" + nMinus1 + ")");
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Percolation{");
        sb.append(", sites=").append(Arrays.toString(sites));
        sb.append(", isOpens=");
        for (int j = 0; j <= nMinus1; j++) {
            sb.append("\n\t [ ");
            for(int i = 0; i <= nMinus1; i++)                                      {
                sb.append(theMap[i][j]).append("(").append(isOpens[i][j]).append(") ");
            }
            sb.append("]");
        }
        sb.append('}');
        return sb.toString();
    }
}
