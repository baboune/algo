package w2; /**
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
 * Date: 9/11/13
 */

/**
 * comments.
 */
public class MainDeque {

    public static void main(String... args) {
        Deque<String> ds = new Deque<String>();
        ds.addFirst("1");
        ds.removeFirst();
        System.out.println("empty: " + ds.isEmpty());

        ds.addLast("2");
        ds.removeLast();
        System.out.println("empty: " + ds.isEmpty());

        ds.addFirst("3");
        ds.removeLast();
        System.out.println("empty: " + ds.isEmpty());

        ds.addLast("4");
        ds.removeFirst();
        System.out.println("empty: " + ds.isEmpty());

        // Add many
        ds.addFirst("1");
        for(String s: ds){
            System.out.print(s + "  ");
        }

        System.out.println("\nAdding 2 and 3 as first");
        ds.addFirst("2");
        ds.addFirst("3");
        for(String s: ds){
            System.out.print(s + "  ");
        }

        System.out.println("\nRemove first");
        ds.removeFirst();
        for(String s: ds){
            System.out.print(s + "  ");
        }
        System.out.println("\nAdding 4, 5 as last");
        ds.addLast("4");
        ds.addLast("5");
        for(String s: ds){
            System.out.print(s + "  ");
        }
        System.out.println("\nRemove last");
        ds.removeLast();
        for(String s: ds){
            System.out.print(s + "  ");
        }

        System.out.println("\nRemove first");
        ds.removeFirst();
        for(String s: ds){
            System.out.print(s + "  ");
        }
    }
}
