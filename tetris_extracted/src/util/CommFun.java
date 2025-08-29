package util;

import util.SoundEffectManager;

public class CommFun {
    
    public static int[][] copyArray2D(int[][] original) {
        if (original == null) return null;
        
        int[][] copy = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            copy[i] = new int[original[i].length];
            System.arraycopy(original[i], 0, copy[i], 0, original[i].length);
        }
        return copy;
    }
    
    public static void playSound(String soundFile) {
        SoundEffectManager.getInstance().playSound(soundFile);
    }
    
    public static void closeSound() {
        SoundEffectManager.getInstance().close();
    }
}