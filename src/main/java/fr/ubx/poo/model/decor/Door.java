package fr.ubx.poo.model.decor;

import fr.ubx.poo.game.Game;
import fr.ubx.poo.model.go.character.Character;
import fr.ubx.poo.model.go.character.Monster;
import fr.ubx.poo.model.go.character.Player;

/**
 * The type Door.
 */
public class Door extends Decor {
    private final boolean closed;
    private final boolean prev;

    /**
     * Is closed boolean.
     * returns true if door is closed.
     * @return the boolean
     */
    public boolean isClosed() {
        return closed;
    }

    /**
     * Is prev boolean.
     * If door goes to a previous world, prev is true.
     * if door goes to next world, prev is false.
     * @return the boolean
     */
    public boolean isPrev() {
        return prev;
    }


    /**
     * Instantiates a new Door.
     *
     * @param closed if door is closed.
     * @param prev   if door goes to a previous world.
     */
    public Door(boolean closed, boolean prev) {
        this.closed = closed;
        this.prev = prev;
    }

    @Override
    public void obtain(Player player) {
        if (!isClosed()) {
            Game game = player.getGame();
            game.getWorldManager().changeWorld(!isPrev(), game);
            game.setToChange(true);
        }
    }

    /**
     * Is Walkable by a character.
     * Only Player can walk on a door, and only if the door is open.
     * @param character the character
     * @return true if character is a player and door is opened.
     *         else false.
     */
    @Override
    public boolean isWalkable(Character character) {
        return !closed && !(character instanceof Monster);
    }

}
