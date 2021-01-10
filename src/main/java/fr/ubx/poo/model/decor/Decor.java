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

    /**
     * Obtain.
     *
     * @param player the player
     */
    public void obtain(Player player) {
    }

    /**
     * Is walkable boolean.
     *
     * @param character the character
     * @return the boolean
     */
    public boolean isWalkable(Character character) {
        return false;
    }

    /**
     * Stops bomb explosion boolean.
     *
     * @return the boolean
     */
    public boolean stopsBombExplosion() {
        return true;
    }

    /**
     * Move.
     *
     * @param player the player
     */
    public void move(Player player) {

    }

    /**
     * Is destroyable boolean.
     *
     * @return the boolean
     */
    public boolean isDestroyable() {
        return false;
    }

    /**
     * Is to remove boolean.
     *
     * @return the boolean
     */
    public boolean isToRemove() {
        return false;
    }

    @Override
    public String toString() {
        return "Decor";
    }


}
