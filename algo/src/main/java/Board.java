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
 * Date: 9/26/13
 */


/**
 * A square board of N x N elements.
 */
public class Board {

    private static final int UNDEF = -1;
    private final int[][] blocks;
    private final int n;

    // construct a board from an N-by-N array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        if (blocks == null) {
            throw new NullPointerException();
        }
        n = blocks.length;
        this.blocks = copySquareArray(blocks);

    }


    // board dimension N
    public int dimension() {
        return n;
    }

    // number of blocks out of place
    public int hamming() {
        int count = 0;
        int total = n * n;
        for (int i = 0; i < total; i++) {
            if (blocks[i / n][i % n] != (i + 1)) {
                count++;
            }
        }
        // Subtract 1 for the empty block.
        return count - 1;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int sum = 0;
        int elem;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                elem = blocks[i][j];
                if (elem == 0) {
                    // skip
                    continue;
                }
                int iExpected = ((elem - 1) / n);
                int jExpected = (elem - 1) % n;
                sum += Math.abs(i - iExpected) + Math.abs(j - jExpected);
            }
        }
        return sum;
    }

    // is this board the goal board?

    public boolean isGoal() {
        if (blocks[n - 1][n - 1] != 0)
            return false;
        // Remove 0
        int total = n * n;
        int max = (total - 1);
        for (int i = 0; i < max; i++) {
            if (blocks[i / n][i % n] != (i + 1)) {
                return false;
            }
        }
        return true;
    }

    // a board obtained by exchanging two adjacent blocks in the same row
    public Board twin() {
        int zeroRow = UNDEF;
        for (int j = 0; j < n && zeroRow == -1; j++)
            // Look if 0 is on first row
            for (int i = 0; (i < n) && zeroRow == -1; i++) {
                if (blocks[j][i] == 0) {
                    zeroRow = j;
                }
            }
        if (zeroRow == -1) {
            throw new UnsupportedOperationException("Expected to find a 0.");
        }
        int rowToUse = 0;
        // Avoid it if this row contains a 0
        if (zeroRow == 0) {
            rowToUse = 1;
        }
        // swap [row][0] with [row][1]
        return new Board(swap(rowToUse, 0, rowToUse, 1));
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        // This is obtained by sliding the 0...
        Queue<Board> q = new Queue<Board>();
        int iZero = -1, jZero = -1;
        for (int i = 0; i < n && (iZero == -1); i++) {
            for (int j = 0; j < n; j++) {
                if (blocks[i][j] == 0) {
                    iZero = i;
                    jZero = j;
                    break;
                }
            }
        }
        if (iZero == -1 || jZero == -1) {
            return q;
        }
        if (jZero > 0) {
            //neighbor to left allowed
            Board b = new Board(swap(iZero, jZero, iZero, jZero - 1));
            q.enqueue(b);
        }
        if (jZero < n - 1) {
            //neighbor to right allowed
            Board b = new Board(swap(iZero, jZero, iZero, jZero + 1));
            q.enqueue(b);
        }
        if (iZero > 0) {
            // Up neighbor
            Board b = new Board(swap(iZero, jZero, iZero - 1, jZero));
            q.enqueue(b);
        }
        if (iZero < n - 1) {
            // Down neighbor
            Board b = new Board(swap(iZero, jZero, iZero + 1, jZero));
            q.enqueue(b);
        }
        return q;
    }

    // string representation of the board (in the output format specified below)
    public String toString() {
        StringBuilder s = new StringBuilder(100);
        s.append(n).append("\n");
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                s.append(String.format("%2d ", blocks[row][col]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Board that = (Board) o;

        if (n != that.n) return false;
        // compare arrays
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (blocks[i][j] != that.blocks[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    private int[][] swap(int i, int j, int toi, int toj) {
        int[][] copy = copySquareArray(blocks);
        int tmp = copy[toi][toj];
        copy[toi][toj] = copy[i][j];
        copy[i][j] = tmp;
        return copy;
    }

    private String printBlocks(int[][] what) {
        StringBuilder s = new StringBuilder(100);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", what[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    private int[][] copySquareArray(int[][] what) {
        int l = what.length;
        int[][] copy = new int[l][l];
        for (int i = 0; i < l; i++) {
            System.arraycopy(what[i], 0, copy[i], 0, l);
        }
        return copy;
    }

}
