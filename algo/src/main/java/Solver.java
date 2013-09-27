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
 * Date: 9/26/13
 */


/**
 * comments.
 */
public class Solver {

    private SearchNode result;
    private SearchNode result2;

    private class SearchNode implements Comparable<SearchNode> {
        private final Board board;
        private final int moves;
        private final SearchNode previous;
        private final int priority;

        private SearchNode(Board board, int moves, SearchNode previous) {
            this.board = board;
            this.previous = previous;
            this.moves = moves;
            this.priority = board.manhattan() + moves;
            //this.priority = this.board.hamming() + moves;
            // Property of A* algorithm.
            assert this.previous == null || priority >= this.previous.priority;
        }

        @Override
        public int compareTo(SearchNode that) {
            return (priority) - (that.priority);
        }
    }


    private SearchNode solve(Board initial) {
        MinPQ<SearchNode> pq = new MinPQ<SearchNode>();

        pq.insert(new SearchNode(initial, 0, null));
        SearchNode smallest = null;
        while (true) {
            smallest = pq.delMin();
            if (smallest.board.isGoal()) {
                //System.out.println("Got a solution!!!");
                break;
            }
            Iterable<Board> neighbors = smallest.board.neighbors();
            for (Board neighbor : neighbors) {
                // Dont enqueue a board that has already been evaluated
                if (smallest.previous == null || !neighbor.equals(smallest.previous.board)) {
                    pq.insert(new SearchNode(neighbor, smallest.moves + 1, smallest));
                }
            }
        }
        return smallest;
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        result = solve(initial);
        // result2 = solve(initial.twin());
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return result != null;
    }

    // min number of moves to solve initial board; -1 if no solution
    public int moves() {
        if (result != null) {
            return result.moves;
        }
        return -1;
    }

    // sequence of boards in a shortest solution; null if no solution
    public Iterable<Board> solution() {
        if (result == null) {
            return null;
        }
        Stack<Board> s = new Stack<Board>();
        for (SearchNode n = result; n != null; n = n.previous) {
            s.push(n.board);
        }
        return s;
    }


    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
