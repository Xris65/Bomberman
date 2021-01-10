/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.game;

import java.util.Arrays;
import java.util.Optional;

/**
 * The enum World entity.
 */
public enum WorldEntity {
    /**
     * Empty world entity.
     */
    Empty('_'),
    /**
     * Box world entity.
     */
    Box('B'),
    /**
     * Heart world entity.
     */
    Heart('H'),
    /**
     * Key world entity.
     */
    Key('K'),
    /**
     * Monster world entity.
     */
    Monster('M'),
    /**
     * Door prev opened world entity.
     */
    DoorPrevOpened('V'),
    /**
     * Door next opened world entity.
     */
    DoorNextOpened('N'),
    /**
     * Door next closed world entity.
     */
    DoorNextClosed('n'),
    /**
     * Player world entity.
     */
    Player('P'),
    /**
     * Stone world entity.
     */
    Stone('S'),
    /**
     * Tree world entity.
     */
    Tree('T'),
    /**
     * Princess world entity.
     */
    Princess('W'),
    /**
     * Bomb range increment bonus world entity.
     */
    BombRangeInc('>'),
    /**
     * Bomb range decrement world bonus entity.
     */
    BombRangeDec('<'),
    /**
     * Bomb number increment world bonus entity.
     */
    BombNumberInc('+'),
    /**
     * Bomb number decrement world bonus entity.
     */
    BombNumberDec('-'),
    ;
    /**
     * Character code of an Entity.
      */
    private final char code;

    /**
     * Instantiates a World Entity
     * @param code
     */
    WorldEntity(char code) {
        this.code = code;
    }

    /**
     * From code optional.
     *
     * @param code the code
     * @return the optional
     */
    public static Optional<WorldEntity> fromCode(char code) {
        return Arrays.stream(values())
                .filter(e -> e.acceptCode(code))
                .findFirst();
    }

    private boolean acceptCode(char code) {
        return this.code == code;
    }

    @Override
    public String toString() {
        return "" + code;
    }
}
