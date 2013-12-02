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
 * Date: 12/1/13
 */

import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

/**
 * comments.
 */
public class Tomography {


    Scanner input;

    public Tomography() {
        input = new Scanner(System.in);
    }

    public Tomography(InputStreamReader is) {
        input = new Scanner(is);
    }

    public void compute() {
        int m = input.nextInt();
        int n = input.nextInt();

        // r -> row sum
        Integer r[] = new Integer[m];
        for (int i = 0; i < m; i++) {
            r[i] = input.nextInt();
        }
        // c -> column sums
        Integer c[] = new Integer[n];
        for (int i = 0; i < n; i++) {
            c[i] = input.nextInt();
        }

        // sort r and c
        Arrays.sort(r, Collections.reverseOrder());
        Arrays.sort(c, Collections.reverseOrder());

        // Full matrix of r
        int p[][] = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (j < r[i]) {
                    // Put 1
                    p[i][j] = 1;
                } else {
                    p[i][j] = 0;
                }
            }
        }
        print(p);
        // transpose it  -> compute new sums
        int pt[][] = transposeMatrix(p);

        print(pt);

        int pt_col_sum[] = computeRowSum(pt);

        // Compute sum of pt
        int pt_sum[] = computeSum(pt_col_sum);
        //System.out.println("pt_sum: " + Arrays.toString(pt_sum));
        int c_sum[] = computeSum(c);
        //System.out.println("pt_sum: " + Arrays.toString(pt_sum)", c_sum: " + Arrays.toString(c_sum));


        // Compare to q
        boolean dominates = true;
        for (int i = 0; i < pt_sum.length; i++) {
            if (pt_sum[i] < c_sum[i]) {
                dominates = false;
                break;
            }
        }
        if (dominates) {
            System.out.println("YES");
        } else {
            System.out.println("NO");
        }


    }

    public static int[][] transposeMatrix(int[][] m) {
        int[][] temp = new int[m[0].length][m.length];
        for (int i = 0; i < m.length; i++)
            for (int j = 0; j < m[0].length; j++)
                temp[j][i] = m[i][j];
        return temp;
    }

    public static int[] computeRowSum(int[][] mat) {
        int rows = mat.length;
        int cols = mat[0].length;
        int[] res = new int[rows];
        // initialize array
        for (int i = 0; i < rows; i++) {
            res[i] = 0;
        }
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                res[i] += mat[i][j];
            }
        }
        return res;
    }

    private static int[] computeSum(Integer[] m) {
        int l = m.length;
        int pt_sum[] = new int[m.length];
        pt_sum[0] = m[0];
        for (int i = 1; i < l; i++) {
            pt_sum[i] = pt_sum[i - 1] + m[i];
        }
        return pt_sum;
    }

    private static int[] computeSum(int[] m) {
        int l = m.length;
        int pt_sum[] = new int[m.length];
        pt_sum[0] = m[0];
        for (int i = 1; i < l; i++) {
            pt_sum[i] = pt_sum[i - 1] + m[i];
        }
        return pt_sum;
    }

    private static void print(int[][] p) {
        int rows = p.length;
        int cols = p[0].length;
        System.out.print("P[][]=\n\t[");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(p[i][j]);
                System.out.print(" ");
            }
            if (i != rows - 1)
                System.out.print("]\n\t[");
            else
                System.out.println("]");

        }
    }

    public static void main(String... args) {
        GaleReiser galeReiser = new GaleReiser();
        galeReiser.compute();


    }
}
