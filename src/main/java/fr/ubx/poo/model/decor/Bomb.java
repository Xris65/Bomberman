package fr.ubx.poo.model.decor;

/**
 * The type Bomb.
 */
public class Bomb extends Decor {

    /**
     * A bomb doesn't stop a bomb explosion, since it explodes as well.
     * @return false
     */
    @Override
    public boolean stopsBombExplosion() {
        return false;
    }

    @Override
    public String toString() {
        return "Bomb";
    }


}
