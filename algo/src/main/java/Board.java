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
 * comments.
 */
public class Board {

    private final int[][] blocks;
    private final int n;
    private int total;

    // construct a board from an N-by-N array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        if (blocks == null) {
            throw new NullPointerException();
        }
        n = blocks.length;
        this.blocks = new int[n][n];
        System.arraycopy(blocks, 0, this.blocks, 0, n);
        total = n * n;
    }


    // Copy a square array.
    private int[][] copySquareArray(int[][] original) {
        int len = original.length;
        int[][] copy = new int[len][len];
        for (int row = 0; row < len; row++) {
            assert original[row].length == len;
            for (int col = 0; col < len; col++)
                copy[row][col] = (short) original[row][col];
        }
        return copy;
    }

    // board dimension N
    public int dimension() {
        return n;
    }

    // number of blocks out of place
    public int hamming() {
        int count = 0;
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
                int exp_i = ((elem - 1) / n);
                int exp_j = (elem - 1) % n;
                sum += Math.abs(i - exp_i) + Math.abs(j - exp_j);
            }
        }
        return sum;
    }

    // is this board the goal board?

    public boolean isGoal() {
        if (blocks[n - 1][n - 1] != 0)
            return false;
        // Remove 0
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
        return null;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        return null;
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

}
