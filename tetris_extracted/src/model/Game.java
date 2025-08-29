package model;

import util.CommFun;
import ui.MainFrame;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import java.awt.Color;
import java.util.ArrayList;

public class Game {
    private static final int GAME_INIT = 0;
    private static final int GAME_RUN = 1;
    private static final int GAME_PAUSE = 2;
    private static final int GAME_END = 3;
    private static final int GAME_DONE = 4;

    private final GameConfig config;
    private int[][] cells;
    private TetrisShapeInstance currentShape;
    private TetrisShapeInstance nextShape;
    private int x;
    private int y;
    private int count;
    private int progress;
    private volatile int gameStatus;
    private int levelUp;
    private int score;
    private int lineErased;
    private final ArrayList<JComponent> observers;
    private final int player;

    public Game(GameConfig config, int player) {
        this.config = config;
        this.levelUp = 0;
        this.score = 0;
        this.lineErased = 0;
        this.observers = new ArrayList<>();
        this.player = player;
    }

    public TetrisShapeInstance getNextShape() {
        return nextShape;
    }

    public TetrisShapeInstance cloneCurrentShape() {
        return TetrisShape.cloneInstanceByShapeIndex(currentShape.getIdx());
    }

    public int getCount() {
        return count;
    }

    public int[][] cloneCells() {
        return CommFun.copyArray2D(cells);
    }

    public int getLineErased() {
        return lineErased;
    }

    public void addObserver(JComponent observer) {
        observers.add(observer);
    }

    private void informObservers() {
        for (JComponent observer : observers) {
            observer.repaint();
        }
    }

    public void newGame() {
        gameStatus = GAME_INIT;
    }

    public synchronized void runGame() {
        switch (gameStatus) {
            case GAME_INIT:
                initGame();
                break;
            case GAME_RUN:
                processGame();
                break;
            case GAME_END:
                endGame();
                break;
        }
    }

    private void updateScore(int linesCleared) {
        this.lineErased += linesCleared;
        this.score += getScore(linesCleared);
        
        int newLevelUp = this.lineErased / 10;
        if (newLevelUp > this.levelUp) {
            this.levelUp = newLevelUp;
            playSound("level-up.wav");
        }
        
        informObservers();
    }

    private int getScore(int linesCleared) {
        switch (linesCleared) {
            case 1:
                return 100;
            case 2:
                return 300;
            case 3:
                return 600;
            case 4:
                return 1000;
            default:
                return 0;
        }
    }

    public void addHighScore() {
        String message = String.format("Player %d's score is in the top scores, please enter player %d's name:", 
                player, player);
        String name = JOptionPane.showInputDialog(MainFrame.MAIN_FRAME, message, "High Score", -1);
        if (name == null) return;
        
        ScoreList.getScoreList().addNewHighScore(score, name, config);
    }

    public void endGame() {
        if (gameStatus == GAME_DONE) return;
        
        boolean isNewHighScore = ScoreList.getScoreList().isNewHighScore(score);
        if (isNewHighScore) {
            addHighScore();
        }
        
        gameStatus = GAME_DONE;
    }

    private synchronized void processGame() {
        int linesCleared = 0;
        
        if (!canMoveDown()) {
            settleShape();
            linesCleared += checkFullRows();
            if (linesCleared > 0) {
                updateScore(linesCleared);
            }
            
            generateShape();
            if (isGameOver()) {
                gameStatus = GAME_END;
                playSound("game-finish.wav");
                return;
            }
        }
        
        progress += (2 + config.initLevel());
        if (progress >= 100) {
            y++;
            progress = 0;
        }
    }

    private boolean isGameOver() {
        int[][] shape = currentShape.getShape();
        return isShapeConflict(shape, 0, 0);
    }

    private boolean isFullRow(int row) {
        for (int col = 0; col < getFieldWidth(); col++) {
            if (cells[row][col] < 0) {
                return false;
            }
        }
        return true;
    }

    private void eraseFullRow(int row) {
        for (int r = row; r >= 0; r--) {
            if (r > 0) {
                System.arraycopy(cells[r - 1], 0, cells[r], 0, getFieldWidth());
            } else {
                for (int c = 0; c < getFieldWidth(); c++) {
                    cells[r][c] = -1;
                }
            }
        }
    }

    private int checkFullRows() {
        int linesCleared = 0;
        for (int row = getFieldHeight() - 1; row >= 0; row--) {
            if (isFullRow(row)) {
                linesCleared++;
                eraseFullRow(row);
                playSound("erase-line.wav");
                row++; // Check the same row again
            }
        }
        return linesCleared;
    }

