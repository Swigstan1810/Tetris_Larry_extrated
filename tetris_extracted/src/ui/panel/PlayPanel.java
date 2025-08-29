package ui.panel;

import model.Game;
import model.GameConfig;
import model.TetrisShape;

import javax.swing.*;
import java.awt.*;

public class PlayPanel extends JPanel {
    private CardLayout cardLayout;
    private JPanel containerPanel;
    private Game game;
    private GamePanel gamePanel;
    private PreviewPanel previewPanel;
    private SideMessagePanel sideMessagePanel;
    private TopMessagePanel topMessagePanel;
    
    public PlayPanel(CardLayout cardLayout, JPanel containerPanel) {
        this.cardLayout = cardLayout;
        this.containerPanel = containerPanel;
        initComponents();
        layoutComponents();
        startNewGame();
    }
    
    private void initComponents() {
        // Create default game configuration
        GameConfig config = new GameConfig(10, 20, GameConfig.PLAYER_TYPE_HUMAN, 1, GameConfig.PLAY_MODE_SINGLE);
        game = new Game(config, 1);
        
        // Initialize shape system
        TetrisShape.initShapes();
        
        gamePanel = new GamePanel(game);
        previewPanel = new PreviewPanel(game);
        sideMessagePanel = new SideMessagePanel(game);
        topMessagePanel = new TopMessagePanel();
        
        // Add observers
        game.addObserver(gamePanel);
        game.addObserver(previewPanel);
        game.addObserver(sideMessagePanel);
    }
    
    private void layoutComponents() {
        setLayout(new BorderLayout());
        
        add(topMessagePanel, BorderLayout.NORTH);
        add(gamePanel, BorderLayout.CENTER);
        
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(previewPanel, BorderLayout.NORTH);
        rightPanel.add(sideMessagePanel, BorderLayout.CENTER);
        
        // Back button
        JButton backButton = new JButton("Back to Menu");
        backButton.addActionListener(e -> {
            gamePanel.stopRun();
            cardLayout.show(containerPanel, "Main Panel");
        });
        rightPanel.add(backButton, BorderLayout.SOUTH);
        
        add(rightPanel, BorderLayout.EAST);
    }
    
    private void startNewGame() {
        gamePanel.startRun();
    }
    
    // Game control methods
    public void moveLeft() {
        if (game.canMoveLeft()) {
            game.moveLeft();
        }
    }
    
    public void moveRight() {
        if (game.canMoveRight()) {
            game.moveRight();
        }
    }
    
    public void moveDown() {
        if (game.canMoveDown()) {
            game.moveDown();
        }
    }
    
    public void rotate() {
        if (game.canRotate()) {
            game.rotate();
        }
    }
    
    public void hardDrop() {
        while (game.canMoveDown()) {
            game.moveDown();
        }
    }
    
    public void pauseGame() {
        game.pauseGame();
    }
}