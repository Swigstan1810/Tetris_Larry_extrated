package server;

public class Situation {
    private int[][] field;
    private int[][] currentPiece;
    private int x, y;
    private int score;
    
    public Situation(int[][] field, int[][] currentPiece, int x, int y) {
        this.field = copyArray(field);
        this.currentPiece = copyArray(currentPiece);
        this.x = x;
        this.y = y;
        this.score = 0;
    }
    
    public double evaluate() {
        // Evaluation heuristics for AI
        double score = 0;
        
        // Height penalty - lower is better
        score -= getMaxHeight() * 5;
        
        // Line clear bonus
        score += countCompletedLines() * 100;
        
        // Hole penalty
        score -= countHoles() * 20;
        
        // Bumpiness penalty
        score -= getBumpiness() * 2;
        
        return score;
    }
    
    private int getMaxHeight() {
        for (int row = 0; row < field.length; row++) {
            for (int col = 0; col < field[row].length; col++) {
                if (field[row][col] != -1) {
                    return field.length - row;
                }
            }
        }
        return 0;
    }
    
    private int countCompletedLines() {
        int count = 0;
        for (int row = 0; row < field.length; row++) {
            boolean complete = true;
            for (int col = 0; col < field[row].length; col++) {
                if (field[row][col] == -1) {
                    complete = false;
                    break;
                }
            }
            if (complete) count++;
        }
        return count;
    }
    
    private int countHoles() {
        int holes = 0;
        for (int col = 0; col < field[0].length; col++) {
            boolean foundBlock = false;
            for (int row = 0; row < field.length; row++) {
                if (field[row][col] != -1) {
                    foundBlock = true;
                } else if (foundBlock) {
                    holes++;
                }
            }
        }
        return holes;
    }
    
    private int getBumpiness() {
        int[] heights = new int[field[0].length];
        
        // Calculate column heights
        for (int col = 0; col < field[0].length; col++) {
            for (int row = 0; row < field.length; row++) {
                if (field[row][col] != -1) {
                    heights[col] = field.length - row;
                    break;
                }
            }
        }
        
        // Calculate bumpiness (difference between adjacent columns)
        int bumpiness = 0;
        for (int i = 0; i < heights.length - 1; i++) {
            bumpiness += Math.abs(heights[i] - heights[i + 1]);
        }
        
        return bumpiness;
    }
    
    private int[][] copyArray(int[][] original) {
        if (original == null) return null;
        int[][] copy = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            copy[i] = original[i].clone();
        }
        return copy;
    }
    
    // Getters
    public int[][] getField() { return field; }
    public int[][] getCurrentPiece() { return currentPiece; }
    public int getX() { return x; }
    public int getY() { return y; }
    public int getScore() { return score; }
}