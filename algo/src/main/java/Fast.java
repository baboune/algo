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
 * PARTICULAR PURPOSE, OR NON-INFRINGEMENT. Baboune SHALL NOT BE LIABLE FOR
 * ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 * DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
 *
 * User: Nicolas
 * Date: 9/19/13
 */


import w3.Point;

import java.util.Arrays;

/**
 * Fast version of the Brute force for finding 3 collinear points.
 *
 * Permutations are excluded ie it does not print permutations of points on a line segment (e.g., p→q→r→s,
 * it does not also output either s→r→q→p or p→r→q→s).
 *
 * The solution is not good enough to eliminate all extra sub-segments but it seems to work..
 * I.e. subsegments of a line segment containing 5 or more points (e.g., if you output p→q→r→s→t,
 * it will also output either p→q→r, and p→q→r→s.
 *
 */
public class Fast {

    // Number of collinear points to look for.
    private static final int MAX_OFFSET = 32768;
    private static final int ADD_ORIG = 1;

    private static void setUpDrawing() {
        StdDraw.setXscale(0, MAX_OFFSET);
        StdDraw.setYscale(0, MAX_OFFSET);
    }

    private static void draw(Point[] ps) {

        Arrays.sort(ps);
        int l = ps.length;
        int last = ps.length - 1;
        for (int s = 0; s < l; s++) {
            StdOut.print(ps[s]);
            if (s < last)
                StdOut.print(" -> ");
        }
        StdOut.println();
        ps[0].drawTo(ps[last]);
    }

    private static Point[] readInputFile(final String filename) {
        In in = new In(filename);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            points[i] = new Point(in.readInt(), in.readInt());
        }
        return points;
    }

    /**
     * Main start.
     * @param args
     */
    public static void main(final String[] args) {
        setUpDrawing();

        Point[] points = readInputFile(args[0]);
        int n = points.length;
        Arrays.sort(points);
        for (Point p : points) {
            p.draw();
        }

        for (int i = 0; i < (n - 2); i++) {
            Point orig = points[i];
            //System.out.println("[" + i + "] Orig: " + orig.toString());

            int next = i + 1;
            Point[] remaining = new Point[n - next];
            for (int j = next; j < n; j++) {
                remaining[j - next] = points[j];
            }

            // Sort by slope
            Arrays.sort(remaining, orig.SLOPE_ORDER);
            //System.out.println("["+ i + "] Ordered by slope: " + Arrays.toString(remaining));

            boolean lastElement = false;
            int segmentCount = 0;
            double lastSlope = orig.slopeTo(remaining[0]);

            for (int k = 1; k < remaining.length; k++) {
                Point currPoint = remaining[k];
                double currentSlope = orig.slopeTo(currPoint);
                lastElement = (k == remaining.length - 1);

                if (currentSlope == lastSlope) {
                    segmentCount++;
                }
                if (currentSlope != lastSlope || lastElement) {
                    // Check for broken slope and/or last element found
                    if (segmentCount >= 2) {
                        // Each segment -> 2 points -> 2 segements => 3 pts
                        int nbFoundPoints = segmentCount + 1;
                        Point[] toDraw = new Point[nbFoundPoints + ADD_ORIG];
                        int pos = 0;
                        toDraw[pos++] = orig;
                        if (lastElement && currentSlope == lastSlope) {
                            // Include current
                            for (int c = segmentCount; c >= 0; c--) {
                                toDraw[pos++] = remaining[k - c];
                            }
                        } else {
                            // Broken slope -> Ignore current
                            for (int c = segmentCount + 1; c > 0; c--) {
                                toDraw[pos++] = remaining[k - c];
                            }
                        }
                        draw(toDraw);
                    }
                    segmentCount = 0;
                    lastSlope = currentSlope;
                }


            }
        }
    }
}
