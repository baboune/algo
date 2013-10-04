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
 * Date: 10/4/13
 */

import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * comments.
 */
public class PointSETTest {
    final Point2D p1 = new Point2D(0.6, 0.5);
    final Point2D p2 = new Point2D(0.1, 0.4);
    final Point2D p3 = new Point2D(0.0, 0.0);
    final Point2D p4 = new Point2D(0.4, 0.3);
    final Point2D p5 = new Point2D(0.8, 0.6);

    @org.junit.Test
    public void testConstruct() {
        PointSET ps = new PointSET();

        assertTrue(ps.isEmpty());

        Point2D p1 = new Point2D(0.7, 0.2);
        Point2D p2 = new Point2D(0.5, 0.4);
        Point2D p3 = new Point2D(0.2, 0.3);
        Point2D p4 = new Point2D(0.4, 0.7);
        Point2D p5 = new Point2D(0.9, 0.6);
        ps.insert(p1);
        ps.insert(p2);
        ps.insert(p3);
        ps.insert(p4);
        ps.insert(p5);

        assertEquals(5, ps.size());
        assertFalse(ps.isEmpty());

        assertTrue(ps.contains(p1));

    }

    @Test
    public void testNearest() {
        PointSET ps = new PointSET();
        ps.insert(p1);
        ps.insert(p2);
        ps.insert(p3);
        ps.insert(p4);


        Point2D nearest = ps.nearest(p5);
        assertEquals(p1, nearest);

        ps.insert(p5);
        nearest = ps.nearest(p5);
        assertEquals(p5, nearest);
    }

    @Test
    public void testRange() {
        PointSET ps = new PointSET();
        Iterable<Point2D> points = ps.range(new RectHV(p2.x(), p2.y(), p5.x(), p5.y()));
        Iterator<Point2D> it = points.iterator();
        assertFalse(it.hasNext());

        insertFivePoints(ps);

        // Draw rect around p1
        points = ps.range(new RectHV(p2.x(), p2.y(), p5.x(), p5.y()));
        it = points.iterator();
        int count = 0;
        boolean foundP1 = false;
        boolean foundP2 = false;
        boolean foundP5 = false;
        boolean foundP3 = false;
        boolean foundP4 = false;

        Point2D curr;
        while (it.hasNext()) {
            count++;
            curr = it.next();
            // There should be only p1 in that rectangle.
            if (p1.equals(curr)) {
                foundP1 = true;
            }
            if (p2.equals(curr)) {
                foundP2 = true;
            }
            if (p3.equals(curr)) {
                foundP3 = true;
            }
            if (p4.equals(curr)) {
                foundP4 = true;
            }
            if (p5.equals(curr)) {
                foundP5 = true;
            }
        }
        assertEquals(3, count);
        assertTrue(foundP1 && foundP2 && foundP5);
        assertFalse(foundP3 && foundP4);
    }


    private void insertFivePoints(PointSET ps) {

        ps.insert(p1);
        ps.insert(p2);
        ps.insert(p3);
        ps.insert(p4);
        ps.insert(p5);
    }
}
