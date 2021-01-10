/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.model.go.character;

import fr.ubx.poo.game.*;
import fr.ubx.poo.model.decor.*;

/**
 * The type Player.
 */
public class Player extends Character {
    /**
     * If player has won.
     */
    private boolean winner;

    /**
     * If player is Invulnerable.
     */
    private boolean isInvulnerable = false;

    /**
     * Number of bombs player can place simultaneously.
     */
    private int bombCapacity = 1;

    /**
     * Number of bombs left the player can place.
     */
    private int numberOfBombs = 0;

    /**
     * Range of the bomb explosion.
     */
    private int bombRange = 1;

    /**
     * Number of keys the player has.
     */
    private int numberOfKeys = 0;

    /**
     * Player wins.
     */
    public void wins() {
        winner = true;
    }

    /**
     * Add bomb in inventory.
     */
    public void addBomb() {
        numberOfBombs--;
    }

    /**
     * Remove bomb from inventory.
     */
    public void removeBomb() {
        numberOfBombs++;
    }

    /**
     * Add 1 to the bomb capacity
     */
    public void addBombCapacity() {
        bombCapacity++;
    }

    /**
     * Remove 1 to bomb capacity.
     * bomb capacity must always be at least one,
     * so only remove if bomb capacity is more than 1.
     */
    public void removeBombCapacity() {
        if (bombCapacity > 1)
            bombCapacity--;
    }

    /**
     * Remove a key.
     */
    public void removeKey() {
        numberOfKeys--;
    }

    /**
     * Add a key.
     */
    public void addKey() {
        numberOfKeys++;
    }

    /**
     * Add 1 to the bomb range.
     */
    public void addBombRange() {
        bombRange++;
    }

    /**
     * Remove 1 to the bomb range.
     * like removeBombCapacity, the bomb range cannot
     * be inferior to 1, so only remove one if bomb range
     * is more than 1.
     */
    public void removeBombRange() {
        if (bombRange > 1)
            bombRange--;
    }

    /**
     * Get the number of keys.
     * @return the number of keys.
     */
    public int getNumberOfKeys() {
        return numberOfKeys;
    }

    /**
     * Get the number of bombs.
     * @return the number of bombs.
     */
    public int getNumberOfBombs() {
        return numberOfBombs;
    }

    /**
     * Get the bomb capacity.
     * @return the bomb capacity.
     */
    public int getBombCapacity() {
        return bombCapacity;
    }

    /**
     * Get the bomb range.
     * @return the bomb range.
     */
    public int getBombRange() {
        return bombRange;
    }

    /**
     * Move a box if a box is the decor the player is walking into.
     */
    public void moveBoxIfAble() {
        Position boxAt = direction.nextPosition(getPosition());
        Decor decor = game.getWorld().get(boxAt);
        if (decor != null) {
            decor.move(this);
        }
    }

    /**
     * Instantiate a new Player.
      * @param game the game.
     * @param position the starting position of the player.
     */
    public Player(Game game, Position position) {
        super(game, position, Direction.S);
        setLives(game.getInitPlayerLives());
        setTimeToAct(2500); // time for the invulnerability to wear off
    }

    /**
     * if player is vulnerable.
     * @return player state of vulnerability.
     */
    public boolean isVulnerable() {
        return !isInvulnerable;
    }

    /**
     * This 'loseLife' method makes the player invulnerable,
     * unlike loseLife().
     * The player just loses a life.
     * @param now the time the player lost a life to manage invulnerability.
     */
    public void loseLife(long now) {
        loseLife();
        isInvulnerable = true;
        setLastActionTime(now);
    }

    /**
     * Execute actions based on the player walking on the decor d
     * at the position p.
     * @param d the decor the player is walking on.
     * @param p the position the player is going to be at.
     * @return true if the newPosition is walkable or not.
     */
    private boolean handleNewPosition(Decor d, Position p) {
        d.obtain(this);
        if (d.isToRemove()) {
            game.getWorld().clear(p);
        }

        if (d instanceof Door) {
            if (!((Door) d).isClosed()) {
                game.changeWorld(!((Door) d).isPrev());
                game.setToChange(true);
                return false;
            }
        }
        return d.isWalkable(this);
    }

    /**
     * Almost the same as the can move from character,
     * but this one handles the new position with handleNewPosition().
     * @param direction the direction the player is trying to move at.
     * @return if the player can move at the given direction.
     */
    @Override
    public boolean canMove(Direction direction) {
        World w = super.game.getWorld();
        Position p = direction.nextPosition(super.getPosition());
        boolean inMap = w.isInside(p);
        Decor targetPosition = w.get(p);
        boolean isWalkable = targetPosition == null || handleNewPosition(targetPosition, p);

        return inMap && isWalkable;
    }


    /**
     * Update the player state on each frame.
     * @param now time of the frame.
     */
    public void update(long now) {
        if (isInvulnerable) {
            actionIfTime(now, () -> isInvulnerable = false);
        }
        super.update(now);
    }


    /**
     * Player is winner.
     * @return true if the player won the game.
     */
    public boolean isWinner() {
        return winner;
    }

    /**
     * This version uses moveBoxIfAble()
     * @param direction the direction the player is trying to go to.
     */
    @Override
    public void requestMove(Direction direction) {
        super.requestMove(direction);
        moveBoxIfAble();
    }
}
