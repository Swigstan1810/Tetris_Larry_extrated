package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScoreList {
    private static ScoreList instance;
    private List<Score> scores;
    
    private ScoreList() {
        scores = new ArrayList<>();
        // Add some default high scores
        scores.add(new Score(50000, "Expert", new GameConfig(10, 20, 0, 5, 0)));
        scores.add(new Score(40000, "Pro", new GameConfig(10, 20, 0, 4, 0)));
        scores.add(new Score(30000, "Advanced", new GameConfig(10, 20, 0, 3, 0)));
        scores.add(new Score(20000, "Intermediate", new GameConfig(10, 20, 0, 2, 0)));
        scores.add(new Score(10000, "Beginner", new GameConfig(10, 20, 0, 1, 0)));
    }
    
    public static ScoreList getScoreList() {
        if (instance == null) {
            instance = new ScoreList();
        }
        return instance;
    }
    
    public boolean isNewHighScore(int score) {
        if (scores.size() < 10) return true;
        return score > scores.get(scores.size() - 1).getScore();
    }
    
    public void addNewHighScore(int score, String name, GameConfig config) {
        scores.add(new Score(score, name, config));
        Collections.sort(scores, (a, b) -> Integer.compare(b.getScore(), a.getScore()));
        
        // Keep only top 10 scores
        if (scores.size() > 10) {
            scores = scores.subList(0, 10);
        }
    }
    
    public List<Score> getScores() {
        return new ArrayList<>(scores);
    }
}