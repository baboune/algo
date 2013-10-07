/**
 *
 * Copyright (c) Ericsson AB, 2011.
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
 * User: Nicolas
 * Date: 10/5/13
 */

import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * comments.
 */
public class KdTreeTest {
    private static final int VERTICAL = 1;
    private static final int HORIZONTAL = 2;

    Point2D p1 = new Point2D(0.7, 0.1);
    Point2D p2 = new Point2D(0.5, 0.4);
    Point2D p3 = new Point2D(0.9, 0.6);
    Point2D p4 = new Point2D(0.2, 0.3);
    Point2D p5 = new Point2D(0.4, 0.7);

    @Test
    public void testConstructor() {
        KdTree tree = new KdTree();
        assertTrue(tree.isEmpty());

        tree.insert(p1);
        assertFalse(tree.isEmpty());

        tree.insert(p2);
        tree.insert(p3);
        assertFalse(tree.contains(p4));
        tree.insert(p4);
        assertTrue(tree.contains(p4));
        tree.insert(p5);

        assertEquals(5, tree.size());
    }

    @Test
    public void testNodeGetRect() {
        Point2D p1 = new Point2D(0.7, 0.1);
        KdTree.Node parent = new KdTree.Node(p1, 1, VERTICAL, new RectHV(0, 0, 1, 1));
        Point2D p2 = new Point2D(0.5, 0.4);
        RectHV r = parent.getChildRect(p2);
        assertTrue(1.0 - r.ymax() < 0.0001);
        assertTrue(0.0 - r.ymin() < 0.0001);
        assertTrue(0.7 - r.xmax() < 0.0001);
        assertTrue(0.0 - r.xmin() < 0.0001);

        r = parent.getChildRect(p3);
        assertTrue(1.0 - r.ymax() < 0.0001);
        assertTrue(0.0 - r.ymin() < 0.0001);
        assertTrue(1.0 - r.xmax() < 0.0001);
        assertTrue(0.7 - r.xmin() < 0.0001);

        parent = new KdTree.Node(p2, 1, HORIZONTAL, new RectHV(0, 0, 0.7, 1));
        r = parent.getChildRect(p4);
        assertTrue(0.4 - r.ymax() < 0.0001);
        assertTrue(0.0 - r.ymin() < 0.0001);
        assertTrue(0.7 - r.xmax() < 0.0001);
        assertTrue(0.0 - r.xmin() < 0.0001);
    }

    @Test
    public void testNearest() {
        final Point2D p1 = new Point2D(0.6, 0.5);
        final Point2D p2 = new Point2D(0.1, 0.4);
        final Point2D p3 = new Point2D(0.0, 0.0);
        final Point2D p4 = new Point2D(0.4, 0.3);
        final Point2D p5 = new Point2D(0.8, 0.6);

        KdTree tree = new KdTree();
        tree.insert(p1);
        tree.insert(p2);
        tree.insert(p3);
        tree.insert(p4);

        Point2D nearest = tree.nearest(p5);
        assertEquals(p1, nearest);

        tree.insert(p5);
        nearest = tree.nearest(p5);
        assertEquals(p5, nearest);
    }

    @Test
    public void testRange() {
        final Point2D p1 = new Point2D(0.6, 0.5);
        final Point2D p2 = new Point2D(0.1, 0.4);
        final Point2D p3 = new Point2D(0.0, 0.0);
        final Point2D p4 = new Point2D(0.4, 0.3);
        final Point2D p5 = new Point2D(0.8, 0.6);

        KdTree tree = new KdTree();
        Iterable<Point2D> points = tree.range(new RectHV(p2.x(), p2.y(), p5.x(), p5.y()));
        Iterator<Point2D> it = points.iterator();
        assertFalse(it.hasNext());
        tree.insert(p1);
        tree.insert(p2);
        tree.insert(p3);
        tree.insert(p4);
        tree.insert(p5);


        // Draw rect around p1
        points = tree.range(new RectHV(p2.x(), p2.y(), p5.x(), p5.y()));
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

    @Test
    public void testCircle10() {
        final Point2D p1 = new Point2D(0.206107, 0.095492);
        final Point2D p2 = new Point2D(0.975528, 0.654508);
        final Point2D p3 = new Point2D(0.024472, 0.345492);
        final Point2D p4 = new Point2D(0.793893, 0.095492);
        final Point2D p5 = new Point2D(0.793893, 0.904508);
        final Point2D p6 = new Point2D(0.975528, 0.345492);
        final Point2D p7 = new Point2D(0.206107, 0.904508);
        final Point2D p8 = new Point2D(0.500000, 0.000000);
        final Point2D p9 = new Point2D(0.024472, 0.654508);
        final Point2D p10 = new Point2D(0.500000, 1.000000);

        KdTree tree = new KdTree();
        tree.insert(p1);
        assertEquals(1, tree.size());
        tree.insert(p2);
        assertEquals(2, tree.size());
        tree.insert(p3);
        assertEquals(3, tree.size());
        tree.insert(p4);
        assertEquals(4, tree.size());
        tree.insert(p5);
        assertEquals(5, tree.size());
        tree.insert(p6);
        assertEquals(6, tree.size());
        tree.insert(p7);
        assertEquals(7, tree.size());
        tree.insert(p8);
        assertEquals(8, tree.size());
        tree.insert(p9);
        assertEquals(9, tree.size());
        tree.insert(p10);

        assertEquals(10, tree.size());

        //  nearest() is called with p = (.81, .30) -> 5
        Point2D test = new Point2D(0.81, 0.30);
        Point2D nearest = tree.nearest(test);
        assertEquals(p6, nearest);

        // testing nearest() with (0.206107, 0.095492)
        test = new Point2D(0.206107, 0.095492);
        nearest = tree.nearest(test);
        assertEquals(p1, nearest);

        Iterable<Point2D> iters = tree.range(new RectHV(0.0, 0.0, 0.81, 0.3));
        Iterator<Point2D> it = iters.iterator();
        int count = 0;
        while (it.hasNext()) {
            Point2D p = it.next();
            assertNotNull(p);
            count++;
        }
        assertEquals(3, count);
    }
}
