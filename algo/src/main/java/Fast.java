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


import java.util.Arrays;

/**
 * Fast version of the Brute force for finding 3 collinear points.
 */
public class Fast {

    // Number of collinear points to look for.
    private static final int MIN_POINTS = 3;

    private static void setUpDrawing() {
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
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

    private static Point[] readInputFile(String filename) {
        In in = new In(filename);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            points[i] = new Point(in.readInt(), in.readInt());
        }
        return points;
    }

    public static void main(String[] args) {
        setUpDrawing();

        Point[] points = readInputFile(args[0]);
        int n = points.length;
        Arrays.sort(points);

        for (int i = 0; i < n - MIN_POINTS; i++) {
            Point orig = points[i];

            Point[] remaining = new Point[n - i];
            for (int j = i; j < n; j++) {
                remaining[j - i] = points[j];
            }

            //System.out.println("Remaining[" + i + "]: " + Arrays.toString(remaining));
            Arrays.sort(remaining, orig.SLOPE_ORDER);
            //System.out.println("SortedRemaining[" + i + "]: " + Arrays.toString(remaining));

            boolean lastElement = false;
            int collinearCount = 0;
            double lastSlope = orig.slopeTo(remaining[0]);
            //System.out.println("slope[" + i + "] vs orig: " + lastSlope);

            for (int k = 1; k < remaining.length; k++) {
                double currentSlope = orig.slopeTo(remaining[k]);
                lastElement = (k == remaining.length - 1);

                if (currentSlope == lastSlope && !lastElement) {
                    collinearCount++;
                } else {
                    // Check for broken slope and/or last element found
                    if (collinearCount >= 2) {
                        Point[] toDraw = new Point[collinearCount + 1];
                        toDraw[0] = orig;
                        if (lastElement) {
                            for (int c = 0; c < collinearCount; c++) {
                                toDraw[c + 1] = remaining[k - c];
                            }
                        } else {
                            // Broken slope
                            for (int c = 0; c < collinearCount; c++) {
                                toDraw[c + 1] = remaining[(k - 1) - c];
                            }
                        }
                        draw(toDraw);
                    }
                    collinearCount = 0;
                    lastSlope = currentSlope;
                }
            }
        }
    }
}
