package fr.ubx.poo.model.go;

import fr.ubx.poo.game.Direction;
import fr.ubx.poo.game.Game;
import fr.ubx.poo.game.Position;
import fr.ubx.poo.game.World;
import fr.ubx.poo.model.decor.Decor;

import java.util.ArrayList;

public class BombObject extends GameObject {
    int range;
    private final World world;

    public World getWorld() {
        return world;
    }

    private int bombPhase = 1;

    @Override
    public void update(long now) {
        super.actionIfTime(now, () -> {
            if (bombPhase <= 4) {
                bombPhase++;
            }
            setLastActionTime(now);
        });
    }

    public int getBombPhase() {
        return bombPhase;
    }

    public int getRange() {
        return range;
    }

    public BombObject(Game game, Position position, int range, long now) {
        super(game, position);
        this.world = game.getWorld();
        this.range = range;
        super.setTimeToAct(1000); // bomb changes phase every 1000 ms
        super.setLastActionTime(now);
    }

    public void explode(long now, ArrayList<Position> bombZone) {
        Position p = getPosition();
        game.getWorld().clear(p);
        game.getWorld().getExplosions().add(new ExplosionObject(game, p, getRange(), now, bombZone, world));
    }

    public ArrayList<Position> getBombZone() {
        ArrayList<Position> positionArrayList = new ArrayList<>();
        positionArrayList.add(getPosition());
        for (Direction d : Direction.values()) {  // for each direction
            Position initialPosition = getPosition();
            for (int i = 0; i < range; i++) { // a la distance i
                initialPosition = d.nextPosition(initialPosition);
                Decor decor = world.get(initialPosition);
                if(decor!= null){
                    if(decor.stopsBombExplosion()){
                        if(decor.isDestroyable()) {
                            System.out.println("This is a box, it stops at direction " + d);
                            positionArrayList.add(initialPosition);
                            break;
                        }
                        System.out.println("Stops explosion at direction " + d);
                        break;
                    } else {
                        System.out.println("Doesn't stop explosion at direction " + d);
                        positionArrayList.add(initialPosition);
                    }
                } else {
                    System.out.println("Empty");
                    positionArrayList.add(initialPosition);
                }
            }
        }
        return positionArrayList;
    }
}