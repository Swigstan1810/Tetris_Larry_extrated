package ui.panel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainPanel extends JPanel {
    private JButton playButton;
    private JButton configButton;
    private JButton highScoreButton;
    private JButton exitButton;
    
    public MainPanel() {
        initComponents();
        layoutComponents();
    }
    
    private void initComponents() {
        playButton = new JButton("Play Game");
        configButton = new JButton("Configure");
        highScoreButton = new JButton("High Scores");
        exitButton = new JButton("Exit");
        
        // Style buttons
        Dimension buttonSize = new Dimension(200, 50);
        Font buttonFont = new Font("Arial", Font.BOLD, 16);
        
        playButton.setPreferredSize(buttonSize);
        playButton.setFont(buttonFont);
        
        configButton.setPreferredSize(buttonSize);
        configButton.setFont(buttonFont);
        
        highScoreButton.setPreferredSize(buttonSize);
        highScoreButton.setFont(buttonFont);
        
        exitButton.setPreferredSize(buttonSize);
        exitButton.setFont(buttonFont);
        
        exitButton.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to exit?",
                "Exit Confirmation",
                JOptionPane.YES_NO_OPTION
            );
            if (result == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
    }
    
    private void layoutComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        // Title
        JLabel title = new JLabel("TETRIS", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 48));
        title.setForeground(Color.BLUE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 0, 40, 0);
        add(title, gbc);
        
        // Buttons
        gbc.insets = new Insets(10, 0, 10, 0);
        
        gbc.gridy = 1;
        add(playButton, gbc);
        
        gbc.gridy = 2;
        add(configButton, gbc);
        
        gbc.gridy = 3;
        add(highScoreButton, gbc);
        
        gbc.gridy = 4;
        add(exitButton, gbc);
    }
    
    public void setPlayListener(ActionListener listener) {
        playButton.addActionListener(listener);
    }
    
    public void setConfigListener(ActionListener listener) {
        configButton.addActionListener(listener);
    }
    
    public void setHighScoreListener(ActionListener listener) {
        highScoreButton.addActionListener(listener);
    }
}