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
 * PARTICULAR PURPOSE, OR NON-INFRINGEMENT. ERICSSON SHALL NOT BE LIABLE FOR
 * ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 * DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
 *
 * User: Baboune
 * Date: 10/4/13
 */

import java.util.TreeSet;

/**
 *  Write a mutable data type PointSET.java that represents a set of points in the unit square.
 */
public class PointSET {
    private TreeSet<Point2D> set = new TreeSet<Point2D>();

    // construct an empty set of points
    public PointSET() {

    }

    // is the set empty?
    public boolean isEmpty() {
        return set.isEmpty();
    }

    // number of points in the set
    public int size() {
        return set.size();
    }

    // add the point p to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) {
            throw new NullPointerException("Expected a point.");
        }
        if (!set.contains(p)) {
            // add
            set.add(p);
        }
    }

    // does the set contain the point p?
    public boolean contains(Point2D p) {
        return set.contains(p);
    }

    // draw all of the points to standard draw
    public void draw() {
        for (Point2D p : set) {
            p.draw();
        }
    }

    // all points in the set that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        Stack<Point2D> stack = new Stack<Point2D>();

        for (Point2D p : set) {
            if (rect.contains(p)) {
                stack.push(p);
            }
        }

        return stack;
    }

    // a nearest neighbor in the set to p; null if set is empty
    public Point2D nearest(Point2D p) {
        if (set.isEmpty()) {
            return null;
        }

        Point2D nearest = null;
        double dist = Double.MAX_VALUE;
        double temp;

        for (Point2D point : set) {
            temp = point.distanceSquaredTo(p);
            if (temp < dist) {
                nearest = point;
                dist = temp;
            }
        }
        return nearest;
    }

}
