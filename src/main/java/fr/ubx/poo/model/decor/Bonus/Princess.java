package fr.ubx.poo.model.decor.Bonus;

import fr.ubx.poo.model.go.character.Character;
import fr.ubx.poo.model.go.character.Monster;
import fr.ubx.poo.model.go.character.Player;

/**
 * The type Princess.
 */
public class Princess extends Bonus {

    @Override
    public boolean isToRemove() {
        return false;
    }

    @Override
    public void obtain(Player player) {
        player.wins();
    }

    @Override
    public boolean isDestroyable() {
        return false;
    }

    @Override
    public boolean isWalkable(Character character) {
        return !(character instanceof Monster);
    }
}
