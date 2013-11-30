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
package phonelist;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * comments.
 */
public class PhoneListGenerator {
    public static void main(String[] args) throws IOException {
        int numOfTc = 40;
        int lengthOfTc = 10000;
        BufferedWriter bw = new BufferedWriter(new FileWriter("PL2.txt"));
        bw.write(Integer.toString(numOfTc));
        bw.newLine();
        for (int i = 0; i < numOfTc; i++) {
            bw.write(Integer.toString(lengthOfTc));
            bw.newLine();
            for (int j = 0; j < lengthOfTc; j++) {
                long range = 9999999999L;
                Random r = new Random();
                long number = (long) (r.nextDouble() * range);
                bw.write(Long.toString(number));
                bw.newLine();
            }
        }
        bw.flush();
        bw.close();
    }
}
