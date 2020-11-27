package fr.ubx.poo.model.go.character;

import fr.ubx.poo.game.Direction;
import fr.ubx.poo.game.Game;
import fr.ubx.poo.game.Position;
import fr.ubx.poo.game.World;
import fr.ubx.poo.model.Entity;
import fr.ubx.poo.model.Movable;
import fr.ubx.poo.model.decor.Bonus;
import fr.ubx.poo.model.go.GameObject;

public class Monster extends GameObject implements Movable {

    private boolean alive = true;
    Direction direction = Direction.random();
    private boolean moveRequested = false;
    private int lives = 1;
    long lastActionTime = 0;
    int timeToMove;
    private final World world;


    public Monster(Game game, Position position, int timeToMove, World w) {
        super(game, position);
        this.timeToMove = timeToMove;
        this.world = w;
    }

    public boolean isThereAValidMovement() {
        for (Direction d : Direction.values()) {
            if (canMove(d))
                return true;
        }
        return false;
    }

    public void requestMove() {
        if (!isThereAValidMovement())
            return;
        while (!canMove(direction))
            this.direction = Direction.random();
        moveRequested = true;
    }


    public void update(long now) {
        if ((now - lastActionTime) > timeToMove * 1000000) {
            lastActionTime = now;
            requestMove();
        }
        if (moveRequested) {
            if (canMove(direction)) {
                doMove(direction);
            }
        }
        moveRequested = false;
    }

    @Override
    public boolean canMove(Direction direction) {
        Position p = direction.nextPosition(super.getPosition());
        Entity targetPosition = world.get(p);
        boolean isWalkable = targetPosition == null || targetPosition instanceof Bonus;
        return world.isInside(p) && isWalkable;
    }

    public void doMove(Direction direction) {
        Position nextPos = direction.nextPosition(getPosition());
        setPosition(nextPos);
    }

    public Direction getDirection() {
        return direction;
    }


}
