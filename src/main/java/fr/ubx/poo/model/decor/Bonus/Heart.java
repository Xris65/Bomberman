package fr.ubx.poo.model.decor.Bonus;

import fr.ubx.poo.model.go.character.Player;

/**
 * The type Heart.
 */
public class Heart extends Bonus {

    /**
     * When obtaining a Heart, add 1 life to player.
     * @param player the player affected by the bonus.
     */
    @Override
    public void obtain(Player player) {
        player.addLife();
    }
}
