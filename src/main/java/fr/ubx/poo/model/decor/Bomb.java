package fr.ubx.poo.model.decor;

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
