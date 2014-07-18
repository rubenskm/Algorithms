public class Solver {

    private int move = 0;
    private MinPQ<Node> minPQ;
    private Queue<Board> solution = new Queue<Board>();

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial)
    {
        /*
        First, insert the initial search node (the initial board, 0 moves, and a null previous search node) into a priority
        queue.

        Then, delete from the priority queue the search node with the minimum priority, and insert onto the priority queue
        all neighboring search nodes (those that can be reached in one move from the dequeued search node).

        Repeat this procedure until the search node dequeued corresponds to a goal board.

        The success of this approach hinges on the choice of priority function for a search node.
        */

        minPQ = new MinPQ<Node>();
        minPQ.insert(new Node(initial, 0, null));
        Node previousSearchNode = minPQ.delMin();
        solution.enqueue(previousSearchNode.board);
        while(!previousSearchNode.board.isGoal())
        {
            Queue<Board> queues = (Queue<Board>) previousSearchNode.board.neighbors();

            for (Board board : queues)
            {
                if (!board.equals(previousSearchNode.board))
                    minPQ.insert(new Node(board, move, previousSearchNode));
            }
            previousSearchNode = minPQ.delMin();
            solution.enqueue(previousSearchNode.board);
            move++;
        }
    }

    // is the initial board solvable?
    public boolean isSolvable()
    {
        return true;
    }

    // min number of moves to solve initial board; -1 if no solution
    public int moves()
    {
        return move;
    }

    private class Node implements Comparable<Node>
    {
        Board board;
        int moves;
        Node previousSearchNode;
        private Node(Board board, int moves, Node node)
        {
            this.board = board;
            this.moves = moves;
            this.previousSearchNode = node;
        }

        public int compareTo(Node node) {
            int result;

            if (this.board.hamming() < node.board.hamming()) {
                result = -1;
            } else if (this.board.hamming() > node.board.hamming()) {
                result = 1;
            } else if (this.board.manhattan() < node.board.manhattan()) {
                result = -1;
            } else if (this.board.manhattan() > node.board.manhattan()) {
                result = 1;
            }
            else {
                result = 0;
            }

            return result;
        }
    }

    // sequence of boards in a shortest solution; null if no solution
    public Iterable<Board> solution()
    {
        return solution;
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
        int a = initial.manhattan();
        int b = initial.hamming();

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