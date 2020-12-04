/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.model.decor;


public class Tree extends Decor {

    @Override
    public boolean isTree() {
        return true;
    }

    @Override
    public String toString() {
        return "Tree";
    }
}
