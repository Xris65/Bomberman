package fr.ubx.poo.model.go.character;

import fr.ubx.poo.game.Direction;
import fr.ubx.poo.game.Game;
import fr.ubx.poo.game.Position;
import fr.ubx.poo.game.World;

import java.util.ArrayList;
import java.util.Random;

public class Monster extends Character {

    // time to move in ms
    public Monster(Game game, Position position, int timeToMove){
        super(game, position, Direction.random());
        super.setTimeToAct(timeToMove);
        setLives(1);
    }

    public ArrayList<Direction> validMovements(){
        ArrayList<Direction> possibleMoveDirections = new ArrayList<>();
        for(Direction d : Direction.values()) {
            if(canMove(d)){
                possibleMoveDirections.add(d);
            }
        }
        return possibleMoveDirections;
    }

    public void requestMove() {
        // creates a list of possible movements
        ArrayList<Direction> possibleMoves = validMovements();
        int movesSize = possibleMoves.size();
        if(movesSize != 0){ // to prevent out of bound in empty array
            // gets a random move in possible moves
            // prevents losing time with random
            this.direction = possibleMoves.get(new Random().nextInt(movesSize));
        }
        this.moveRequested = true;
    }

    public void update(long now) {
        // Every getTimeToAct(), here 1 s, request a move
        super.actionIfTime(now, ()->{
            this.requestMove();
            setLastActionTime(now);
        });
        super.update(now);
    }

    public Direction getDirection() {
        return direction;
    }


}
