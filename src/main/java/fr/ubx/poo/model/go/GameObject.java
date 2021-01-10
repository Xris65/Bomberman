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
    /**
     * The Game.
     */
    protected final Game game;

    /**
     * Its position.
     */
    private Position position;

    /**
     * This field is the same type as 'long now' in update loops,
     * It remembers the 'now' variable at the time of the last action.
     */
    private long lastActionTime = 0;

    /**
     * Time in milliseconds between actions.
     */
    private int timeToAct;

    /**
     * Gets last action time.
     *
     * @return the last action time
     */
    public long getLastActionTime() {
        return lastActionTime;
    }

    /**
     * Sets last action time.
     *
     * @param lastActionTime the last action time
     */
    public void setLastActionTime(long lastActionTime) {
        this.lastActionTime = lastActionTime;
    }

    /**
     * Gets time to act.
     *
     * @return the time to act
     */
    public int getTimeToAct() {
        return timeToAct;
    }

    /**
     * Sets time to act.
     *
     * @param timeToAct the time to act
     */
    public void setTimeToAct(int timeToAct) {
        this.timeToAct = timeToAct;
    }

    /**
     * Action if time.
     *
     * @param now      current time
     * @param runnable function to execute if it is time
     */
    public void actionIfTime(long now, Runnable runnable) {
        if (getTimeToAct() != 0 && (now - getLastActionTime()) > (getTimeToAct() * 1000000L)) {
            runnable.run();
        }
    }

    /**
     * Update.
     *
     * @param now the now
     */
    public void update(long now) {

    }

    /**
     * Gets position.
     *
     * @return the position
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Sets position.
     *
     * @param position the position
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * Instantiates a new Game object.
     *
     * @param game     the game
     * @param position the position
     */
    public GameObject(Game game, Position position) {
        this.game = game;
        this.position = position;
    }

    /**
     * Gets game.
     *
     * @return the game
     */
    public Game getGame() {
        return game;
    }
}
