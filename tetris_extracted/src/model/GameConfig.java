package model;

public class GameConfig {
    public static final int PLAYER_TYPE_HUMAN = 0;
    public static final int PLAYER_TYPE_AI = 1;
    public static final int PLAYER_TYPE_EXTERNAL = 2;
    
    public static final int PLAY_MODE_SINGLE = 0;
    public static final int PLAY_MODE_DOUBLE = 1;

    private final int fieldWidth;
    private final int fieldHeight;
    private final int playerType;
    private final int initLevel;
    private final int playMode;

    public GameConfig(int fieldWidth, int fieldHeight, int playerType, int initLevel, int playMode) {
        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;
        this.playerType = playerType;
        this.initLevel = initLevel;
        this.playMode = playMode;
    }

    public int fieldWidth() {
        return fieldWidth;
    }

    public int fieldHeight() {
        return fieldHeight;
    }

    public int playerType() {
        return playerType;
    }

    public int initLevel() {
        return initLevel;
    }

    public int playMode() {
        return playMode;
    }

    public String getPlayerTypeString() {
        switch (playerType) {
            case PLAYER_TYPE_HUMAN:
                return "Human";
            case PLAYER_TYPE_AI:
                return "AI";
            case PLAYER_TYPE_EXTERNAL:
                return "External";
            default:
                return "Unknown";
        }
    }

    public String getPlayModeString() {
        switch (playMode) {
            case PLAY_MODE_SINGLE:
                return "Single";
            case PLAY_MODE_DOUBLE:
                return "Double";
            default:
                return "Unknown";
        }
    }

    @Override
    public String toString() {
        return String.format("%dx%d(%d) %s %s", 
                fieldWidth, fieldHeight, initLevel, 
                getPlayerTypeString(), getPlayModeString());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        GameConfig that = (GameConfig) obj;
        return fieldWidth == that.fieldWidth &&
               fieldHeight == that.fieldHeight &&
               playerType == that.playerType &&
               initLevel == that.initLevel &&
               playMode == that.playMode;
    }

    @Override
    public int hashCode() {
        int result = fieldWidth;
        result = 31 * result + fieldHeight;
        result = 31 * result + playerType;
        result = 31 * result + initLevel;
        result = 31 * result + playMode;
        return result;
    }
}