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
 * Date: 12/16/13
 */

import static org.junit.Assert.assertFalse;

/**
 * comments.
 */
public class WordNetTest {
    String synsets = "./testfiles/wordnet/synsets.txt";
    String hypernyms = "./testfiles/wordnet/hypernyms.txt";

    @org.junit.Test
    public void testDefault() {

        WordNet wordNet = new WordNet(synsets, hypernyms);

        assertFalse(wordNet.isNoun("bla1123"));


    }
}
