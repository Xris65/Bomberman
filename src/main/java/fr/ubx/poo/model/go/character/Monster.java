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

    public Monster(Game game, Position position) {
        super(game, position);
    }

    public void requestMove() {
        while(canMove(direction) == false)
            this.direction = Direction.random();
        moveRequested = true;
    }

    public void update(long now) {
        if (moveRequested) {
            if (canMove(direction)) {
                doMove(direction);
            }
        }
        moveRequested = false;
    }

    @Override
    public boolean canMove(Direction direction) {
        World w = super.game.getWorld();
        Position p = direction.nextPosition(super.getPosition());

        boolean inMap = ((p.x >= 0) && (p.x < w.dimension.width) && (p.y >= 0) && (p.y < w.dimension.height));
        Entity targetPosition = w.get(p);
        boolean isWalkable = targetPosition == null || targetPosition instanceof Bonus;
        return inMap && isWalkable;
    }
    public void doMove(Direction direction) {
        Position nextPos = direction.nextPosition(getPosition());
        setPosition(nextPos);
    }

    public Direction getDirection(){
        return direction;
    }


}
