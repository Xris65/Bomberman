package fr.ubx.poo.model.go;

import fr.ubx.poo.game.Direction;
import fr.ubx.poo.game.Game;
import fr.ubx.poo.game.Position;
import fr.ubx.poo.game.World;
import fr.ubx.poo.model.decor.Decor;

import java.util.ArrayList;

/**
 * The type Bomb object.
 */
public class BombObject extends GameObject {
    /**
     * Bomb explosion range.
     */
    int range;

    /**
     *
     */
    private final World world;

    /**
     * Gets world.
     *
     * @return the world
     */
    public World getWorld() {
        return world;
    }

    /**
     * Used to display the right sprite and to explode at the right time.
     */
    private int bombPhase = 1;

    /**
     * Sets bomb phase.
     *
     * @param bombPhase the bomb phase
     */
    public void setBombPhase(int bombPhase) {
        this.bombPhase = bombPhase;
    }

    /**
     * Update the bomb.
     * The Runnable adds 1 to the bombPhase each time 1 second has passed.
     * If bombPhase goes to 5, bomb explodes.
     * @param now the time of the frame
     */
    @Override
    public void update(long now) {
        super.actionIfTime(now, () -> {
            if (bombPhase <= 4) {
                bombPhase++;
            }
            setLastActionTime(now);
        });
    }

    /**
     * Gets bomb phase.
     *
     * @return the bomb phase
     */
    public int getBombPhase() {
        return bombPhase;
    }

    /**
     * Gets range.
     *
     * @return the range
     */
    public int getRange() {
        return range;
    }


    /**
     * Instantiate a new BombObject.
     * @param game the game to instantiate the gameObject.
     * @param position position of the bomb.
     * @param range range of the bomb.
     * @param now time of the frame used to calculate how much time passes for
     *            the bomb phases.
     */
    public BombObject(Game game, Position position, int range, long now) {
        super(game, position);
        this.world = game.getWorld();
        this.range = range;
        super.setTimeToAct(1000); // bomb changes phase every 1000 ms
        super.setLastActionTime(now);
    }

    /**
     * Explodes the bomb.
     * @param now time of the frame.
     * @param bombZone ArrayList of the positions the bomb exploded at.
     *                 Used to interact with the entities at the given positions.
     */
    public void explode(long now, ArrayList<Position> bombZone) {
        Position p = getPosition();
        game.getWorld().clear(p);
        game.getWorld().getExplosions().add(new ExplosionObject(game, p, getRange(), now, bombZone, world));
    }

    /**
     * This function maps the area the bomb exploded at.
     * It is used by the ExplosionObject to interact with the map, monsters and player,
     * but also by the SpriteExplosion to display explosions at the right spot.
     *
     * It goes in all directions, and registers the area it can explode in in an ArrayList.
     * The explosion stops if it encounters a decor that stops the bomb explosion.
     * If this decor is destroyable, it is added anyway to display the explosion in the game engine.
     * @return the arraylist containing the positions the bomb exploded at.
     */
    public ArrayList<Position> getBombZone() {
        ArrayList<Position> positionArrayList = new ArrayList<>();
        positionArrayList.add(getPosition());
        for (Direction d : Direction.values()) {  // for each direction
            Position initialPosition = getPosition(); // resets starting point at the center before each for call
            for (int i = 0; i < range; i++) { // range times
                initialPosition = d.nextPosition(initialPosition);

                if (!world.isInside(initialPosition)) {
                    break;
                }
                Decor decor = world.get(initialPosition);
                if (decor != null) {
                    if (decor.stopsBombExplosion()) {
                        if (decor.isDestroyable()) {
                            positionArrayList.add(initialPosition);
                            break;
                        }
                        break;
                    } else {
                        positionArrayList.add(initialPosition);
                    }
                } else {
                    positionArrayList.add(initialPosition);
                }
            }
        }
        return positionArrayList;
    }
}