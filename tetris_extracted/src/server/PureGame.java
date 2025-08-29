package server;

import java.util.Arrays;

public class PureGame {
    private int width;
    private int height;
    private int[][] cells;
    private int[][] currentShape;
    private int[][] nextShape;

    public PureGame() {
    }

    @Override
    public String toString() {
        return String.format("PureGame{width=%d, height=%d, cells=%s, currentShape=%s, nextShape=%s}",
                width, height,
                Arrays.deepToString(cells),
                Arrays.deepToString(currentShape),
                Arrays.deepToString(nextShape));
    }

    public int[][] getCells() {
        return cells;
    }

    public int[][] getCurrentShape() {
        return currentShape;
    }

    public int[][] getNextShape() {
        return nextShape;
    }
}