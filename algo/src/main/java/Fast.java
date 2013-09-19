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
 * Date: 9/19/13
 */

import java.util.Arrays;

/**
 * comments.
 */
public class Fast {

    // Number of collinear points to look for.
    private static final int MIN_POINTS = 4;

    private static void setUpDrawing() {
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
    }

    private static void draw(Point p1, Point p2, Point p3, Point p4) {
        Point[] ps = new Point[]{p1, p2, p3, p4};

        Arrays.sort(ps);
        for (int s = 0; s < 4; s++) {
            StdOut.print(ps[s]);
            if (s < 3)
                StdOut.print(" -> ");
        }
        StdOut.println();
        ps[0].drawTo(ps[3]);
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
        //Point lastPoint = null;
        //System.out.println("Array: " + Arrays.toString(points));
        Arrays.sort(points);
        //System.out.println("SortedArray: " + Arrays.toString(points) + " \n\n");

        for (int i = 0; i < n - MIN_POINTS; i++) {
            Point orig = points[i];

            Point[] remaining = new Point[n - i];
            for (int j = i; j < n; j++) {
                remaining[j - i] = points[j];
            }
            //System.out.println("Remaining[" + i + "]: " + Arrays.toString(remaining));
            Arrays.sort(remaining, orig.SLOPE_ORDER);
            //System.out.println("SortedRemaining[" + i + "]: " + Arrays.toString(remaining));
            int low = 0;
            int high = 0;
            double lastSlope = orig.slopeTo(remaining[0]);

            for (int k = 1; k < remaining.length; k++) {
                double currentSlope = orig.slopeTo(remaining[k]);

                if (currentSlope == lastSlope) {
                    high++;
                } else {
                    int diff = high - low;
                    if (diff >= 2) {
                        //System.out.println("Diff: " + diff);
                        draw(orig, remaining[high], remaining[high - 1], remaining[high - 2]);
                    }
                    low = k;
                    high = k;
                    lastSlope = currentSlope;
                }
            }

            /*if (high - low >= 2 && remaining[high] != lastPoint) {
                lastPoint = remaining[high];
                draw(orig, remaining[high], remaining[high - 1], remaining[high - 2]);
            } */
             /*do {
                // Invariant: We're looking at the subarray that begins right
                // after the last run.
                next = points[i].slopeTo(scratch[stop]);
                // Gallop the stop pointer ri)ghtward to look for a run.
                do {
                    stop += 1;
                    last = next;
                    next = points[i].slopeTo(scratch[stop]);
                } while (stop < n - i - 1 && last != next);
                start = stop - 1;
                while (stop < n - i && last == points[i].slopeTo(scratch[stop]))
                    stop++;
                //if (stop - start + 1 >= MIN_POINTS) // Add one for points[i].
                //    draw(points[i], scratch, start, stop);
            } while (stop < n - i - 1);
            */
        }
    }
}
