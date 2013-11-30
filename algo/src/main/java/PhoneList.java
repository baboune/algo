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


import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;

/**
 * comments.
 */
public class PhoneList {
    Scanner keyboard;

    public PhoneList() {
        keyboard = new Scanner(System.in);
    }

    public PhoneList(InputStreamReader is) {
        keyboard = new Scanner(is);
    }

    public void compute() {
        String[] phoneList;

        // Read TC
        int nbOfTc = Integer.valueOf(keyboard.nextLine());

        for (int i = 0; i < nbOfTc; i++) {
            int nbOfRows = Integer.valueOf(keyboard.nextLine());
            phoneList = new String[nbOfRows];
            for (int j = 0; j < nbOfRows; j++) {
                phoneList[j] = keyboard.nextLine();
            }

            // order
            Arrays.sort(phoneList);
            //System.out.println("Ordered List: " + Arrays.toString(phoneList));
            boolean hasPrefix = false;
            for (int j = 0; j < nbOfRows - 1 && !hasPrefix; j++) {
                // Check if prefix to next
                if (phoneList[j + 1].startsWith(phoneList[j])) {
                    hasPrefix = true;
                    //System.out.println("row: " + j);
                }
            }
            if (hasPrefix) {
                System.out.println("NO");
            } else {
                System.out.println("YES");
            }
        }
    }

    public static void main(String... args) {
        PhoneList pl = new PhoneList();
        pl.compute();

    }
}
