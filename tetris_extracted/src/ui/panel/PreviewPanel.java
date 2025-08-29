package ui.panel;

import model.Game;
import model.TetrisShapeInstance;

import javax.swing.*;
import java.awt.*;

public class PreviewPanel extends JPanel {
    private Game game;
    
    public PreviewPanel(Game game) {
        this.game = game;
        setPreferredSize(new Dimension(120, 100));
        setBorder(BorderFactory.createTitledBorder("Next Shape"));
        setBackground(Color.WHITE);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        TetrisShapeInstance nextShape = game.getNextShape();
        if (nextShape == null) return;
        
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int[][] shapeBoxes = nextShape.getShape();
        Color shapeColor = nextShape.getColor();
        
        // Center the preview shape
        int startX = (getWidth() - shapeBoxes[0].length * 20) / 2;
        int startY = (getHeight() - shapeBoxes.length * 20) / 2;
        
        g2d.setColor(shapeColor);
        for (int i = 0; i < shapeBoxes.length; i++) {
            for (int j = 0; j < shapeBoxes[0].length; j++) {
                if (shapeBoxes[i][j] == 1) {
                    g2d.fillRect(startX + j * 20, startY + i * 20, 18, 18);
                }
            }
        }
    }
}