/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.game;

import fr.ubx.poo.model.Entity;
import fr.ubx.poo.model.decor.Decor;
import fr.ubx.poo.model.go.BombObject;
import fr.ubx.poo.model.go.Explosion;
import fr.ubx.poo.model.go.character.Monster;

import java.lang.reflect.Array;
import java.util.*;
import java.util.function.BiConsumer;

public class World {
    private final Map<Position, Decor> grid;
    private final WorldEntity[][] raw;
    public final Dimension dimension;
    private boolean changed = false;
    private ArrayList<Monster> monsters = new ArrayList<>();
    private final ArrayList<BombObject> bombs = new ArrayList<>();
    private final ArrayList<Explosion> explosions = new ArrayList<>();

    public void setMonsters(ArrayList<Monster> monsters) {
        this.monsters = monsters;
    }

    public ArrayList<BombObject> getBombs() {
        return bombs;
    }

    public ArrayList<Explosion> getExplosions() {
        return explosions;
    }

    public ArrayList<Monster> getMonsters() {
        return monsters;
    }

    public boolean isChanged() {
        return changed;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }

    public void updateMonsters(long now){
        monsters.forEach(self -> self.update(now));
    }


    public World(WorldEntity[][] raw) {
        this.raw = raw;
        dimension = new Dimension(raw.length, raw[0].length);
        grid = WorldBuilder.build(raw, dimension);
    }

    public ArrayList<Monster> findMonsters(Game game) {
        ArrayList<Monster> monsters = new ArrayList<>();
        for (int x = 0; x < dimension.width; x++) {
            for (int y = 0; y < dimension.height; y++) {
                if (raw[y][x] == WorldEntity.Monster) {
                    monsters.add(new Monster(game, new Position(x,y),1000));
                }
            }
        }
        return monsters;
    }

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

    public Decor  get(Position position) {
        return grid.get(position);
    }

    public void set(Position position, Decor decor) {
        grid.put(position, decor);
    }

    public void clear(Position position) {
        grid.remove(position);
        changed = true;
    }

    public void forEach(BiConsumer<Position, Decor> fn) {
        grid.forEach(fn);
    }

    public Collection<Decor> values() {
        return grid.values();
    }

    public boolean isInside(Position p) {
        return ((p.x >= 0) && (p.x < dimension.width) && (p.y >= 0) && (p.y < dimension.height));
    }

    public boolean isEmpty(Position position) {
        return grid.get(position) == null;
    }
}
