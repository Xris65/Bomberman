/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.model.go.character;

import fr.ubx.poo.game.*;
import fr.ubx.poo.model.Entity;
import fr.ubx.poo.model.Movable;
import fr.ubx.poo.model.decor.Bonus;
import fr.ubx.poo.model.decor.Princess;
import fr.ubx.poo.model.go.GameObject;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Player extends GameObject implements Movable {

    private final boolean alive = true;
    Direction direction;
    private boolean moveRequested = false;
    private int lives;
    private boolean winner;
    private boolean isInvulnerable = false;
    private boolean onBonus = false;

    public boolean isOnBonus() {
        return onBonus;
    }

    public void setOnBonus(boolean onBonus) {
        this.onBonus = onBonus;
    }

    public Player(Game game, Position position) {
        super(game, position);
        this.direction = Direction.S;
        this.lives = game.getInitPlayerLives();
    }

    public int getLives() {
        return lives;
    }

    public boolean isPlayerVulnerable() {
        return !isInvulnerable;
    }

    public void loseLife() {
        lives--;
        isInvulnerable = true;
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        Runnable task1 = () -> isInvulnerable = false;
        executor.schedule(task1, 1, TimeUnit.SECONDS);
        executor.shutdown();
    }

    public Direction getDirection() {
        return direction;
    }

    public void requestMove(Direction direction) {
        if (direction != this.direction) {
            this.direction = direction;
        }
        moveRequested = true;
    }

    private boolean handleNewPosition(Entity d, Position p) {
        if (d instanceof Princess) {
            this.winner = true;
            super.game.changeStage(2);
            return true;
        }
        if (d instanceof Monster) {
            return true;
        }
        if (d instanceof Bonus) {
            super.game.getWorld().clear(p);
            onBonus = true;
            return true;
        }

        return false;
    }

    @Override
    public boolean canMove(Direction direction) {
        World w = super.game.getWorld();
        Position p = direction.nextPosition(super.getPosition());
        boolean inMap = ((p.x >= 0) && (p.x < w.dimension.width) && (p.y >= 0) && (p.y < w.dimension.height));
        Entity targetPosition = w.get(p);
        boolean isWalkable = targetPosition == null || handleNewPosition(targetPosition, p);

        return inMap && isWalkable;
    }

    public void doMove(Direction direction) {
        Position nextPos = direction.nextPosition(getPosition());
        setPosition(nextPos);
    }

    public void update(long now) {
        if (moveRequested) {
            if (canMove(direction)) {
                doMove(direction);
            }
        }
        moveRequested = false;
    }

    public boolean isWinner() {
        return winner;
    }

    public boolean isAlive() {
        return lives > 0;
    }

}
