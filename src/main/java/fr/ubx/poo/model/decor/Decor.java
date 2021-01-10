/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.model.decor;

import fr.ubx.poo.model.Entity;
import fr.ubx.poo.model.go.character.Character;
import fr.ubx.poo.model.go.character.Player;

/***
 * A decor is an element that does not know its own position in the grid.
 */
abstract public class Decor extends Entity {

    public void obtain(Player player) {
    }

    public boolean isWalkable(Character character) {
        return false;
    }

    public boolean stopsBombExplosion() {
        return true;
    }

    public void move(Player player) {

    }

    public boolean isDestroyable() {
        return false;
    }

    public boolean isToRemove() {
        return false;
    }

    @Override
    public String toString() {
        return "Decor";
    }


}
