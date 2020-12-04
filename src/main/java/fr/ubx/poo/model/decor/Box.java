package fr.ubx.poo.model.decor;

public class Box extends Decor{

    @Override
    public boolean isBox() {
        return true;
    }

    @Override
    public String toString() {
        return "Box";
    }
}
