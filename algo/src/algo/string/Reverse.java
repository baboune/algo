/**
 * Copyright (c) Nicolas Seyvet, 2010.
 *
 * All Rights Reserved. Reproduction in whole or in part is prohibited
 * without the written consent of the copyright owner.
 *
 * Nicolas Seyvet MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF
 * THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE, OR NON-INFRINGEMENT. ERICSSON SHALL NOT BE LIABLE FOR
 * ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 * DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
 *
 * User: Baboune
 * Date: 14/01/12
 * Time: 10:53
 */
package algo.string;

/**
 *
 */
public class Reverse {

    public static String reverse(String val) {
        if(val == null) {
            return null;
        }
        if(val.length() == 1) {
            return val;
        }
        return reverse(val.substring(1)) + val.charAt(0);

    }


    static public void main(String... args) {
        String s = "john";

        String res = reverse(s);
        System.out.println(res);
        if("nhoj".equals(res)) {
            System.out.println("yeah!");
        } else {
            System.out.println("booh!");
        }

    }
}
