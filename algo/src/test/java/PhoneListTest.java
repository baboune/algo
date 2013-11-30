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
 * Date: 11/30/13
 */

import org.junit.Test;

import java.io.File;
import java.io.FileReader;

/**
 * comments.
 */
public class PhoneListTest {

    @Test
    public void testSimple() throws Exception {
        long start = System.nanoTime();
        File f = new File("./phonelist/PL2.txt");
        PhoneList pl = new PhoneList(new FileReader(f));
        pl.compute();

        System.out.println("Time: " + ((System.nanoTime() - start) / 1000000));


    }

}
