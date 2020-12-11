/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.model.go.character;

import fr.ubx.poo.game.*;
import fr.ubx.poo.model.Entity;
import fr.ubx.poo.model.decor.*;
import fr.ubx.poo.model.decor.Bonus.Bonus;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Player extends Character {
    private int lives;
    private boolean winner;
    private boolean isInvulnerable = false;

    private int bombCapacity = 1;
    private int numberOfBombs = 0;
    private int bombRange = 1;
    private int numberOfKeys = 0;

    public void wins(){
        winner = true;
    }

    public void addBomb(){
        numberOfBombs--;
    }
    public void removeBomb(){
        numberOfBombs++;
    }

    public void addBombCapacity(){
        bombCapacity++;
    }
    public void removeBombCapacity(){
        if (bombCapacity > 1)
            bombCapacity--;
    }

    public void removeKey() {
        numberOfKeys--;
    }
    public void addKey() {
        numberOfKeys++;
    }

    public void addBombRange(){bombRange++;}
    public void removeBombRange(){
        if (bombRange > 1)
            bombRange--;
    }

    public void addLife(){
        lives++;
    }

    public int getNumberOfKeys() {
        return numberOfKeys;
    }

    public int getNumberOfBombs() {
        return numberOfBombs;
    }

    public int getBombCapacity() {
        return bombCapacity;
    }

    public int getBombRange() {
        return bombRange;
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

    public Player(Game game, Position position) {
        super(game, position, Direction.S);
        this.lives = game.getInitPlayerLives();
        setTimeToAct(1000);
    }

    public int getLives() {
        return lives;
    }

    public boolean isPlayerVulnerable() {
        return !isInvulnerable;
    }

    public void loseLife(long now) {
        lives--;
        isInvulnerable = true;
        setLastActionTime(now);
    }

    private boolean handleNewPosition(Decor d, Position p) {
        d.obtain(this);
        if(d.isToRemove()) {
            game.getWorld().clear(p);
        }

        if (d instanceof Door){
            if(!((Door) d).isClosed()){
                game.changeWorld(!((Door) d).isPrev());
                game.setToChange(true);
                return false;
            }

        }
        return d.isWalkable();
    }

    @Override
    public boolean canMove(Direction direction) {
        World w = super.game.getWorld();
        Position p = direction.nextPosition(super.getPosition());
        System.out.println(getPosition());
        System.out.println(direction);
        boolean inMap = w.isInside(p);
        Decor targetPosition = w.get(p);
        boolean isWalkable = targetPosition == null || handleNewPosition(targetPosition, p);

        return inMap && isWalkable;
    }


    public void update(long now) {
        if(isInvulnerable){
            actionIfTime(now, () -> {
                isInvulnerable = false;
            });
        }
        super.update(now);
    }


    public boolean isWinner() {
        return winner;
    }

    public boolean isAlive() {
        return lives > 0;
    }

}
