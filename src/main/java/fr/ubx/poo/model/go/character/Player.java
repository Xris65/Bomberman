/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.model.go.character;

import fr.ubx.poo.game.*;
import fr.ubx.poo.model.Entity;
import fr.ubx.poo.model.Movable;
import fr.ubx.poo.model.decor.Bomb;
import fr.ubx.poo.model.decor.Bonus;
import fr.ubx.poo.model.decor.Box;
import fr.ubx.poo.model.decor.Princess;
import fr.ubx.poo.model.go.BombObject;
import fr.ubx.poo.model.go.GameObject;

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
    private int bombCapacity = 1;
    private int numberOfBombs = 0;
    private int range = 1;

    public void substractKey() {
        numberOfKeys--;
    }

    private int numberOfKeys = 0;

    public int getNumberOfKeys() {
        return numberOfKeys;
    }

    public int getNumberOfBombs() {
        return numberOfBombs;
    }

    public int getBombCapacity() {
        return bombCapacity;
    }
    public void addBomb(){
        numberOfBombs--;
    }

    public void removeBomb(){
        numberOfBombs++;
    }
    public int getRange() {
        return range;
    }

    private boolean isInMap(Position p){
        World w = game.getWorld();
        return ((p.x >= 0) && (p.x < w.dimension.width) && (p.y >= 0) && (p.y < w.dimension.height));
    }


    public void moveBoxIfAble(World w){
        Position boxAt = direction.nextPosition(getPosition());
        if(w.get(boxAt) instanceof Box){
            if (w.get(direction.nextPosition(boxAt)) == null && isInMap(direction.nextPosition(boxAt))){
                // can move
                w.set(direction.nextPosition(boxAt),new Box());
                w.clear(boxAt);
                w.setChanged(true);

            }
        }
    }



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
        executor.schedule(() -> isInvulnerable = false, 1, TimeUnit.SECONDS);
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
            return true;
        }
        if (d instanceof Bonus) {
            Bonus myBonus = (Bonus) d;
            if(myBonus.getType() == WorldEntity.BombRangeDec){
                if(range > 1)
                    range--;
            }
            if(myBonus.getType() == WorldEntity.BombRangeInc){
                range++;
            }
            if(myBonus.getType() == WorldEntity.BombNumberDec){
                if(bombCapacity > 1)
                    bombCapacity--;
            }
            if(myBonus.getType() == WorldEntity.BombNumberInc){
                bombCapacity++;
            }
            if(myBonus.getType() == WorldEntity.Heart){
                lives++;
            }
            if(myBonus.getType() == WorldEntity.Key){
                numberOfKeys++;
            }
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
        boolean inMap = isInMap(p);
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
