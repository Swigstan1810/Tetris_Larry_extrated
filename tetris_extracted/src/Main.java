import ui.SplashScreen;
import ui.MainFrame;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SplashScreen splashScreen = new SplashScreen(3000);
        splashScreen.showSplash();
        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }
    
    private static void createAndShowGUI() {
        MainFrame mainFrame = new MainFrame("Tetris");
    }
}