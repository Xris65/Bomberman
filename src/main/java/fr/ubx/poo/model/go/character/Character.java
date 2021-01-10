package fr.ubx.poo.model.go.character;

import fr.ubx.poo.game.Direction;
import fr.ubx.poo.game.Game;
import fr.ubx.poo.game.Position;
import fr.ubx.poo.game.World;
import fr.ubx.poo.model.Movable;
import fr.ubx.poo.model.decor.Decor;
import fr.ubx.poo.model.go.GameObject;

/**
 * The type Character.
 */
public class Character extends GameObject implements Movable {

    /**
     * The number of lives.
     */
    private int lives;
    /**
     * The Move requested.
     */
    protected boolean moveRequested = false;
    /**
     * The Direction.
     */
    protected Direction direction;

    /**
     * Instantiates a new Character.
     *
     * @param game      the game
     * @param position  its position
     * @param direction its direction
     */
    public Character(Game game, Position position, Direction direction) {
        super(game, position);
        this.direction = direction;
    }

    /**
     * Lose life.
     */
    public void loseLife() {
        lives--;
    }

    /**
     * Add life.
     */
    public void addLife() {
        lives++;
    }

    /**
     * Sets lives.
     *
     * @param lives the lives
     */
    public void setLives(int lives) {
        this.lives = lives;
    }

    /**
     * Gets lives.
     *
     * @return the lives
     */
    public int getLives() {
        return lives;
    }

    /**
     * Is alive boolean.
     *
     * @return if character is alive.
     */
    public boolean isAlive() {
        return lives > 0;
    }

    /**
     * Request a move.
     *
     * @param direction the direction
     */
    public void requestMove(Direction direction) {
        if (direction != this.direction) {
            this.direction = direction;
        }
        moveRequested = true;
    }

    /**
     * Gets current direction.
     *
     * @return the direction
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * Can move boolean.
     *
     * @param direction the direction
     * @param world     the world
     * @return if the player can move in this direction in this world
     */
    public boolean canMove(Direction direction, World world) {
        Position nextPosition = direction.nextPosition(getPosition());
        Decor decor = world.get(nextPosition);
        return world.isInside(nextPosition) && (decor == null || decor.isWalkable(this));
    }
    /**
     * Can move boolean.
     *
     * @param direction the direction
     */
    @Override
    public boolean canMove(Direction direction) {
        return canMove(direction, game.getWorld());
    }

    /**
     * Moves to the given direction.
     * @param direction the direction to move at.
     */
    public void doMove(Direction direction) {
        Position nextPos = direction.nextPosition(getPosition());
        setPosition(nextPos);
    }

    /**
     * Update the character on each frame of the game.
     * @param now the time of the frame.
     */
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
