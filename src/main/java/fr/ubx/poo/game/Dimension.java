package fr.ubx.poo.game;

public class Dimension {
    public final int height;
    public final int width;

    public Dimension(int height, int width) {
        this.height = height;
        this.width = width;
    }

    @Override
    public String toString() {
        return "Dimension{" +
                "height=" + height +
                ", width=" + width +
                '}';
    }
}
