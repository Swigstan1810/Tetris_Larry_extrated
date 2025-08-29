package ui.panel;

import model.Game;

import javax.swing.*;
import java.awt.*;

public class SideMessagePanel extends JPanel {
    private Game game;
    private JLabel scoreLabel;
    private JLabel levelLabel;
    private JLabel linesLabel;
    
    public SideMessagePanel(Game game) {
        this.game = game;
        initComponents();
        layoutComponents();
    }
    
    private void initComponents() {
        scoreLabel = new JLabel("Score: 0");
        levelLabel = new JLabel("Level: 1");
        linesLabel = new JLabel("Lines: 0");
        
        Font labelFont = new Font("Arial", Font.BOLD, 14);
        scoreLabel.setFont(labelFont);
        levelLabel.setFont(labelFont);
        linesLabel.setFont(labelFont);
    }
    
    private void layoutComponents() {
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createTitledBorder("Game Info"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(scoreLabel, gbc);
        
        gbc.gridy = 1;
        add(levelLabel, gbc);
        
        gbc.gridy = 2;
        add(linesLabel, gbc);
        
        // Controls info
        gbc.gridy = 4;
        gbc.insets = new Insets(20, 5, 5, 5);
        add(new JLabel("Controls:"), gbc);
        
        gbc.insets = new Insets(2, 5, 2, 5);
        gbc.gridy = 5;
        add(new JLabel("← → Move"), gbc);
        
        gbc.gridy = 6;
        add(new JLabel("↑ Rotate"), gbc);
        
        gbc.gridy = 7;
        add(new JLabel("↓ Soft Drop"), gbc);
        
        gbc.gridy = 8;
        add(new JLabel("Space Hard Drop"), gbc);
        
        gbc.gridy = 9;
        add(new JLabel("P Pause"), gbc);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Update labels with current game state
        scoreLabel.setText("Score: " + game.getScore());
        levelLabel.setText("Level: " + game.getCurrentLevel());
        linesLabel.setText("Lines: " + game.getLineErased());
    }
}