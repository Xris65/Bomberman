/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.game;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

import fr.ubx.poo.model.go.character.Monster;
import fr.ubx.poo.model.go.character.Player;
import fr.ubx.poo.view.image.ImageFactory;
import fr.ubx.poo.view.sprite.SpriteExplosion;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

import javax.swing.*;

public class Game {

    private final World world;
    private final Player player;
    private final String worldPath;
    public int initPlayerLives = 1;
    ArrayList<Monster> monsters;
    public int stageNumber;

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

    public void changeStage(int stageNumber) {
        //world = world.loadWorld(stageNumber);
        monsters = world.findMonsters(this);
        ActionTimer.stopTimer();
        //timer.startTimer(1, monsters);
    }
    public void startTimer(int seconds){
        ActionTimer.startTimer(2, monsters);
    }
    public void setMonsters(ArrayList<Monster> monsters) {
        this.monsters = monsters;
        ActionTimer.startTimer(1, monsters);
    }
    public void end() {
        ActionTimer.stopTimer();
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


}
