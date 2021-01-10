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

/**
 * The type Game.
 */
public class Game {

    private World world;



    private final WorldManager manager;
    private final Player player;
    /**
     * The Init player lives.
     */
    public int initPlayerLives = 1;
    private boolean toChange = false;


    /**
     * Instantiates a new Game.
     *
     * @param worldPath the world path
     */
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

    public void setWorld(World world) {
        this.world = world;
    }


    /**
     * Gets init player lives.
     *
     * @return the init player lives
     */
    public int getInitPlayerLives() {
        return initPlayerLives;
    }

    /**
     * Loads the configuration from the given path.
     * @param path the path.
     */
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

    /**
     * Gets world.
     *
     * @return the world
     */
    public World getWorld() {
        return world;
    }

    /**
     * Gets player.
     *
     * @return the player
     */
    public Player getPlayer() {
        return this.player;
    }


    /**
     * Confirms if the world has changed or not since last iteration.
     *
     * @return true if the world has changed, false if not.
     */
    public boolean isToChange() {
        return toChange;
    }

    /**
     * Sets if the world has changed or not.
     *
     * @param toChange boolean if the world has changed or not.
     */
    public void setToChange(boolean toChange) {
        this.toChange = toChange;
    }


    /**
     * Gets the world manager.
     *
     * @return the world manager.
     */
    public WorldManager getWorldManager() {
        return manager;
    }
}
