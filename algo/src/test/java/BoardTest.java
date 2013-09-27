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

import java.util.Iterator;

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

    @Test
    public void testNeighbors() {
        int[][] orig = {{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};
        Board left = new Board(new int[][]{{8, 1, 3}, {0, 4, 2}, {7, 6, 5}});
        Board right = new Board(new int[][]{{8, 1, 3}, {4, 2, 0}, {7, 6, 5}});
        Board up = new Board(new int[][]{{8, 0, 3}, {4, 1, 2}, {7, 6, 5}});
        Board down = new Board(new int[][]{{8, 1, 3}, {4, 6, 2}, {7, 0, 5}});

        Board b = new Board(orig);
        Iterator<Board> it = b.neighbors().iterator();
        Board current = it.next();
        assertEquals(left, current);
        assertEquals(right, it.next());
        assertEquals(up, it.next());
        assertEquals(down, it.next());
        assertFalse(it.hasNext());

        b = new Board(perfect);
        Board p_left = new Board(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 0, 8}});
        Board p_up = new Board(new int[][]{{1, 2, 3}, {4, 5, 0}, {7, 8, 6}});

        it = b.neighbors().iterator();
        assertEquals(p_left, it.next());
        assertEquals(p_up, it.next());
        assertFalse(it.hasNext());

        b = new Board(new int[][]{{0, 2, 3}, {4, 5, 6}, {7, 1, 8}});
        Board o_right = new Board(new int[][]{{2, 0, 3}, {4, 5, 6}, {7, 1, 8}});
        Board o_down = new Board(new int[][]{{4, 2, 3}, {0, 5, 6}, {7, 1, 8}});

        it = b.neighbors().iterator();
        assertEquals(o_right, it.next());
        assertEquals(o_down, it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void testTwin() {
        int[][] orig = {{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};
        Board twin = new Board(new int[][]{{1, 8, 3}, {4, 0, 2}, {7, 6, 5}});

        Board b = new Board(orig);
        Board curr = b.twin();
        assertEquals(twin, curr);

        int[][] twin2 = {{1, 8, 0}, {3, 4, 2}, {7, 6, 5}};
        Board o2 = new Board(new int[][]{{1, 8, 0}, {4, 3, 2}, {7, 6, 5}});

        Board b2 = new Board(twin2);
        curr = b2.twin();
        assertEquals(o2, curr);
    }

    @Test
    public void testNeighbors2() {
        int[][] initial = {{0, 1, 3}, {4, 2, 5}, {7, 8, 6}};
        Board right = new Board(new int[][]{{1, 0, 3}, {4, 2, 5}, {7, 8, 6}});
        Board down = new Board(new int[][]{{4, 1, 3}, {0, 2, 5}, {7, 8, 6}});

        Board orig = new Board(initial);
        Iterator<Board> it = orig.neighbors().iterator();
        assertEquals(right, it.next());
        assertEquals(down, it.next());
        assertFalse(it.hasNext());

        Board left2 = new Board(initial);
        Board right2 = new Board(new int[][]{{1, 3, 0}, {4, 2, 5}, {7, 8, 6}});
        Board down2 = new Board(new int[][]{{1, 2, 3}, {4, 0, 5}, {7, 8, 6}});

        // Proceed on from right...
        it = right.neighbors().iterator();
        Board current = it.next();
        assertEquals(left2, current);
        assertEquals(right2, it.next());
        assertEquals(down2, it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void testNeighbors3() {
        int[][] initial = {{1, 0, 3}, {4, 2, 5}, {7, 8, 6}};

        Board left = new Board(new int[][]{{0, 1, 3}, {4, 2, 5}, {7, 8, 6}});
        Board right = new Board(new int[][]{{1, 3, 0}, {4, 2, 5}, {7, 8, 6}});
        Board down = new Board(new int[][]{{1, 2, 3}, {4, 0, 5}, {7, 8, 6}});

        Board orig = new Board(initial);
        Iterator<Board> it = orig.neighbors().iterator();
        Board current = it.next();
        assertEquals(left, current);
        current = it.next();
        assertEquals(right, current);
        current = it.next();
        assertEquals(down, current);
        assertFalse(it.hasNext());

    }

}
