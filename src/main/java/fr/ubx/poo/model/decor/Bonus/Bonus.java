package fr.ubx.poo.model.decor.Bonus;

import fr.ubx.poo.model.decor.Decor;
import fr.ubx.poo.model.go.character.Character;

/**
 * The type Bonus.
 */
abstract public class Bonus extends Decor {
    @Override
    public boolean isWalkable(Character character) {
        return true;
    }

    @Override
    public boolean isToRemove() {
        return true;
    }

    @Override
    public boolean isDestroyable() {
        return true;
    }

    // As in a classic bomberman, bonuses won't stop the bomb explosion
    @Override
    public boolean stopsBombExplosion() {
        return false;
    }

    @Override
    public String toString() {
        return "Bonus";
    }
}
