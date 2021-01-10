package fr.ubx.poo.model.decor.Bonus;

import fr.ubx.poo.model.go.character.Character;
import fr.ubx.poo.model.go.character.Monster;
import fr.ubx.poo.model.go.character.Player;

/**
 * The type Princess.
 */
public class Princess extends Bonus {

    /**
     * The princess disappears because she is saved by the player.
     * @return true
     */
    @Override
    public boolean isToRemove() {
        return true;
    }

    /**
     * When walked on, the princess makes the player the winner.
     * @param player the player affected by the bonus.
     */
    @Override
    public void obtain(Player player) {
        player.wins();
    }

    /**
     * The princess cannot be killed by a bomb.
     * @return false
     */
    @Override
    public boolean isDestroyable() {
        return false;
    }

    /**
     * The princess can only be walked on by player, not by monsters.
     * @param character the character that may be or may not be a monster.
     * @return true if the character is a player, else false.
     */
    @Override
    public boolean isWalkable(Character character) {
        return !(character instanceof Monster);
    }
}
