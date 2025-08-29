package model;

public class MetaConfig {
    private static MetaConfig instance;
    private boolean soundOn = true;
    private boolean musicOn = true;
    
    private MetaConfig() {
    }
    
    public static MetaConfig getMetaConfig() {
        if (instance == null) {
            instance = new MetaConfig();
        }
        return instance;
    }
    
    public boolean isSoundOn() {
        return soundOn;
    }
    
    public void setSoundOn(boolean soundOn) {
        this.soundOn = soundOn;
    }
    
    public boolean isMusicOn() {
        return musicOn;
    }
    
    public void setMusicOn(boolean musicOn) {
        this.musicOn = musicOn;
    }
    
    public void toggleSound() {
        soundOn = !soundOn;
    }
    
    public void toggleMusic() {
        musicOn = !musicOn;
    }
}