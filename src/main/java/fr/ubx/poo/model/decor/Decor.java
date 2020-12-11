/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.model.decor;

import fr.ubx.poo.model.Entity;
import fr.ubx.poo.model.go.character.Player;

/***
 * A decor is an element that does not know its own position in the grid.
 */
abstract public class Decor extends Entity {

    public void obtain(Player player){
    }

    @Override
    public boolean isWalkable() {
        return false;
    }

    @Override
    public String toString() {
        return "Decor";
    }

}
