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

import java.util.Comparator;

/**
 * comments.
 */
public class Point {
    // compare points by slope to this point
    public final Comparator<Point> SLOPE_ORDER = new SlopeOrder();
    int x, y;

    // construct the point (x, y)
    public Point(int x, int y) {
        this.x = x;
        this.y = y;

    }

    // draw this point
    public void draw() {
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.point(x, y);
    }

    // draw the line segment from this point to that point
    public void drawTo(Point that) {
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // string representation
    public String toString() {
        return "(" + x + ", " + y + ")";

    }

    // is this point lexicographically smaller than that point?
    public int compareTo(Point that) {
        return -1;
    }

    // the slope between this point and that point
    public double slopeTo(Point that) {
        if ((this.x == that.x) && (this.y == that.y))
            return Float.NEGATIVE_INFINITY;
        if (that.x - this.x == 0)
            return Float.POSITIVE_INFINITY;
        if (that.y - this.y == 0) {
            return 0.0;
        }
        return (double) (that.y - this.y) / (that.x - this.x);

    }

    private class SlopeOrder implements Comparator<Point> {
        public int compare(Point a, Point b) {
            if (slopeTo(b) > slopeTo(a))
                return -1;
            if (slopeTo(b) < slopeTo(a))
                return 1;
            return 0;
        }
    }

}
