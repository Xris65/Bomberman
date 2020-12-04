/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.model.decor;

public class Stone extends Decor {

    @Override
    public boolean isStone() {
        return true;
    }

    @Override
    public String toString() {
        return "Stone";
    }
}
