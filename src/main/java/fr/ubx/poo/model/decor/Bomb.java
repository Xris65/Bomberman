package fr.ubx.poo.model.decor;

public class Bomb extends Decor {

    @Override
    public boolean isBomb() {
        return true;
    }

    @Override
    public String toString() {
        return "Bomb";
    }
}
