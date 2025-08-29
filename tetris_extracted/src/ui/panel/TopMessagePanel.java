package ui.panel;

import javax.swing.*;
import java.awt.*;

public class TopMessagePanel extends JPanel {
    
    public TopMessagePanel() {
        initComponents();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(0, 50));
        
        JLabel titleLabel = new JLabel("TETRIS GAME", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.BLUE);
        
        add(titleLabel, BorderLayout.CENTER);
    }
}