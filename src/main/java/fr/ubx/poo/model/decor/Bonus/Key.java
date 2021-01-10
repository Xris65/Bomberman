package fr.ubx.poo.model.decor.Bonus;

import fr.ubx.poo.model.go.character.Player;

/**
 * The type Key.
 */
public class Key extends Bonus {
    @Override
    public void obtain(Player player) {
        player.addKey();
    }

    @Override
    public boolean isDestroyable() {
        return false;
    }
}
