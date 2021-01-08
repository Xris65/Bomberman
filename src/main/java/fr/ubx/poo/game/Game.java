/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.game;


import fr.ubx.poo.model.decor.Door;
import fr.ubx.poo.model.go.character.Player;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Game {

    private World world;
    private final WorldManager manager;
    private final Player player;
    public int initPlayerLives = 1;
    private boolean toChange = false;


    public Game(String worldPath) {
        manager = new WorldManager(worldPath);
        loadConfig(worldPath);
        world = manager.getNextWorld(this);
        Position positionPlayer;
        try {
            positionPlayer = world.findPlayer();
            player = new Player(this, positionPlayer);
        } catch (PositionNotFoundException e) {
            System.err.println("Position not found : " + e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }

    public void changeWorld(boolean goingUp) {
        if (goingUp) {
            World nextWorld = manager.getNextWorld(this);
            if (nextWorld == null) {
                return;
            } else {
                world = nextWorld;
            }
        } else {
            world = manager.getPreviousWorld();
        }
        Dimension dimension = world.dimension;
        for (int x = 0; x < dimension.width; x++) {
            for (int y = 0; y < dimension.height; y++) {
                if (world.get(new Position(x, y)) instanceof Door)
                    if (!((Door) world.get(new Position(x, y))).isClosed()) {
                        if ((goingUp && ((Door) world.get(new Position(x, y))).isPrev())
                                || !(goingUp) && !(((Door) world.get(new Position(x, y))).isPrev())) { //Si c'est la bonne porte
                            player.setPosition(new Position(x, y));
                            break;
                        }
                    }
            }
        }
        world.setChanged(true);
    }

    public int getInitPlayerLives() {
        return initPlayerLives;
    }

    private void loadConfig(String path) {
        try (InputStream input = new FileInputStream(new File(path, "config.properties"))) {
            Properties prop = new Properties();
            // load the configuration file
            prop.load(input);
            initPlayerLives = Integer.parseInt(prop.getProperty("lives", "3"));
            manager.setPrefix(prop.getProperty("prefix", "level"));
            manager.setMaxLevel(Integer.parseInt(prop.getProperty("levels", "3")));
        } catch (IOException ex) {
            System.err.println("Error loading configuration");
        }
    }

    public World getWorld() {
        return world;
    }

    public Player getPlayer() {
        return this.player;
    }


    public boolean isToChange() {
        return toChange;
    }

    public void setToChange(boolean toChange) {
        this.toChange = toChange;
    }


    public WorldManager getWorldManager() {
        return manager;
    }
}
