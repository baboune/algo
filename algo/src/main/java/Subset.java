/**
 *
 * Copyright (c) Baboune AB, 2013.
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
 * User: Baboune
 * Date: 9/12/13
 */


import w2.RandomizedQueue;

/**
 * comments.
 */
public class Subset {

    public static void main(String[] args) {
        if (args == null || args.length != 1) {
            System.out.println("One argument only");
            System.exit(1);
        }
        int k = Integer.valueOf(args[0]);

        RandomizedQueue<String> rq = new RandomizedQueue<String>();

        System.out.println("Enter value:");
        for (int i = 0; i < 10; i++) {
            rq.enqueue(StdIn.readString());
        }

        for (int i = 0; i < k; i++) {
            System.out.println(rq.dequeue());
        }


    }
}
