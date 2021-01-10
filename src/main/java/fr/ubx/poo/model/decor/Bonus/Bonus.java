package fr.ubx.poo.model.decor.Bonus;

import fr.ubx.poo.model.decor.Decor;
import fr.ubx.poo.model.go.character.Character;

/**
 * The type Bonus.
 */
abstract public class Bonus extends Decor {
    /**
     * A bonus is walkable.
     * @param character the character if needed.
     * @return true
     */
    @Override
    public boolean isWalkable(Character character) {
        return true;
    }

    /**
     * Is to remove by an explosion.
     * @return true
     */
    @Override
    public boolean isToRemove() {
        return true;
    }

    /**
     * Is destroyable by a bomb.
     * @return true
     */
    @Override
    public boolean isDestroyable() {
        return true;
    }

    /**
     * Stops bomb explosion.
     * As in a classic bomberman, bonuses won't stop the bomb explosion.
     * @return false
     */
    @Override
    public boolean stopsBombExplosion() {
        return false;
    }

    @Override
    public String toString() {
        return "Bonus";
    }
}
