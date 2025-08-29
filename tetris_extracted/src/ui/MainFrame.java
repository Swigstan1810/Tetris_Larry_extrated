package ui;

import ui.panel.*;
import util.CommFun;
import model.MetaConfig;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainFrame extends JFrame {
    public static MainFrame MAIN_FRAME;
    
    private CardLayout cardLayout;
    private PlayPanel playPanel;
    private JPanel containerPanel;
    
    private static final int WIDTH = 675;
    private static final int HEIGHT = 550;

    public MainFrame(String title) throws HeadlessException {
        super(title);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                confirmExit();
            }
        });
        
        initLayout();
        initPanels();
        toNormalSize();
        setVisible(true);
        MAIN_FRAME = this;
        setupKeyBindings();
    }

    public void toGameSize() {
        pack();
        center();
    }

    public void toNormalSize() {
        setSize(WIDTH, HEIGHT);
        center();
    }

    private void setupKeyBindings() {
        InputMap inputMap = containerPanel.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        ActionMap actionMap = containerPanel.getActionMap();

        // Left movement
        Action leftAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (playPanel != null) playPanel.moveLeft();
            }
        };
        Action leftAction2 = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (playPanel != null) playPanel.moveLeft();
            }
        };

        // Right movement
        Action rightAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (playPanel != null) playPanel.moveRight();
            }
        };
        Action rightAction2 = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (playPanel != null) playPanel.moveRight();
            }
        };

        // Rotation
        Action upAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (playPanel != null) playPanel.rotate();
            }
        };
        Action upAction2 = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (playPanel != null) playPanel.rotate();
            }
        };

        // Down movement
        Action downAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (playPanel != null) playPanel.moveDown();
            }
        };
        Action downAction2 = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (playPanel != null) playPanel.hardDrop();
            }
        };

        // Pause
        Action pauseAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (playPanel != null) playPanel.pauseGame();
            }
        };

        // Music toggle
        Action musicAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MetaConfig.getMetaConfig().toggleMusic();
            }
        };

        // Sound toggle
        Action soundAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MetaConfig.getMetaConfig().toggleSound();
            }
        };

        // Bind keys
        inputMap.put(KeyStroke.getKeyStroke("LEFT"), "leftAction");
        actionMap.put("leftAction", leftAction);
        inputMap.put(KeyStroke.getKeyStroke("COMMA"), "leftAction2");
        actionMap.put("leftAction2", leftAction2);

        inputMap.put(KeyStroke.getKeyStroke("RIGHT"), "rightAction");
        actionMap.put("rightAction", rightAction);
        inputMap.put(KeyStroke.getKeyStroke("PERIOD"), "rightAction2");
        actionMap.put("rightAction2", rightAction2);

        inputMap.put(KeyStroke.getKeyStroke("UP"), "upAction");
        actionMap.put("upAction", upAction);
        inputMap.put(KeyStroke.getKeyStroke("L"), "upAction2");
        actionMap.put("upAction2", upAction2);

        inputMap.put(KeyStroke.getKeyStroke("DOWN"), "downAction");
        actionMap.put("downAction", downAction);
        inputMap.put(KeyStroke.getKeyStroke("SPACE"), "downAction2");
        actionMap.put("downAction2", downAction2);

        inputMap.put(KeyStroke.getKeyStroke("P"), "pauseAction");
        actionMap.put("pauseAction", pauseAction);

        inputMap.put(KeyStroke.getKeyStroke("M"), "musicAction");
        actionMap.put("musicAction", musicAction);

        inputMap.put(KeyStroke.getKeyStroke("S"), "soundAction");
        actionMap.put("soundAction", soundAction);
    }

    private void initLayout() {
        setLayout(new BorderLayout());
        cardLayout = new CardLayout();
        containerPanel = new JPanel(cardLayout);
        add(containerPanel, BorderLayout.CENTER);
        
        JPanel authorPanel = new JPanel();
        authorPanel.add(new JLabel("Author: Larry Wen"));
        add(authorPanel, BorderLayout.SOUTH);
    }

    private void initPanels() {
        MainPanel mainPanel = new MainPanel();
        playPanel = new PlayPanel(cardLayout, containerPanel);
        
        mainPanel.setPlayListener(e -> {
            cardLayout.show(containerPanel, "Play Panel");
        });
        
        ConfigurePanel configurePanel = new ConfigurePanel(cardLayout, containerPanel);
        mainPanel.setConfigListener(e -> {
            cardLayout.show(containerPanel, "Configure Panel");
        });
        
        HighScorePanel highScorePanel = new HighScorePanel(cardLayout, containerPanel);
        mainPanel.setHighScoreListener(e -> {
            cardLayout.show(containerPanel, "High Score Panel");
        });
        
        containerPanel.add(mainPanel, "Main Panel");
        containerPanel.add(playPanel, "Play Panel");
        containerPanel.add(configurePanel, "Configure Panel");
        containerPanel.add(highScorePanel, "High Score Panel");
    }

    private void center() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = getSize();
        int x = (screenSize.width - frameSize.width) / 2;
        int y = (screenSize.height - frameSize.width) / 2;
        setLocation(x, y);
    }

    public void confirmExit() {
        int result = JOptionPane.showConfirmDialog(
            MAIN_FRAME,
            "Are you sure you want to exit?",
            "Exit Confirmation",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        
        if (result == JOptionPane.YES_OPTION) {
            CommFun.closeSound();
            System.exit(0);
        }
    }
}