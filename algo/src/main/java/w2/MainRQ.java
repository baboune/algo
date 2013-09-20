package w2; /**
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
 * Date: 9/11/13
 */

/**
 * comments.
 */
public class MainRQ {

    public static void main(final String... args) {
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        System.out.println("empty: " + rq.isEmpty());

        for (int i = 0; i < 10; i++) {
            rq.enqueue(String.valueOf(i));
        }
        for (String s : rq) {
            System.out.print(s + "  ");
        }

        for (int i = 0; i < 10; i++) {
            rq.dequeue();
            System.out.println("\ni: " + i);
            for (String s : rq) {
                System.out.print(s + "  ");
            }
        }
        System.out.println("\nempty: " + rq.isEmpty());
    }
}
