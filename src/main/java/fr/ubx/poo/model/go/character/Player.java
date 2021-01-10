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
     * Move a box if a box is the decor the player is walking into
     * @param world
     */
    public void moveBoxIfAble(World world) {
        Position boxAt = direction.nextPosition(getPosition());
        Decor decor = world.get(boxAt);
        if (decor != null) {
            decor.move(this);
        }
    }

    /**
     *
      * @param game
     * @param position
     */
    public Player(Game game, Position position) {
        super(game, position, Direction.S);
        setLives(game.getInitPlayerLives());
        setTimeToAct(2500); // time for the invulnerability to wear off
    }

    public boolean isVulnerable() {
        return !isInvulnerable;
    }

    public void loseLife(long now) {
        loseLife();
        isInvulnerable = true;
        setLastActionTime(now);
    }

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

    @Override
    public boolean canMove(Direction direction) {
        World w = super.game.getWorld();
        Position p = direction.nextPosition(super.getPosition());
        boolean inMap = w.isInside(p);
        Decor targetPosition = w.get(p);
        boolean isWalkable = targetPosition == null || handleNewPosition(targetPosition, p);

        return inMap && isWalkable;
    }


    public void update(long now) {
        if (isInvulnerable) {
            actionIfTime(now, () -> isInvulnerable = false);
        }
        super.update(now);
    }


    public boolean isWinner() {
        return winner;
    }

    @Override
    public void requestMove(Direction direction) {
        super.requestMove(direction);
        moveBoxIfAble(game.getWorld());
    }
}
