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
    private boolean changed = false;
    private ArrayList<Monster> monsters = new ArrayList<>();
    private final ArrayList<BombObject> bombs = new ArrayList<>();
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
     * @return the bombs
     */
    public ArrayList<BombObject> getBombs() {
        return bombs;
    }

    /**
     * Gets explosions.
     *
     * @return the explosions
     */
    public ArrayList<ExplosionObject> getExplosions() {
        return explosions;
    }

    /**
     * Gets monsters.
     *
     * @return the monsters
     */
    public ArrayList<Monster> getMonsters() {
        return monsters;
    }

    /**
     * Is changed boolean.
     *
     * @return the boolean
     */
    public boolean isChanged() {
        return changed;
    }

    /**
     * Sets changed.
     *
     * @param changed the changed
     */
    public void setChanged(boolean changed) {
        this.changed = changed;
    }

    /**
     * Update monsters.
     *
     * @param now the now
     */
    public void updateMonsters(long now) {
        monsters.forEach(self -> self.update(now, this));
    }


    /**
     * Instantiates a new World.
     *
     * @param raw the raw
     */
    public World(WorldEntity[][] raw) {
        this.raw = raw;
        dimension = new Dimension(raw.length, raw[0].length);
        grid = WorldBuilder.build(raw, dimension);
    }

    /**
     * Find monsters array list.
     *
     * @param game the game
     * @return the array list
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
     * Find player position.
     *
     * @return the position
     * @throws PositionNotFoundException the position not found exception
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
     * Get decor.
     *
     * @param position the position
     * @return the decor
     */
    public Decor get(Position position) {
        return grid.get(position);
    }

    /**
     * Set.
     *
     * @param position the position
     * @param decor    the decor
     */
    public void set(Position position, Decor decor) {
        grid.put(position, decor);
        changed = true;
    }

    /**
     * Clear.
     *
     * @param position the position
     */
    public void clear(Position position) {
        grid.remove(position);
        changed = true;
    }

    /**
     * For each.
     *
     * @param fn the fn
     */
    public void forEach(BiConsumer<Position, Decor> fn) {
        grid.forEach(fn);
    }

    /**
     * Is inside boolean.
     *
     * @param p the p
     * @return the boolean
     */
    public boolean isInside(Position p) {
        return ((p.x >= 0) && (p.x < dimension.width) && (p.y >= 0) && (p.y < dimension.height));
    }

    /**
     * Is there a monster at boolean.
     *
     * @param position the position
     * @return the boolean
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
