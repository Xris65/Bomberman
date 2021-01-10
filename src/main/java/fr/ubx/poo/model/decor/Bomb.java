package fr.ubx.poo.model.decor;

/**
 * The type Bomb.
 */
public class Bomb extends Decor {
    @Override
    public String toString() {
        return "Bomb";
    }

    @Override
    public boolean stopsBombExplosion() {
        return false;
    }
}
