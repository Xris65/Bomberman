/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.model.go.character;

import fr.ubx.poo.game.*;
import fr.ubx.poo.model.Entity;
import fr.ubx.poo.model.Movable;
import fr.ubx.poo.model.decor.*;
import fr.ubx.poo.model.go.BombObject;
import fr.ubx.poo.model.go.GameObject;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Player extends Character {
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



    public void moveBoxIfAble(World w){
        Position boxAt = direction.nextPosition(getPosition());
        Entity e = w.get(boxAt);
        if(e instanceof Box){
            if (w.get(direction.nextPosition(boxAt)) == null && game.getWorld().isInside(direction.nextPosition(boxAt))){
                // can move
                for(Monster m : w.getMonsters()){
                    if (m.getPosition().equals(direction.nextPosition(boxAt))){
                        return;
                    }
                }
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
        else if (d instanceof Bonus) {
            Bonus myBonus = (Bonus) d;
            WorldEntity type = myBonus.getType();
            if(type == WorldEntity.BombRangeDec){
                if(range > 1)
                    range--;
            }
            else if(type == WorldEntity.BombRangeInc){
                range++;
            }
            else if(type == WorldEntity.BombNumberDec){
                if(bombCapacity > 1)
                    bombCapacity--;
            }
            else if(type == WorldEntity.BombNumberInc){
                bombCapacity++;
            }
            else if(type == WorldEntity.Heart){
                lives++;
            }
            else if(type == WorldEntity.Key){
                numberOfKeys++;
            }
            super.game.getWorld().clear(p);
            onBonus = true;
            return true;
        }
        else if (d instanceof Door){
            if(!((Door) d).isClosed()){
                game.changeWorld(!((Door) d).isPrev());
                game.setToChange(true);
                return false;
            }

        }
        return false;
    }

    @Override
    public boolean canMove(Direction direction) {
        World w = super.game.getWorld();
        Position p = direction.nextPosition(super.getPosition());
        boolean inMap = w.isInside(p);
        Entity targetPosition = w.get(p);
        boolean isWalkable = targetPosition == null || handleNewPosition(targetPosition, p);

        return inMap && isWalkable;
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
