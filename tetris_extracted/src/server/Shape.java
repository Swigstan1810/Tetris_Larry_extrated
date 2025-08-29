package server;

public class Shape {
    private int[][] shape;
    private int maxRotate;
    private int currentRotate;

    Shape(int[][] shape) {
        this.shape = shape;
        this.currentRotate = 0;
        calculateMaxRotate();
    }

    private boolean boxesMatch(int[][] shape1, int[][] shape2) {
        int rows = shape1.length;
        int cols = shape1[0].length;
        
        if (shape2.length != rows || shape2[0].length != cols) {
            return false;
        }
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (shape1[i][j] != shape2[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    private void calculateMaxRotate() {
        int[][] current = this.shape;
        this.maxRotate = 0;
        
        do {
            this.maxRotate++;
            current = rotateClockWise(current);
            if (boxesMatch(this.shape, current)) {
                break;
            }
        } while (this.maxRotate < 4);
    }

    public int getMaxRotate() {
        return maxRotate;
    }

    public int getCurrentRotate() {
        return currentRotate;
    }

    public int[][] getShape() {
        return shape;
    }

    private static int[][] rotateClockWise(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        int[][] rotated = new int[cols][rows];
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                rotated[j][rows - 1 - i] = matrix[i][j];
            }
        }
        return rotated;
    }

    private int[][] testRotateClockwise() {
        return rotateClockWise(this.shape);
    }

    public void doRotateClockwise() {
        int[][] rotated = testRotateClockwise();
        this.currentRotate = (this.currentRotate + 1) % this.maxRotate;
        setShape(rotated);
    }

    public void setShape(int[][] shape) {
        this.shape = shape;
    }
}