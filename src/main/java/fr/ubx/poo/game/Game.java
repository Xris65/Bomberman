/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.game;


import fr.ubx.poo.model.Entity;
import fr.ubx.poo.model.decor.Door;
import fr.ubx.poo.model.go.character.Monster;
import fr.ubx.poo.model.go.character.Player;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

public class Game {

    private World world;
    private final ArrayList<World> worlds = new ArrayList<>();
    private final WorldManager manager;
    private final Player player;
    public int initPlayerLives = 1;
    ArrayList<Monster> monsters;
    private boolean toChange = false;


    public Game(String worldPath) {
        manager = new WorldManager(worldPath);
        world = manager.getNextWorld(this);
        loadConfig(worldPath);
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
        world = (goingUp) ? manager.getNextWorld(this) : manager.getPreviousWorld();
        Dimension dimension = world.dimension;
        for (int x = 0; x < dimension.width; x++) {
            for (int y = 0; y < dimension.height; y++) {
                if (world.get(new Position(x, y)) instanceof Door)
                    if (!((Door) world.get(new Position(x, y))).isClosed()) {
                        if ((goingUp && ((Door) world.get(new Position(x, y))).isPrev())
                           || !(goingUp) && !(((Door) world.get(new Position(x, y))).isPrev())){ //Si c'est la bonne porte
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

    public ArrayList<World> getWorlds() {
        return worlds;
    }


    public WorldManager getWorldManager() {
        return manager;
    }
}
