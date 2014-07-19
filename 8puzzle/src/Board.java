import java.util.Arrays;

public class Board {

    private final byte[][] tiles;
    private final int N;
    private int emptySpaceX;
    private int emptySpaceY;
    private int manhattan = -1;
    private int hamming = -1;

    // construct a board from an N-by-N array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        this.N = blocks[0].length;
        tiles = convertIntToByte(new byte[N][N], blocks);

        initialSetup();
    }

    private Board(byte[][] blocks) {
        this.N = blocks[0].length;
        tiles = cloneArray(blocks);

        initialSetup();
    }

    private void initialSetup() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {

                //Memorize position for empty space in tiles
                if (tiles[i][j] == 0) {
                    emptySpaceX = i;
                    emptySpaceY = j;
                }
            }
        }
    }

    private byte[][] convertIntToByte(byte[][] _tiles, int[][] blocks) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                _tiles[i][j] = (byte) blocks[i][j];
            }
        }
        return _tiles;
    }

    // board dimension N
    public int dimension() {
        return N;
    }

    // number of blocks out of place
    public int hamming() {
        if (hamming == -1) {

            int count = 0;
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    //compare with correct goal value in current position
                    if (tiles[i][j] != 0 && tiles[i][j] != i * N + j + 1) {
                        count++;
                    }
                }
            }

            hamming = count;
            return count;
        } else {
            return hamming;
        }
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        if (manhattan == -1) {

            int sum = 0;
            int x,y;

            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if(tiles[i][j] != 0) {
                        //Get the position where could store the values when reach goal
                        x = (tiles[i][j] - 1) / N;
                        y = (tiles[i][j] - 1) % N;
                        sum += Math.abs(x - i) + Math.abs(y - j);
                    }
                }
            }

            manhattan = sum;
            return sum;
        } else {
            return manhattan;
        }
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // a board obtained by exchanging two adjacent blocks in the same row
    public Board twin() {

        byte [][] twin = cloneArray(tiles);
        int line = 0;

        if (emptySpaceX == 0) {
            line = 1;
        }

        byte aux = twin[line][0];
        twin[line][0] = twin[line][1];
        twin[line][1] = aux;

        return new Board(twin);
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;

        Board board = (Board) y;
        return Arrays.deepEquals(tiles, board.tiles);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Queue<Board> queues = new Queue<Board>();

        //left
        queues = checkNeighbors(queues, emptySpaceX, emptySpaceY - 1);
        //Up
        queues = checkNeighbors(queues, emptySpaceX - 1, emptySpaceY);
        //Right
        queues = checkNeighbors(queues, emptySpaceX, emptySpaceY + 1);
        //Down
        queues = checkNeighbors(queues, emptySpaceX + 1, emptySpaceY);

        return queues;
    }

    private Queue<Board> checkNeighbors(Queue<Board> queues, int x, int y) {
        //If the index not outside array and not reach goal
        if (!(x < 0) && !(x >= N) && !(y < 0) && !(y >= N)) {

            byte[][] tilesAux = cloneArray(tiles);
            byte aux = tilesAux[emptySpaceX][emptySpaceY];
            tilesAux[emptySpaceX][emptySpaceY] = tilesAux[x][y];
            tilesAux[x][y] = aux;
            Board board = new Board(tilesAux);
            queues.enqueue(board);
        }
        return queues;
    }

    private Queue<Board> checkNeighbors(Queue<Board> queues, int x, int y) {
        //If the index not outside array and not reach goal
        if (!(x < 0) && !(x >= N) && !(y < 0) && !(y >= N)) {

            byte[][] tilesAux = cloneArray(tiles);
            byte aux = tilesAux[emptySpaceX][emptySpaceY];
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
    }    private static byte[][] cloneArray(byte[][] src) {
        int length = src.length;
        byte[][] target = new byte[length][src[0].length];
        for (int i = 0; i < length; i++) {
            System.arraycopy(src[i], 0, target[i], 0, src[i].length);
        }
        return target;
    }
}