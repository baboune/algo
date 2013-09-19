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
 * Date: 9/19/13
 */



import org.junit.Test;

import java.util.Arrays;

/**
 * comments.
 */
public class PointTest {

    @Test
    public void testSimple() {
        Point origin = new Point(0, 0);
        Point[] pts = new Point[]{
                new Point(1, 1),
                new Point(1, 0),
                new Point(1, -1),
                new Point(0, 1),
                origin,
                new Point(0, -1),
                new Point(-1, 1),
                new Point(-1, 0),
                new Point(-1, -1)
        };
        Arrays.sort(pts, origin.SLOPE_ORDER);
        for (int i = 0; i < pts.length; ++i) {
            StdOut.println(pts[i] + ": " + origin.slopeTo(pts[i]));
        }
        Point p1 = new Point(216, 352);
        Point p2 = new Point(169, 311);
        StdOut.println(p1.slopeTo(p2));
        StdOut.println(p2.slopeTo(p1));
    }
}
