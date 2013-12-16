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

/**
 * Outcast detection. Given a list of wordnet nouns A1, A2, ..., An, which noun is the least related to the others?
 * To identify an outcast, compute the sum of the distances between each noun and every other one:
 *
 * di   =   dist(Ai, A1)   +   dist(Ai, A2)   +   ...   +   dist(Ai, An)
 *
 * and return a noun At for which dt is maximum. .
 */
public class Outcast {
    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {

    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        return null;
    }

    // for unit testing of this class (such as the one below)
    public static void main(String[] args) {

    }
}
