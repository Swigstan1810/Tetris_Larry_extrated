package model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public enum TetrisShape {
    I(Color.CYAN, new int[][]{{1, 1, 1, 1}}, 4),
    J(Color.BLUE, new int[][]{{0, 0, 1}, {1, 1, 1}}, 4),
    L(Color.ORANGE, new int[][]{{1, 0, 0}, {1, 1, 1}}, 4),
    O(Color.YELLOW, new int[][]{{1, 1}, {1, 1}}, 1),
    S(Color.GREEN, new int[][]{{0, 1, 1}, {1, 1, 0}}, 2),
    T(Color.PINK, new int[][]{{0, 1, 0}, {1, 1, 1}}, 4),
    Z(Color.RED, new int[][]{{1, 1, 0}, {0, 1, 1}}, 2);

    private static List<Integer> shapes;
    private final Color color;
    private final int[][] shape;
    private final int maxRotate;

    TetrisShape(Color color, int[][] shape, int maxRotate) {
        this.color = color;
        this.shape = shape;
        this.maxRotate = maxRotate;
    }

    public static synchronized void initShapes() {
        shapes = new ArrayList<>();
    }

    private static synchronized void generateShapes() {
        TetrisShape[] allShapes = values();
        Random random = new Random();
        
        for (int i = 0; i < 10; i++) {
            shapes.add(random.nextInt(allShapes.length));
        }
    }

    public static TetrisShapeInstance getShapeBySequenceIndex(int index) {
        while (index >= shapes.size()) {
            generateShapes();
        }
        int shapeIndex = shapes.get(index);
        return cloneInstanceByShapeIndex(shapeIndex);
    }

    public static synchronized TetrisShapeInstance cloneInstanceByShapeIndex(int shapeIndex) {
        TetrisShape[] allShapes = values();
        return allShapes[shapeIndex].createInstance(shapeIndex);
    }

    private synchronized TetrisShapeInstance createInstance(int idx) {
        return new TetrisShapeInstance(this.color, this.shape, idx, this.maxRotate);
    }

    public static Color getShapeColor(int shapeIndex) {
        TetrisShape[] allShapes = values();
        return allShapes[shapeIndex].color;
    }
}