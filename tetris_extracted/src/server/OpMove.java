package server;

public class OpMove {
    private int x;
    private int rotate;

    public OpMove() {
    }

    public OpMove(int x, int rotate) {
        this.x = x;
        this.rotate = rotate;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getRotate() {
        return rotate;
    }

    public void setRotate(int rotate) {
        this.rotate = rotate;
    }
}