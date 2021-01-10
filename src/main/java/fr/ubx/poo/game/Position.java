/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.game;

import java.util.Objects;

/**
 * The type Position.
 */
public class Position {
    /**
     * The X.
     */
    public final int x;
    /**
     * The Y.
     */
    public final int y;

    /**
     * Instantiates a new Position.
     *
     * @param x the x
     * @param y the y
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Instantiates a new Position.
     *
     * @param position the position
     */
    public Position(Position position) {
        this.x = position.x;
        this.y = position.y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return x == position.x &&
                y == position.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "(x:" + x + ", y:" + y + ")";
    }
}
