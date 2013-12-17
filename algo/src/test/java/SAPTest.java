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
 * Date: 12/15/13
 */

import static org.junit.Assert.assertEquals;

/**
 * comments.
 */
public class SAPTest {
    String digraph1 = "./testfiles/wordnet/digraph1.txt";
    String digraph2 = "./testfiles/wordnet/digraph2.txt";

    private SAP loadDigraph(String fileName) {
        In in = new In(fileName);
        Digraph G = new Digraph(in);
        return new SAP(G);
    }

    @org.junit.Test
    public void testDigraph1() {

        SAP sap = loadDigraph(digraph1);
        assertEquals(4, sap.length(3, 11));
        assertEquals(1, sap.ancestor(3, 11));

        assertEquals(3, sap.length(9, 12));
        assertEquals(5, sap.ancestor(9, 12));

        assertEquals(4, sap.length(7, 2));
        assertEquals(0, sap.ancestor(7, 2));

        assertEquals(-1, sap.ancestor(1, 6));
        assertEquals(-1, sap.length(1, 6));


    }

    @org.junit.Test
    public void testDigraph1SameNode() {
        SAP sap = loadDigraph(digraph1);
        assertEquals(0, sap.length(3, 3));
    }


    @org.junit.Test
    public void testDigraph2() {

        SAP sap = loadDigraph(digraph2);
        assertEquals(2, sap.length(1, 5));
        assertEquals(0, sap.ancestor(1, 5));
    }


    @org.junit.Test(expected = IndexOutOfBoundsException.class)
    public void testAncestorBoundaries() {
        SAP sap = loadDigraph(digraph1);
        sap.ancestor(3, 14);
    }

    @org.junit.Test(expected = IndexOutOfBoundsException.class)
    public void testLengthBoundaries() {
        SAP sap = loadDigraph(digraph1);
        sap.length(3, 14);
    }

    @org.junit.Test
    public void testDigraph2Trial7() {
        SAP sap = loadDigraph(digraph2);
        assertEquals(2, sap.length(1, 3));
    }
}
