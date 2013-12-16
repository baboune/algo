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

import java.util.HashMap;
import java.util.Map;

/**
 * comments.
 */
public class WordNet {
    private Map<Integer, String> id2Synset = new HashMap<Integer, String>();
    private Map<String, Bag<Integer>> nouns2Ids = new HashMap<String, Bag<Integer>>();
    private final SAP sap;


    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        readSynsets(synsets);
        sap = new SAP(readHypernyms(hypernyms));
    }

    // the set of nouns (no duplicates), returned as an Iterable
    public Iterable<String> nouns() {
        return nouns2Ids.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        return nouns2Ids.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        verifyNoun(nounA);
        verifyNoun(nounB);
        // Map nouns to id + apply sap.length
        return sap.length(nouns2Ids.get(nounA), nouns2Ids.get(nounB));
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        verifyNoun(nounA);
        verifyNoun(nounB);
        // Map nouns to id + apply sap.length
        int ancestor = sap.ancestor(nouns2Ids.get(nounA), nouns2Ids.get(nounB));
        return id2Synset.get(ancestor);
    }

    private void readSynsets(String synsetsFile) {
        In input = new In(synsetsFile);

        while (input.hasNextLine()) {
            String[] tokens = input.readLine().split(",");

            int id = Integer.parseInt(tokens[0]);
            id2Synset.put(id, tokens[1]);
            String[] synset = tokens[1].split(" ");
            for (String noun : synset) {
                Bag<Integer> bag = nouns2Ids.get(noun);
                if (bag == null) {
                    bag = new Bag<Integer>();
                    nouns2Ids.put(noun, bag);
                }
                bag.add(id);
            }
        }
        input.close();
    }

    private void verifyNoun(String noun) {
        if (!isNoun(noun)) {
            throw new IllegalArgumentException();
        }
    }

    private Digraph readHypernyms(String hypernymsFile) {
        Digraph digraph = new Digraph(id2Synset.size());
        In input = new In(hypernymsFile);

        while (input.hasNextLine()) {
            String[] tokens = input.readLine().split(",");
            int id = Integer.parseInt(tokens[0]);
            for (int i = 1, sz = tokens.length; i < sz; i++) {
                digraph.addEdge(id, Integer.parseInt(tokens[i]));
            }
        }

        input.close();

        //verifyCycle(digraph);
        //verifyRoot(digraph);
        return digraph;
    }

    // for unit testing of this class
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            String[] nouns = In.readStrings(args[t]);
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }

    }
}
