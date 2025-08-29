package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Score {
    private int score;
    private String playerName;
    private GameConfig gameConfig;
    private LocalDateTime timestamp;
    
    public Score(int score, String playerName, GameConfig gameConfig) {
        this.score = score;
        this.playerName = playerName;
        this.gameConfig = gameConfig;
        this.timestamp = LocalDateTime.now();
    }
    
    public int getScore() {
        return score;
    }
    
    public String getPlayerName() {
        return playerName;
    }
    
    public GameConfig getGameConfig() {
        return gameConfig;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return String.format("%s - %d points (%s) - %s", 
                playerName, score, gameConfig.toString(), timestamp.format(formatter));
    }
}