    private void settleShape() {
        int[][] shape = currentShape.getShape();
        int shapeIdx = currentShape.getIdx();
        
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                if (shape[i][j] == 1) {
                    cells[y + i][x + j] = shapeIdx;
                }
            }
        }
    }

    public boolean needDrawCurrentShape() {
        if (currentShape == null) return false;
        switch (gameStatus) {
            case GAME_RUN:
            case GAME_PAUSE:
            case GAME_END:
                return true;
            default:
                return false;
        }
    }

    public synchronized void pauseGame() {
        switch (gameStatus) {
            case GAME_RUN:
                gameStatus = GAME_PAUSE;
                break;
            case GAME_PAUSE:
                gameStatus = GAME_RUN;
                break;
            default:
                // Keep current status
                break;
        }
    }

    public int getProgress() {
        return progress;
    }

    public int[][] getShapeBoxes() {
        return currentShape.getShape();
    }

    public Color getShapeColor() {
        return currentShape.getColor();
    }

    public synchronized int getShapeX() {
        return x;
    }

    public int getShapeY() {
        return y;
    }

    public synchronized int getShapeRotate() {
        if (currentShape == null) return 0;
        return currentShape.getCurrentRotate();
    }

    private void initGame() {
        initCells();
        count = 0;
        nextShape = TetrisShape.getShapeBySequenceIndex(count++);
        generateShape();
        gameStatus = GAME_RUN;
    }

    private synchronized void generateShape() {
        currentShape = nextShape;
        nextShape = TetrisShape.getShapeBySequenceIndex(count++);
        y = 0;
        x = getFieldWidth() / 2 - 2;
        progress = 0;
        informObservers();
    }

    private synchronized void initCells() {
        cells = new int[config.fieldHeight()][config.fieldWidth()];
        for (int i = 0; i < getFieldHeight(); i++) {
            for (int j = 0; j < getFieldWidth(); j++) {
                cells[i][j] = -1;
            }
        }
    }

    public synchronized int getCell(int row, int col) {
        if (gameStatus == GAME_INIT) return -1;
        return cells[row][col];
    }

    public int getFieldWidth() {
        return config.fieldWidth();
    }

    public int getFieldHeight() {
        return config.fieldHeight();
    }

    private synchronized boolean isShapeConflict(int[][] shape, int deltaX, int deltaY) {
        if (x + deltaX < 0) return true;
        if (x + deltaX + shape[0].length > getFieldWidth()) return true;
        if (y + deltaY + shape.length > getFieldHeight()) return true;
        
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                if (shape[i][j] == 1) {
                    if (cells[y + i + deltaY][x + j + deltaX] >= 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public synchronized boolean isPlaying() {
        switch (gameStatus) {
            case GAME_INIT:
            case GAME_RUN:
            case GAME_PAUSE:
                return true;
            default:
                return false;
        }
    }

    public synchronized boolean isRunning() {
        return gameStatus == GAME_RUN;
    }

    public boolean isPaused() {
        return gameStatus == GAME_PAUSE;
    }

    private void playSound(String soundFile) {
        if (MetaConfig.getMetaConfig().isSoundOn()) {
            CommFun.playSound(soundFile);
        }
    }

    public String getPlayerTypeString() {
        return config.getPlayerTypeString();
    }

    public int getInitLevel() {
        return config.initLevel();
    }

    public int getCurrentLevel() {
        return getInitLevel() + levelUp;
    }

    public int getScore() {
        return score;
    }

    public synchronized void moveLeft() {
        x--;
    }

    public synchronized boolean canMoveLeft() {
        if (gameStatus != GAME_RUN) return false;
        
        boolean hasProgress = progress > 0;
        int[][] shape = currentShape.getShape();
        return !isShapeConflict(shape, -1, hasProgress ? 1 : 0);
    }

    public void moveRight() {
        x++;
    }

    public synchronized boolean canMoveRight() {
        if (gameStatus != GAME_RUN) return false;
        
        boolean hasProgress = progress > 0;
        int[][] shape = currentShape.getShape();
        return !isShapeConflict(shape, 1, hasProgress ? 1 : 0);
    }

    public synchronized boolean canMoveDown() {
        if (gameStatus != GAME_RUN) return false;
        
        int[][] shape = currentShape.getShape();
        return !isShapeConflict(shape, 0, 1);
    }

    public synchronized void moveDown() {
        y++;
    }

    public synchronized boolean canRotate() {
        if (gameStatus != GAME_RUN) return false;
        if (currentShape == null) return false;
        
        int[][] rotatedShape = currentShape.testRotateClockwise();
        return !isShapeConflict(rotatedShape, 0, 0);
    }

    public synchronized void rotate() {
        currentShape.doRotateClockwise();
        playSound("move-turn.wav");
    }
}