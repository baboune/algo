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


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Shortest Ancestral Path.
 */
public class SAP {
    private final Digraph G;
    private final int nbVertices;
    private final List<Integer> roots = new ArrayList<Integer>();
    private final Map<Integer, BreadthFirstDirectedPaths> bfdps = new HashMap<Integer, BreadthFirstDirectedPaths>();

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        this.G = G;
        this.nbVertices = this.G.V();

        // , the root node cannot have any incoming edges and the other nodes can only have one incoming edge
        // For optimizations compute the root (s) outside and only once.
        for (int v = 0; v < G.V(); v++) {
            if (!G.adj(v).iterator().hasNext()) {
                roots.add(v);
                //System.out.println("Found possible root: " + v);
            }
        }
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        if (v == w) {
            return 1;
        }
        return length(Arrays.asList(v), Arrays.asList(w));
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        if (v == w) {
            return v;
        }
        return ancestor(Arrays.asList(v), Arrays.asList(w));
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        checkBoundaries(v, "v");
        checkBoundaries(w, "w");

        BreadthFirstDirectedPaths bfdpV = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths bfdpW = new BreadthFirstDirectedPaths(G, w);

        // Reuse known roots
        for (int root : roots) {
            if (bfdpV.hasPathTo(root) && bfdpW.hasPathTo(root)) {
                Iterable<Integer> pathToV = bfdpV.pathTo(root);
                for (int z : pathToV) {
                    if (bfdpW.hasPathTo(z)) {
                        return bfdpV.distTo(z) + bfdpW.distTo(z);
                    }
                }
            }
        }

        return -1;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        checkBoundaries(v, "v");
        checkBoundaries(w, "w");
        BreadthFirstDirectedPaths bfdpV = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths bfdpW = new BreadthFirstDirectedPaths(G, w);

        for (int root = 0; root < nbVertices; root++) {
            // Is this a possible root?
            if (bfdpV.hasPathTo(root) && bfdpW.hasPathTo(root)) {
                Iterable<Integer> pathToV = bfdpV.pathTo(root);
                for (int z : pathToV) {
                    if (bfdpW.hasPathTo(z)) {
                        return z;
                    }
                }
            }
        }
        return -1;
    }

    private void checkBoundaries(Iterable<Integer> bs, String c) {
        for (int b : bs) {
            if (b < 0 || b > (nbVertices - 1)) {
                throw new IllegalArgumentException(c + " is oor");
            }
        }
    }


    // for unit testing of this class (such as the one below)
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}
