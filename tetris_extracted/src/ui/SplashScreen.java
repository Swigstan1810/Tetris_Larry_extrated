package ui;

import javax.swing.*;
import java.awt.*;

public class SplashScreen {
    private int duration;
    private JWindow splashWindow;
    
    public SplashScreen(int duration) {
        this.duration = duration;
    }
    
    public void showSplash() {
        splashWindow = new JWindow();
        
        JPanel content = new JPanel();
        content.setBackground(Color.WHITE);
        content.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        content.setLayout(new BorderLayout());
        
        JLabel title = new JLabel("TETRIS", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 48));
        title.setForeground(Color.BLUE);
        content.add(title, BorderLayout.CENTER);
        
        JLabel loading = new JLabel("Loading...", SwingConstants.CENTER);
        loading.setFont(new Font("Arial", Font.PLAIN, 16));
        content.add(loading, BorderLayout.SOUTH);
        
        splashWindow.setContentPane(content);
        splashWindow.setSize(400, 300);
        
        // Center the splash screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - 400) / 2;
        int y = (screenSize.height - 300) / 2;
        splashWindow.setLocation(x, y);
        
        splashWindow.setVisible(true);
        
        // Auto-hide after duration
        Timer timer = new Timer(duration, e -> {
            splashWindow.setVisible(false);
            splashWindow.dispose();
        });
        timer.setRepeats(false);
        timer.start();
    }
}