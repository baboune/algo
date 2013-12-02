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

import org.junit.Test;

import java.io.File;
import java.io.FileReader;
import java.util.Arrays;

/**
 * comments.
 */
public class GaleReiserTest {
    @Test
    public void testFile1() throws Exception {
        long start = System.nanoTime();

        File f = new File("./tomography/File1.txt");
        GaleReiser pl = new GaleReiser(new FileReader(f));
        pl.compute();

        System.out.println("Time: " + ((System.nanoTime() - start) / 1000000));


    }

    @Test
    public void testFile2() throws Exception {
        long start = System.nanoTime();

        File f = new File("./tomography/File2.txt");
        GaleReiser pl = new GaleReiser(new FileReader(f));
        pl.compute();

        System.out.println("Time: " + ((System.nanoTime() - start) / 1000000));


    }

    @Test
    public void testFile3() throws Exception {
        long start = System.nanoTime();

        File f = new File("./tomography/File3.txt");
        GaleReiser pl = new GaleReiser(new FileReader(f));
        pl.compute();

        System.out.println("Time: " + ((System.nanoTime() - start) / 1000000));


    }

    @Test
    public void testComputeRowSum() {
        int[][] mat = new int[][]{{1, 1, 1, 0}, {1, 1, 0, 0}, {1, 0, 0, 0}};

        int[] res = GaleReiser.computeRowSum(mat);
        System.out.println(Arrays.toString(res));
    }
}
