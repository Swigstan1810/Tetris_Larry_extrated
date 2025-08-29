package ui.panel;

import model.Score;
import model.ScoreList;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class HighScorePanel extends JPanel {
    private CardLayout cardLayout;
    private JPanel containerPanel;
    private JList<Score> scoreList;
    
    public HighScorePanel(CardLayout cardLayout, JPanel containerPanel) {
        this.cardLayout = cardLayout;
        this.containerPanel = containerPanel;
        initComponents();
        layoutComponents();
    }
    
    private void initComponents() {
        scoreList = new JList<>();
        scoreList.setFont(new Font("Monospaced", Font.PLAIN, 12));
        updateScoreList();
    }
    
    private void layoutComponents() {
        setLayout(new BorderLayout());
        
        JLabel title = new JLabel("High Scores", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);
        
        JScrollPane scrollPane = new JScrollPane(scoreList);
        scrollPane.setPreferredSize(new Dimension(500, 300));
        add(scrollPane, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel();
        
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> updateScoreList());
        buttonPanel.add(refreshButton);
        
        JButton backButton = new JButton("Back to Menu");
        backButton.addActionListener(e -> {
            cardLayout.show(containerPanel, "Main Panel");
        });
        buttonPanel.add(backButton);
        
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void updateScoreList() {
        List<Score> scores = ScoreList.getScoreList().getScores();
        DefaultListModel<Score> model = new DefaultListModel<>();
        
        for (int i = 0; i < scores.size(); i++) {
            Score score = scores.get(i);
            model.addElement(score);
        }
        
        scoreList.setModel(model);
        
        // Custom renderer to show rank
        scoreList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                    boolean isSelected, boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Score) {
                    setText(String.format("%d. %s", index + 1, value.toString()));
                }
                return c;
            }
        });
    }
}