package server;

public class Simulation {
    private int[][] cells;
    private int[][] currentShape;
    private int[][] nextShape;
    
    public Simulation(int[][] cells, int[][] currentShape, int[][] nextShape) {
        this.cells = copyArray(cells);
        this.currentShape = copyArray(currentShape);
        this.nextShape = copyArray(nextShape);
    }
    
    public int[] getOptimizedMove() {
        // Simple AI: try different positions and rotations
        int bestX = 0;
        int bestRotate = 0;
        double bestScore = Double.NEGATIVE_INFINITY;
        
        Shape shape = new Shape(currentShape);
        
        // Try all rotations
        for (int rotate = 0; rotate < shape.getMaxRotate(); rotate++) {
            int[][] rotatedShape = shape.getShape();
            
            // Try all horizontal positions
            for (int x = 0; x <= getFieldWidth() - rotatedShape[0].length; x++) {
                double score = evaluatePosition(rotatedShape, x);
                if (score > bestScore) {
                    bestScore = score;
                    bestX = x;
                    bestRotate = rotate;
                }
            }
            
            shape.doRotateClockwise();
        }
        
        return new int[]{bestX, bestRotate};
    }
    
    private double evaluatePosition(int[][] shape, int x) {
        // Simple evaluation based on:
        // - Height of placement (lower is better)
        // - Lines cleared (more is better)  
        // - Holes created (fewer is better)
        
        int dropY = findDropPosition(shape, x);
        if (dropY < 0) return Double.NEGATIVE_INFINITY; // Invalid position
        
        double score = 0;
        
        // Lower placement is better
        score -= dropY * 10;
        
        // Check for line clears
        int[][] tempCells = copyArray(cells);
        placeShape(tempCells, shape, x, dropY);
        int linesCleared = countFullLines(tempCells);
        score += linesCleared * 1000;
        
        // Penalize holes
        score -= countHoles(tempCells) * 50;
        
        return score;
    }
    
    private int findDropPosition(int[][] shape, int x) {
        for (int y = 0; y < getFieldHeight(); y++) {
            if (isCollision(shape, x, y)) {
                return y - 1;
            }
        }
        return getFieldHeight() - shape.length;
    }
    
    private boolean isCollision(int[][] shape, int x, int y) {
        if (y + shape.length > getFieldHeight()) return true;
        if (x < 0 || x + shape[0].length > getFieldWidth()) return true;
        
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                if (shape[i][j] == 1 && y + i >= 0 && cells[y + i][x + j] != -1) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private void placeShape(int[][] field, int[][] shape, int x, int y) {
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                if (shape[i][j] == 1) {
                    field[y + i][x + j] = 1;
                }
            }
        }
    }
    
    private int countFullLines(int[][] field) {
        int count = 0;
        for (int i = 0; i < field.length; i++) {
            boolean full = true;
            for (int j = 0; j < field[i].length; j++) {
                if (field[i][j] == -1) {
                    full = false;
                    break;
                }
            }
            if (full) count++;
        }
        return count;
    }
    
    private int countHoles(int[][] field) {
        int holes = 0;
        for (int j = 0; j < getFieldWidth(); j++) {
            boolean foundBlock = false;
            for (int i = 0; i < getFieldHeight(); i++) {
                if (field[i][j] != -1) {
                    foundBlock = true;
                } else if (foundBlock) {
                    holes++;
                }
            }
        }
        return holes;
    }
    
    private int getFieldWidth() {
        return cells[0].length;
    }
    
    private int getFieldHeight() {
        return cells.length;
    }
    
    private int[][] copyArray(int[][] original) {
        if (original == null) return null;
        int[][] copy = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            copy[i] = original[i].clone();
        }
        return copy;
    }
}