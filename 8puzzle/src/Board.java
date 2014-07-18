import java.util.Arrays;

public class Board {

    private final int[][] tiles;
    private final int[][] goalTiles;
    private final int N;
    private int emptySpaceX;
    private int emptySpaceY;
    private int manhattan = -1;

    // construct a board from an N-by-N array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks)
    {
        this.tiles = blocks;
        this.N = tiles[0].length;
        goalTiles = new int[N][N];

        //Create goal array
        int count = 1;
        for (int i = 0; i < N; i++){
            for (int j = 0; j < N; j++) {

                //Memorize position for empty space in tiles
                if (tiles[i][j] == 0){
                    emptySpaceX = i;
                    emptySpaceY = j;
                }

                //Set values for goal
                this.goalTiles[i][j] = count;
                count++;
            }
        }
        this.goalTiles[N - 1][N - 1] = 0;
    }

    // board dimension N
    public int dimension()
    {
        return N;
    }

    // number of blocks out of place
    public int hamming()
    {
        int count = 0;
        for (int i = 0; i < N; i++){
            for (int j = 0; j < N; j++) {
                if (tiles[i][j] != goalTiles[i][j]){
                    //not count space as block
                    if (tiles[i][j] == 0) break;
                    count++;
                }
            }
        }
        return count;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan()
    {
        if (manhattan != -1) {
            int sum = 0;
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    for (int k = 0; k < N; k++) {
                        for (int l = 0; l < N; l++) {
                            if (tiles[i][j] == goalTiles[k][l]) {

                                //not count space as block
                                if (tiles[i][j] == 0) break;
                                sum += Math.abs((i + j) - (k + l));
                            }
                        }
                    }
                }
            }
            manhattan = sum;
            return sum;
        }
        else
            return manhattan;

    }

    // is this board the goal board?
    public boolean isGoal()
    {
        return hamming() == 0;
    }

    // a board obtained by exchanging two adjacent blocks in the same row
    public Board twin()
    {
        Board twin = new Board(tiles);
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (twin.tiles[i][j] != 0 && twin.tiles[i + 1][ j + 1] != 0)
                {
                    int aux = twin.tiles[i][j];
                    twin.tiles[i][j] = twin.tiles[i + 1][ j + 1];
                    twin.tiles[i + 1][ j + 1] = aux;
                }
            }
        }

        return twin;
    }

    // does this board equal y?
    public boolean equals(Object y)
    {
        //todo: checar se está ok a comparacao
        if (y == tiles) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;

        Board board = (Board) y;
        return Arrays.deepEquals(tiles, board.tiles);
    }

    // all neighboring boards
    public Iterable<Board> neighbors()
    {
        Queue<Board> queues = new Queue<Board>();

        //left
        queues = CheckNeighbors(queues, emptySpaceX, emptySpaceY - 1);
        //Up
        queues = CheckNeighbors(queues, emptySpaceX - 1, emptySpaceY);
        //Right
        queues = CheckNeighbors(queues, emptySpaceX , emptySpaceY + 1);
        //Down
        queues = CheckNeighbors(queues, emptySpaceX + 1, emptySpaceY);

        return queues;
    }

    private Queue<Board> CheckNeighbors(Queue<Board> queues, int x, int y) {
        //If the index not outside array and not reach goal
        if (!(x < 0) && !(x >= N) && !(y < 0) && !(y >= N)) {

            int[][] tilesAux = cloneArray(tiles);
            int aux = tilesAux[emptySpaceX][emptySpaceY];
            tilesAux[emptySpaceX][emptySpaceY] = tilesAux[x][y];
            tilesAux[x][y] = aux;
            Board board = new Board(tilesAux);
            queues.enqueue(board);
        }
        return queues;
    }

    // string representation of the board (in the output format specified below)
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tiles[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    public static int[][] cloneArray(int[][] src) {
        int length = src.length;
        int[][] target = new int[length][src[0].length];
        for (int i = 0; i < length; i++) {
            System.arraycopy(src[i], 0, target[i], 0, src[i].length);
        }
        return target;
    }
}