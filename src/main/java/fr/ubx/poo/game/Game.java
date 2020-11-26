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

    private final String worldPath;
    private World world;
    private ArrayList<World> worlds = new ArrayList<>();
    private final Player player;
    private int worldIndex = 1;
    public int initPlayerLives = 1;
    ArrayList<Monster> monsters;
    private boolean returning = false;
    private boolean toChange = false;



    public Game(String worldPath) {
        WorldLoader loader = new WorldLoader();
        world = loader.readFromFile("level1.txt");
        //world = new WorldStatic();
        this.worldPath = worldPath;
        loadConfig(worldPath);
        Position positionPlayer = null;
        try {
            positionPlayer = world.findPlayer();
            player = new Player(this, positionPlayer);
        } catch (PositionNotFoundException e) {
            System.err.println("Position not found : " + e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }
    public void changeWorld(){
        WorldLoader loader = new WorldLoader();
        if(returning){
            world = worlds.get(worldIndex-1);
            returning = false;
        }else {
            worlds.add(world);
            if (worldIndex == 1)
                world = loader.readFromFile("level1.txt");
            if (worldIndex == 2)
                world = loader.readFromFile("level2.txt");
            if (worldIndex == 3)
                world = loader.readFromFile("level3.txt");
        }
        Dimension dimension = world.dimension;
        for (int x = 0; x < dimension.width; x++) {
            for (int y = 0; y < dimension.height; y++) {
                if (world.get(new Position(x, y)) instanceof Door)
                    if (!((Door) world.get(new Position(x, y))).isClosed()) {
                        player.setPosition(new Position(x, y));
                        break;
                    }
            }
        }
        world.setChanged(true);

    }


    public ActionTimer setMonsters(ArrayList<Monster> monsters, Stage stage) {
        ActionTimer timer = new ActionTimer();
        this.monsters = monsters;
        timer.startTimer(1, monsters, stage);
        return timer;
    }

    public int getInitPlayerLives() {
        return initPlayerLives;
    }
    /*public void createExplosions(ArrayList<SpriteExplosion> explosions, Pane layer, Player player) {
        Image explosionImage = ImageFactory.getInstance().getBomb(5);
        Position playerPosition = player.getPosition();
        SpriteExplosion explosionN;
        SpriteExplosion explosionE;
        SpriteExplosion explosionS;
        SpriteExplosion explosionW;
        for (int i = 1; i <= player.getRange(); i++) {
            explosionN = new SpriteExplosion(layer, explosionImage,new Position(playerPosition.x, playerPosition.y-i));
            explosionE = new SpriteExplosion(layer, explosionImage,new Position(playerPosition.x+i, playerPosition.y));
            explosionS = new SpriteExplosion(layer, explosionImage,new Position(playerPosition.x, playerPosition.y+i));
            explosionW = new SpriteExplosion(layer, explosionImage,new Position(playerPosition.x-i, playerPosition.y));
            explosions.add(explosionN);
            explosions.add(explosionE);
            explosions.add(explosionS);
            explosions.add(explosionW);
        }
    }*/


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


    public int getWorldIndex() {
        return worldIndex;
    }

    public void setWorldIndex(int worldIndex) {
        this.worldIndex = worldIndex;
    }

    public boolean isToChange() {
        return toChange;
    }

    public void setToChange(boolean toChange) {
        this.toChange = toChange;
    }

    public boolean isReturning() {
        return returning;
    }

    public void setReturning(boolean returning) {
        this.returning = returning;
    }



}
