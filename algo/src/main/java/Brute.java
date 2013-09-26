/**
 *
 * Copyright (c) Baboune AB, 2011.
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
 * comments.
 */
public class Brute {

    private static final int MAX_OFFSET = 32768;

    private static void setUpDrawing() {
        StdDraw.setXscale(0, MAX_OFFSET);
        StdDraw.setYscale(0, MAX_OFFSET);
    }

    private static boolean collinear(final Point[] p) {
        if (p.length != 4) {
            throw new UnsupportedOperationException("4 points only");
        }
        double s1 = p[0].slopeTo(p[1]);
        return (s1 == p[0].slopeTo(p[2])
                && s1 == p[0].slopeTo(p[3]));
    }

    public static void main(final String[] args) {
        setUpDrawing();

        int i, j, k, l;
        In input = new In(args[0]);
        int pointer = 0;
        int N = input.readInt();
        Point[] arr = new Point[N];

        while (!input.isEmpty()) {
            arr[pointer] = new Point(input.readInt(), input.readInt());
            arr[pointer].draw();
            pointer++;
        }
        Point[] points = new Point[4];
        for (i = 0; i < N - 3; i += 1) {
            for (j = i + 1; j < N - 2; j += 1) {
                for (k = j + 1; k < N - 1; k += 1) {
                    for (l = k + 1; l < N; l += 1) {
                        points[0] = arr[i];
                        points[1] = arr[j];
                        points[2] = arr[k];
                        points[3] = arr[l];
                        //check if these 4 point have the same slope
                        if (collinear(points)) {
                            Arrays.sort(points);
                            for (int s = 0; s < 4; s++) {
                                StdOut.print(points[s]);
                                if (s < 3)
                                    StdOut.print(" -> ");
                            }
                            StdOut.println();
                            points[0].drawTo(points[3]);
                        }
                    }
                }
            }

        }
    }
}
