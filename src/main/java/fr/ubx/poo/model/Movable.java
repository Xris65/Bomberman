/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.model;

import fr.ubx.poo.game.Direction;

/**
 * The interface Movable.
 */
public interface Movable {
    /**
     * Can the player move in the given direction.
     *
     * @param direction the direction
     * @return if the player can move
     */
    boolean canMove(Direction direction);

    /**
     * Do move in the given direction.
     *
     * @param direction the direction
     */
    void doMove(Direction direction);
}
