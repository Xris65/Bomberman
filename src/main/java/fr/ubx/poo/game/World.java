/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.game;

import fr.ubx.poo.model.decor.Decor;
import fr.ubx.poo.model.go.BombObject;
import fr.ubx.poo.model.go.ExplosionObject;
import fr.ubx.poo.model.go.character.Monster;

import java.util.*;
import java.util.function.BiConsumer;

/**
 * The type World.
 */
public class World {
    private final Map<Position, Decor> grid;
    private final WorldEntity[][] raw;
    /**
     * The Dimension.
     */
    public final Dimension dimension;
    /**
     * Confirms if the world has changed or not.
     */
    private boolean changed = false;
    /**
     * Array list containing monsters on this specific world.
     */
    private ArrayList<Monster> monsters = new ArrayList<>();
    /**
     * Array list containing bombs on this specific world.
     */
    private final ArrayList<BombObject> bombs = new ArrayList<>();
    /**
     * Array list containing bomb explosions on this specific world.
     */
    private final ArrayList<ExplosionObject> explosions = new ArrayList<>();

    /**
     * Sets monsters.
     *
     * @param monsters the monsters
     */
    public void setMonsters(ArrayList<Monster> monsters) {
        this.monsters = monsters;
    }

    /**
     * Gets bombs.
     *
     * @return the bombs' array list
     */
    public ArrayList<BombObject> getBombs() {
        return bombs;
    }

    /**
     * Gets explosions.
     *
     * @return the explosions' array list
     */
    public ArrayList<ExplosionObject> getExplosions() {
        return explosions;
    }

    /**
     * Gets monsters.
     *
     * @return the monsters' array list
     */
    public ArrayList<Monster> getMonsters() {
        return monsters;
    }

    /**
     * Confirms if the world has changed or not.
     *
     * @return true if the world has changed, false if not.
     */
    public boolean isChanged() {
        return changed;
    }

    /**
     * Sets changed boolean.
     *
     * @param changed the changed boolean
     */
    public void setChanged(boolean changed) {
        this.changed = changed;
    }

    /**
     * Updates monsters.
     *
     * @param now the actual time.
     */
    public void updateMonsters(long now) {
        monsters.forEach(self -> self.update(now, this));
    }


    /**
     * Instantiates a new World.
     *
     * @param raw the raw world.
     */
    public World(WorldEntity[][] raw) {
        this.raw = raw;
        dimension = new Dimension(raw.length, raw[0].length);
        grid = WorldBuilder.build(raw, dimension);
    }

    /**
     * Find monsters in the current world.
     *
     * @param game the game
     * @return the array list containing all monsters in this world.
     */
    public ArrayList<Monster> findMonsters(Game game) {
        ArrayList<Monster> monsters = new ArrayList<>();
        for (int x = 0; x < dimension.width; x++) {
            for (int y = 0; y < dimension.height; y++) {
                if (raw[y][x] == WorldEntity.Monster) {
                    monsters.add(new Monster(game, new Position(x, y), 1000));
                }
            }
        }
        return monsters;
    }

    /**
     * Finds player position.
     *
     * @return the player position.
     * @throws PositionNotFoundException the position not found exception.
     */
    public Position findPlayer() throws PositionNotFoundException {
        for (int x = 0; x < dimension.width; x++) {
            for (int y = 0; y < dimension.height; y++) {
                if (raw[y][x] == WorldEntity.Player) {
                    return new Position(x, y);
                }
            }
        }
        throw new PositionNotFoundException("Player");
    }

    /**
     * Get the decor in the given position of the map.
     *
     * @param position the position.
     * @return the decor at the given position.
     */
    public Decor get(Position position) {
        return grid.get(position);
    }

    /**
     * Sets the content of the given position of the map to the given decor.
     *
     * @param position the position.
     * @param decor    the decor.
     */
    public void set(Position position, Decor decor) {
        grid.put(position, decor);
        changed = true;
    }

    /**
     * Clears the given position on that map position.
     *
     * @param position the position.
     */
    public void clear(Position position) {
        grid.remove(position);
        changed = true;
    }

    /**
     * Applies the function given to every element of the list.
     *
     * @param fn the function.
     */
    public void forEach(BiConsumer<Position, Decor> fn) {
        grid.forEach(fn);
    }

    /**
     * Checks if the given position is inside the map or not.
     *
     * @param p the position.
     * @return true if the position is inside, false if not.
     */
    public boolean isInside(Position p) {
        return ((p.x >= 0) && (p.x < dimension.width) && (p.y >= 0) && (p.y < dimension.height));
    }

    /**
     * Checks if there is a monster at the given position or not.
     *
     * @param position the position.
     * @return true if there is a monster at that position, false if not.
     */
    public boolean isThereAMonsterAt(Position position) {
        for (Monster monster : getMonsters()) {
            if (monster.getPosition().equals(position)) {
                return true;
            }
        }
        return false;
    }

}
