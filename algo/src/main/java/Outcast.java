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
    private final WordNet wordnet;

    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        this.wordnet = wordnet;
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        String outcast = null;
        int max = 0;

        for (String noun : nouns) {
            int distance = 0;
            for (String toCompare : nouns) {
                distance += wordnet.distance(noun, toCompare);
            }

            if (distance > max) {
                max = distance;
                outcast = noun;
            }
        }
        return outcast;
    }

    // for unit testing of this class (such as the one below)
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            String[] nouns = In.readStrings(args[t]);
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}
