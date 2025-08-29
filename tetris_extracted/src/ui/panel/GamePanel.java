package ui.panel;

import model.Game;
import model.TetrisShape;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GamePanel extends JPanel {
    public static final int CELL_SIZE = 20;
    
    private final Game game;
    private Timer timer;

    public GamePanel(Game game) {
        super();
        this.game = game;
        setPreferredSize(new Dimension(getFieldWidth(), getFieldHeight()));
        setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        drawCells(g2d);
        
        if (game.needDrawCurrentShape()) {
            drawShape(g2d);
        }
        
        if (game.isPaused()) {
            drawPauseMessage(g2d);
        }
    }

    private void drawPauseMessage(Graphics2D g2d) {
        g2d.setFont(new Font("Arial", Font.BOLD, 12));
        g2d.setColor(Color.BLACK);
        g2d.drawString("Game is paused,", 10, 100);
        g2d.drawString("press P to continue.", 10, 120);
    }

    private void drawShape(Graphics2D g2d) {
        int[][] shapeBoxes = game.getShapeBoxes();
        int shapeX = game.getShapeX();
        int shapeY = game.getShapeY();
        Color shapeColor = game.getShapeColor();
        int progress = game.getProgress();
        
        int progressOffset = (progress * 20) / 100;
        
        for (int i = 0; i < shapeBoxes.length; i++) {
            for (int j = 0; j < shapeBoxes[0].length; j++) {
                if (shapeBoxes[i][j] == 1) {
                    drawShapeBox(g2d, shapeY + i, progressOffset, shapeX + j, shapeColor);
                }
            }
        }
    }

    private void drawShapeBox(Graphics2D g2d, int row, int progressOffset, int col, Color color) {
        g2d.setColor(color);
        g2d.fillRect(col * CELL_SIZE, row * CELL_SIZE + progressOffset, CELL_SIZE, CELL_SIZE);
    }

    private void drawCells(Graphics2D g2d) {
        for (int row = 0; row < game.getFieldHeight(); row++) {
            for (int col = 0; col < game.getFieldWidth(); col++) {
                drawCell(g2d, row, col);
            }
        }
    }

    private void drawCell(Graphics2D g2d, int row, int col) {
        int cellValue = game.getCell(row, col);
        if (cellValue < 0) return; // Empty cell
        
        Color color = TetrisShape.getShapeColor(cellValue);
        g2d.setColor(color);
        g2d.fillRect(col * CELL_SIZE, row * CELL_SIZE, CELL_SIZE, CELL_SIZE);
    }

    public void startRun() {
        game.newGame();
        
        if (timer == null) {
            timer = new Timer(20, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    game.runGame();
                    repaint();
                }
            });
        }
        timer.start();
    }

    public void stopRun() {
        if (timer == null) return;
        timer.stop();
    }

    private int getFieldHeight() {
        return CELL_SIZE * game.getFieldHeight();
    }

    private int getFieldWidth() {
        return CELL_SIZE * game.getFieldWidth();
    }
}