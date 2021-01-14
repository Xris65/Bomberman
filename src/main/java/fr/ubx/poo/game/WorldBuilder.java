package fr.ubx.poo.game;

import fr.ubx.poo.model.decor.*;
import fr.ubx.poo.model.decor.Bonus.*;
import fr.ubx.poo.model.decor.Bonus.BombBonus.BombNumber;
import fr.ubx.poo.model.decor.Bonus.BombBonus.BombRange;
import fr.ubx.poo.model.decor.Bonus.Princess;

import java.util.Hashtable;
import java.util.Map;

/**
 * The type World builder.
 */
public class WorldBuilder {
    private final Map<Position, Decor> grid = new Hashtable<>();

    private WorldBuilder() {
    }

    /**
     * Builds the map.
     *
     * @param raw       the raw version of the map
     * @param dimension the dimension of the map
     * @return the map
     */
    public static Map<Position, Decor> build(WorldEntity[][] raw, Dimension dimension) {
        WorldBuilder builder = new WorldBuilder();
        for (int x = 0; x < dimension.width; x++) {
            for (int y = 0; y < dimension.height; y++) {
                Position pos = new Position(x, y);
                Decor decor = processEntity(raw[y][x]);
                if (decor != null)
                    builder.grid.put(pos, decor);
            }
        }
        return builder.grid;
    }

    /**
     * Processes the given entity into the corresponding Decor.
     *
     * @param entity the entity.
     * @return the corresponding decor.
     */
    private static Decor processEntity(WorldEntity entity) {
        switch (entity) {
            case Stone:
                return new Stone();
            case Tree:
                return new Tree();
            case Box:
                return new Box();
            case BombNumberDec:
                return new BombNumber(false);
            case BombNumberInc:
                return new BombNumber(true);
            case BombRangeDec:
                return new BombRange(false);
            case BombRangeInc:
                return new BombRange(true);
            case Heart:
                return new Heart();
            case Key:
                return new Key();
            case Princess:
                return new Princess();
            case DoorNextClosed:
                return new Door(true, false);
            case DoorNextOpened:
                return new Door(false, false);
            case DoorPrevOpened:
                return new Door(false, true);
            // no bomb in raw, so this case is useless
            // case Bomb -> new Bomb();
            default:
                return null;
        }
    }
}
