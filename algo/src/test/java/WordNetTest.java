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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
        assertTrue(wordNet.isNoun("municipality"));
        assertTrue(wordNet.isNoun("region"));
    }

    /**
     * The synsets individual and edible_fruit have several different paths to their common ancestor physical_entity.
     * individual -> organism being -> living_thing animate_thing -> whole unit -> object physical_object -> physical_entity
     * person individual someone somebody mortal soul -> causal_agent cause causal_agency -> physical_entity
     * edible_fruit -> garden_truck -> food solid_food -> solid -> matter -> physical_entity
     * edible_fruit -> fruit -> reproductive_structure -> plant_organ -> plant_part -> natural_object -> unit -> object -> physical_entity
     */
    @org.junit.Test
    public void testAncestors() {

        WordNet wordNet = new WordNet(synsets, hypernyms);

        String ancestor = wordNet.sap("individual", "edible_fruit");
        assertEquals("physical_entity", ancestor);

    }

    /**
     * 33 = distance("Black_Plague", "black_marlin")
     * 27 = distance ("American_water_spaniel", "histology")
     * 29 = distance("Brown_Swiss", "barrel_roll")
     */
    @org.junit.Test
    public void testDistance() {

        WordNet wordNet = new WordNet(synsets, hypernyms);

        int distance = wordNet.distance("Black_Plague", "black_marlin");
        assertEquals(33, distance);

        distance = wordNet.distance("American_water_spaniel", "histology");
        assertEquals(27, distance);

        distance = wordNet.distance("Brown_Swiss", "barrel_roll");
        assertEquals(29, distance);

    }

    @org.junit.Test(expected = IllegalArgumentException.class)
    public void testInvalidNoun() {
        WordNet wordNet = new WordNet(synsets, hypernyms);

        wordNet.distance("bla123", "bla234");
    }
}
