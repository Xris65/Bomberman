package fr.ubx.poo.model.go.character;

import fr.ubx.poo.game.Direction;
import fr.ubx.poo.game.Game;
import fr.ubx.poo.game.Position;
import fr.ubx.poo.game.World;
import fr.ubx.poo.model.Entity;
import fr.ubx.poo.model.Movable;
import fr.ubx.poo.model.go.GameObject;

public class Character extends GameObject implements Movable {
    protected boolean moveRequested = false;
    protected Direction direction;

    public Character(Game game, Position position, Direction direction) {
        super(game, position);
        this.direction = direction;
    }

    public void requestMove(Direction direction) {
        if (direction != this.direction) {
            this.direction = direction;
        }
        moveRequested = true;
    }

    public Direction getDirection() {
        return direction;
    }

    @Override
    public boolean canMove(Direction direction) {
        World world = game.getWorld();
        Position nextPosition = direction.nextPosition(getPosition());
        Entity entity = world.get(nextPosition);
        return world.isInside(nextPosition) && (entity == null || entity.isWalkable());
    }

    public void doMove(Direction direction) {
        Position nextPos = direction.nextPosition(getPosition());
        setPosition(nextPos);
    }

    @Override
    public void update(long now) {
        if (moveRequested) {
            if (canMove(direction)) {
                doMove(direction);
            }
        }
        moveRequested = false;
    }
}
