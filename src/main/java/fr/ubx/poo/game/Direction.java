/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.game;

import java.util.Random;

/**
 * The enum Direction.
 */
public enum Direction {
    /**
     * The North.
     */
    N {
        @Override
        public Position nextPosition(Position pos, int delta) {
            return new Position(pos.x, pos.y - delta);
        }
    },
    /**
     * The East.
     */
    E {
        @Override
        public Position nextPosition(Position pos, int delta) {
            return new Position(pos.x + delta, pos.y);
        }
    },
    /**
     * The South.
     */
    S {
        @Override
        public Position nextPosition(Position pos, int delta) {
            return new Position(pos.x, pos.y + delta);
        }
    },
    /**
     * The West.
     */
    W {
        @Override
        public Position nextPosition(Position pos, int delta) {
            return new Position(pos.x - delta, pos.y);
        }
    },
    ;

    private static final Random randomGenerator = new Random();

    /***
     *
     * @return a pseudorandom direction
     */
    public static Direction random() {
        return values()[randomGenerator.nextInt(values().length)];
    }


    /**
     * Next position.
     *
     * @param pos   the position
     * @param delta the delta
     * @return the position
     */
    public abstract Position nextPosition(Position pos, int delta);

    /**
     * Next position.
     *
     * @param pos the position
     * @return the position
     */
    final public Position nextPosition(Position pos) {
        return nextPosition(pos, 1);
    }
}