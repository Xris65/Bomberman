/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.model.go;

import fr.ubx.poo.game.Game;
import fr.ubx.poo.game.Position;
import fr.ubx.poo.model.Entity;

/***
 * A GameObject can access the game and knows its position in the grid.
 */
public abstract class GameObject extends Entity {
    protected final Game game;
    private Position position;
    private long lastActionTime = 0;
    private int timeToAct;

    public long getLastActionTime() {
        return lastActionTime;
    }

    public void setLastActionTime(long lastActionTime) {
        this.lastActionTime = lastActionTime;
    }

    public int getTimeToAct() {
        return timeToAct;
    }

    public void setTimeToAct(int timeToAct) {
        this.timeToAct = timeToAct;
    }

    public void actionIfTime(long now, Runnable runnable){
        if(getTimeToAct() != 0 && (now - getLastActionTime()) > (getTimeToAct() * 1000000L)){
            runnable.run();
        }
    }

    public void update(long now) {

    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public GameObject(Game game, Position position) {
        this.game = game;
        this.position = position;
    }
    public Game getGame(){
        return game;
    }
}
