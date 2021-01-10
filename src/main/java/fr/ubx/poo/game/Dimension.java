package fr.ubx.poo.game;

/**
 * The type Dimension.
 */
public class Dimension {
    /**
     * The Height.
     */
    public final int height;
    /**
     * The Width.
     */
    public final int width;

    /**
     * Instantiates a new Dimension.
     *
     * @param height the height
     * @param width  the width
     */
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
