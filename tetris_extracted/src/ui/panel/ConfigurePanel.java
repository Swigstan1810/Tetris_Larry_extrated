package ui.panel;

import javax.swing.*;
import java.awt.*;

public class ConfigurePanel extends JPanel {
    private CardLayout cardLayout;
    private JPanel containerPanel;
    
    public ConfigurePanel(CardLayout cardLayout, JPanel containerPanel) {
        this.cardLayout = cardLayout;
        this.containerPanel = containerPanel;
        initComponents();
        layoutComponents();
    }
    
    private void initComponents() {
        // Configuration options would go here
        // For now, just show a placeholder
    }
    
    private void layoutComponents() {
        setLayout(new BorderLayout());
        
        JLabel title = new JLabel("Configuration", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);
        
        JPanel configPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        // Placeholder configuration options
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        configPanel.add(new JLabel("Game configuration options coming soon..."), gbc);
        
        add(configPanel, BorderLayout.CENTER);
        
        JButton backButton = new JButton("Back to Menu");
        backButton.addActionListener(e -> {
            cardLayout.show(containerPanel, "Main Panel");
        });
        add(backButton, BorderLayout.SOUTH);
    }
}