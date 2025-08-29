package util;

import javax.sound.sampled.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class SoundEffectManager {
    private static SoundEffectManager instance;
    private Map<String, Clip> clips;
    
    private SoundEffectManager() {
        clips = new HashMap<>();
    }
    
    public static synchronized SoundEffectManager getInstance() {
        if (instance == null) {
            instance = new SoundEffectManager();
        }
        return instance;
    }
    
    public void playSound(String soundFile) {
        try {
            Clip clip = clips.get(soundFile);
            if (clip == null) {
                InputStream audioStream = getClass().getResourceAsStream("/resources/audios/" + soundFile);
                if (audioStream == null) {
                    System.err.println("Could not find sound file: " + soundFile);
                    return;
                }
                
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioStream);
                clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clips.put(soundFile, clip);
            }
            
            clip.setFramePosition(0);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Error playing sound " + soundFile + ": " + e.getMessage());
        }
    }
    
    public void close() {
        for (Clip clip : clips.values()) {
            if (clip != null && clip.isOpen()) {
                clip.close();
            }
        }
        clips.clear();
    }
}