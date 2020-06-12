import java.util.Arrays;

import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Board {

    private final int n;
    private int[][] board;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        n = tiles.length;
        board = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++)
                board[i][j] = tiles[i][j];
        }
    }

    // string representation of this board
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append(n + "\n");

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                string.append(String.format("%2d ", board[i][j]));
            }
            string.append("\n");
        }

        return string.toString();
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        int count = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if ((board[i][j] != 0) && (board[i][j] != ((i * n) + j + 1)))
                    count++;
            }
        }

        return count;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int sum = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {

                if ((board[i][j] != 0) && (board[i][j] != ((i * n) + j + 1))) {
                    int expectedI = (board[i][j] - 1) / n;
                    int expectedJ = (board[i][j] - 1) % n;

                    sum += Math.abs(i - expectedI) + Math.abs(j - expectedJ);
                }
            }
        }

        return sum;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null)
            return false;
        if (y == this)
            return true;

        if (y.getClass() != this.getClass())
            return false;

        Board that = (Board) y;
        return Arrays.deepEquals(this.board, that.board);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Stack<Board> neighbours = new Stack<>();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 0) {

                    // if 0 block is not at top position
                    if (i > 0) {
                        int[][] newBoard = copyBoard(board, n);
                        newBoard[i][j] = board[i - 1][j];
                        newBoard[i - 1][j] = board[i][j];
                        neighbours.push(new Board(newBoard));
                    }

                    // if 0 block is not at left position
                    if (j > 0) {
                        int[][] newBoard = copyBoard(board, n);
                        newBoard[i][j] = board[i][j - 1];
                        newBoard[i][j - 1] = board[i][j];
                        neighbours.push(new Board(newBoard));
                    }

                    // if 0 block is not at bottom position
                    if (i < n - 1) {
                        int[][] newBoard = copyBoard(board, n);
                        newBoard[i][j] = board[i + 1][j];
                        newBoard[i + 1][j] = board[i][j];
                        neighbours.push(new Board(newBoard));
                    }

                    // if 0 block is not at right position
                    if (j < n - 1) {
                        int[][] newBoard = copyBoard(board, n);
                        newBoard[i][j] = board[i][j + 1];
                        newBoard[i][j + 1] = board[i][j];
                        neighbours.push(new Board(newBoard));
                    }

                    break;
                }
            }
        }

        return neighbours;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[][] newBoard = copyBoard(board, n);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - 1; j++) {

                if ((newBoard[i][j] != 0) && (newBoard[i][j + 1] != 0)) {
                    int swap = newBoard[i][j];
                    newBoard[i][j] = newBoard[i][j + 1];
                    newBoard[i][j + 1] = swap;
                    return new Board(newBoard);
                }
            }
        }

        return null;

    }

    private int[][] copyBoard(int[][] tiles, int n) {
        int[][] newBoard = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                newBoard[i][j] = tiles[i][j];
            }
        }

        return newBoard;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
    }

}