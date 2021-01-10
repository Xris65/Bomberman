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
     * The N.
     */
    N {
        @Override
        public Position nextPosition(Position pos, int delta) {
            return new Position(pos.x, pos.y - delta);
        }
    },
    /**
     * The E.
     */
    E {
        @Override
        public Position nextPosition(Position pos, int delta) {
            return new Position(pos.x + delta, pos.y);
        }
    },
    /**
     * The S.
     */
    S {
        @Override
        public Position nextPosition(Position pos, int delta) {
            return new Position(pos.x, pos.y + delta);
        }
    },
    /**
     * The W.
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
     * Next position position.
     *
     * @param pos   the pos
     * @param delta the delta
     * @return the position
     */
    public abstract Position nextPosition(Position pos, int delta);

    /**
     * Next position position.
     *
     * @param pos the pos
     * @return the position
     */
    final public Position nextPosition(Position pos) {
        return nextPosition(pos, 1);
    }
}