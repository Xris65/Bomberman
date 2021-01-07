package fr.ubx.poo.model.go.character;

import fr.ubx.poo.game.Direction;
import fr.ubx.poo.game.Game;
import fr.ubx.poo.game.Position;
import fr.ubx.poo.game.World;
import fr.ubx.poo.model.Entity;
import fr.ubx.poo.model.Movable;
import fr.ubx.poo.model.decor.Decor;
import fr.ubx.poo.model.go.GameObject;

public class Character extends GameObject implements Movable {
    private int lives;
    protected boolean moveRequested = false;
    protected Direction direction;

    public Character(Game game, Position position, Direction direction) {
        super(game, position);
        this.direction = direction;
    }

    public void loseLife(){
        lives--;
    }

    public void addLife(){
        lives++;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public int getLives() {
        return lives;
    }

    public boolean isAlive() {
        return lives > 0;
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
        Decor decor = world.get(nextPosition);
        return world.isInside(nextPosition) && (decor == null || decor.isWalkable(this));
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
