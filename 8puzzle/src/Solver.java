public class Solver {

    private Node[] lastNode;
    private boolean isGoal;
    private int turn;
    private Stack<Board> solutionQueue;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        /*
        First, insert the initial search node (the initial board, 0 moves, and a null previous search node) into a priority
        queue.

        Then, delete from the priority queue the search node with the minimum priority, and insert onto the priority queue
        all neighboring search nodes (those that can be reached in one move from the dequeued search node).

        Repeat this procedure until the search node dequeued corresponds to a goal board.

        The success of this approach hinges on the choice of priority function for a search node.
        */

        isGoal = false;

        MinPQ<Node> minPQ = new MinPQ<Node>();
        MinPQ<Node> minTwinPQ = new MinPQ<Node>();

        minPQ.insert(new Node(initial, 0, null));
        minTwinPQ.insert(new Node(initial.twin(), 0, null));

        lastNode = new Node[2];

        while (!minPQ.isEmpty() && !minTwinPQ.isEmpty()) {
            MinPQ<Node> priorityQueue;
            if (turn == 0)
                priorityQueue = minPQ;
            else
                priorityQueue = minTwinPQ;

            lastNode[turn] = priorityQueue.delMin();

            if (lastNode[turn].board.isGoal()) {
                isGoal = true;
                break;
            }

            Iterable<Board> boards = lastNode[turn].board.neighbors();

            Node previous = lastNode[turn].previous;

            for (Board board : boards) {
                if (previous == null || !board.equals(previous.board)) {
                    priorityQueue.insert(new Node(board, lastNode[turn].moves + 1, lastNode[turn]));
                }
            }

            turn = 1 - turn;
        }
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

    // is the initial board solvable?
    public boolean isSolvable() {
        return isGoal && turn == 0;
    }

    // min number of moves to solve initial board; -1 if no solution
    public int moves() {
        if (isSolvable()) {
            if (lastNode[0] == null)
                return 0;
            return lastNode[0].moves;
        }

        return -1;
    }

    // sequence of boards in a shortest solution; null if no solution
    public Iterable<Board> solution() {

        if (isSolvable()) {
            if (solutionQueue == null) {
                solutionQueue = new Stack<Board>();
                Node node = lastNode[0];
                while (node != null) {
                    solutionQueue.push(node.board);
                    node = node.previous;
                }
            }

            return solutionQueue;

        } else {
            return null;
        }
    }

    private static class Node implements Comparable<Node> {

        private Board board;
        private Node previous;
        private int moves;
        private int manhattan;

        private Node(Board myBoard, int myMoves, Node myNode) {
            this.board = myBoard;
            this.previous = myNode;
            this.moves = myMoves;
            manhattan = board.manhattan();
        }

        @Override
        public int compareTo(Node that) {
            if (this.manhattan + this.moves < that.manhattan + that.moves) {
                return -1;
            } else if (this.manhattan + this.moves > that.manhattan + that.moves) {
                return 1;
            } else if (this.moves > that.moves) {
                return -1;
            } else if (this.moves < that.moves) {
                return 1;
            } else {
                return 0;
            }
        }
    }
}