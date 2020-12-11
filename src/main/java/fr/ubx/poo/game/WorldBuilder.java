package fr.ubx.poo.game;

import fr.ubx.poo.model.decor.*;
import fr.ubx.poo.model.decor.Bonus.*;
import fr.ubx.poo.model.decor.Bonus.BombBonus.BombNumber;
import fr.ubx.poo.model.decor.Bonus.BombBonus.BombRange;
import fr.ubx.poo.model.decor.Bonus.Princess;

import java.util.Hashtable;
import java.util.Map;

public class WorldBuilder {
    private final Map<Position, Decor> grid = new Hashtable<>();

    private WorldBuilder() {
    }

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

    private static Decor processEntity(WorldEntity entity) {
        return switch (entity) {
            case Stone -> new Stone();
            case Tree -> new Tree();
            case Box -> new Box();
            case BombNumberDec -> new BombNumber(false);
            case BombNumberInc -> new BombNumber(true);
            case BombRangeDec -> new BombRange(false);
            case BombRangeInc -> new BombRange(true);
            case Heart -> new Heart();
            case Key -> new Key();
            case Princess -> new Princess();
            case DoorNextClosed -> new Door(true,false);
            case DoorNextOpened -> new Door(false,false);
            case DoorPrevOpened -> new Door(false,true);
            // no bomb in raw, so this case is useless
            // case Bomb -> new Bomb();
            default -> null;
        };
    }
}
