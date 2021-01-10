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
     * This function's behavior changes depending on the decor.
     * @param player the player obtaining the decor.
     */
    public void obtain(Player player) {
    }

    /**
     * Is walkable boolean.
     * By default a decor isn't walkable.
     * @param character the character
     * @return the boolean
     */
    public boolean isWalkable(Character character) {
        return false;
    }

    /**
     * Stops bomb explosion boolean.
     * By default a decor stops bomb explosion.
     * @return the boolean
     */
    public boolean stopsBombExplosion() {
        return true;
    }

    /**
     * Move.
     * A decor doesn't move by default.
     * @param player the player that moves the decor.
     */
    public void move(Player player) {

    }

    /**
     * Is destroyable boolean.
     * By default a decor isn't destroyable by a bomb.
     * @return the boolean
     */
    public boolean isDestroyable() {
        return false;
    }

    /**
     * Is to remove boolean.
     * By default a decor should not be removed if a player walks on it.
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
