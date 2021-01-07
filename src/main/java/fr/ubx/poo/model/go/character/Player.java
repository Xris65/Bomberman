/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.model.go.character;

import fr.ubx.poo.game.*;
import fr.ubx.poo.model.decor.*;

public class Player extends Character {
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
        Decor decor = w.get(boxAt);
        if(decor != null) {
            decor.move(this);
        }
    }

    public Player(Game game, Position position) {
        super(game, position, Direction.S);
        setLives(game.getInitPlayerLives());
        setTimeToAct(1000); // time for the invulnerability to wear off
    }

    public boolean isVulnerable() {
        return !isInvulnerable;
    }

    public void loseLife(long now) {
        loseLife();
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
        return d.isWalkable(this);
    }

    @Override
    public boolean canMove(Direction direction) {
        World w = super.game.getWorld();
        Position p = direction.nextPosition(super.getPosition());
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

}
