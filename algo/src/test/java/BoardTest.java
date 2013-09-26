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

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * comments.
 */
public class BoardTest {
    int[][] perfect = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};

    @Test
    public void testConstructor() {
        int[][] orig = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        Board b = new Board(orig);
        assertEquals(3, b.dimension());
    }

    @Test
    public void testHamming() {
        int[][] orig = {{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};
        Board b = new Board(orig);
        assertEquals(5, b.hamming());
    }

    @Test
    public void testManahattan() {
        int[][] orig = {{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};
        Board b = new Board(orig);
        assertEquals(10, b.manhattan());
    }

    @Test
    public void testIsGoal() {
        int[][] orig = {{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};
        Board b = new Board(orig);
        assertFalse(b.isGoal());

        b = new Board(perfect);
        assertTrue(b.isGoal());
    }
}
