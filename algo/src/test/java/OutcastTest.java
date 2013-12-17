/**
 *
 * Copyright (c) Ericsson AB, 2013.
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
 * User: lmcnise
 * Date: 12/17/13
 */

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * comments.
 */
public class OutcastTest {

    String synsets = "./testfiles/wordnet/synsets.txt";
    String hypernyms = "./testfiles/wordnet/hypernyms.txt";


    @Test
    public void testOutcast5() {
        WordNet wordnet = new WordNet(synsets, hypernyms);
        Outcast outcast = new Outcast(wordnet);
        String[] nouns = new String[]{"horse", "zebra", "cat", "bear", "table"};
        String result = outcast.outcast(nouns);
        assertEquals("table", result);

    }

    @Test
    public void testOutcast8() {
        WordNet wordnet = new WordNet(synsets, hypernyms);
        Outcast outcast = new Outcast(wordnet);
        String[] nouns = new String[]{"water", "soda", "bed", "orange_juice", "milk", "apple_juice", "tea", "coffee"};
        String result = outcast.outcast(nouns);
        assertEquals("bed", result);

    }


    @Test
    public void testOutcast11() {
        WordNet wordnet = new WordNet(synsets, hypernyms);
        Outcast outcast = new Outcast(wordnet);
        String[] nouns = new String[]{"apple", "pear", "peach", "banana", "lime", "lemon", "blueberry", "strawberry",
                "mango", "watermelon", "potato"};
        String result = outcast.outcast(nouns);
        assertEquals("potato", result);

    }
}
