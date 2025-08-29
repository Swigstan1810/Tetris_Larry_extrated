package model;

import java.awt.Color;

public class TetrisShapeInstance {
    private final Color color;
    private int[][] shape;
    private final int idx;
    private final int maxRotate;
    private int currentRotate;

    TetrisShapeInstance(Color color, int[][] shape, int idx, int maxRotate) {
        this.color = color;
        this.shape = shape;
        this.idx = idx;
        this.maxRotate = maxRotate;
        this.currentRotate = 0;
    }

    public int getMaxRotate() {
        return maxRotate;
    }

    public int getCurrentRotate() {
        return currentRotate;
    }

    public int getIdx() {
        return idx;
    }

    public Color getColor() {
        return color;
    }

    public int[][] getShape() {
        return shape;
    }

    public int[][] testRotateClockwise() {
        int rows = shape.length;
        int cols = shape[0].length;
        int[][] rotated = new int[cols][rows];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                rotated[j][rows - 1 - i] = shape[i][j];
            }
        }
        return rotated;
    }

    public void doRotateClockwise() {
        int[][] rotated = testRotateClockwise();
        this.currentRotate = (this.currentRotate + 1) % this.maxRotate;
        setShape(rotated);
    }

    public void setShape(int[][] shape) {
        this.shape = shape;
    }

    public int[][] rotateCounterClockwise() {
        int rows = shape.length;
        int cols = shape[0].length;
        int[][] rotated = new int[cols][rows];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                rotated[cols - 1 - j][i] = shape[i][j];
            }
        }
        return rotated;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}