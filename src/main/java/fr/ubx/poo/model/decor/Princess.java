package fr.ubx.poo.model.decor;

public class Princess extends Decor{

    @Override
    public boolean isWalkable() {
        return true;
    }

    @Override
    public boolean isPrincess() {
        return true;
    }
}
