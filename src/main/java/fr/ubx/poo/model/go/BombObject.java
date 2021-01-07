package fr.ubx.poo.model.go;

import fr.ubx.poo.game.Direction;
import fr.ubx.poo.game.Game;
import fr.ubx.poo.game.Position;
import fr.ubx.poo.game.World;
import fr.ubx.poo.model.decor.Decor;
import fr.ubx.poo.model.go.character.Monster;
import fr.ubx.poo.model.go.character.Player;
import fr.ubx.poo.view.image.ImageFactory;
import fr.ubx.poo.view.sprite.Sprite;
import fr.ubx.poo.view.sprite.SpriteExplosion;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class BombObject extends GameObject {
    int range;
    private World world;

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

    public void explode(long now) {
        Position p = getPosition();
        game.getWorld().clear(p);
        game.getWorld().getExplosions().add(new Explosion(game, p, getRange(), now, getBombZone(), world));
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
                    if (!decor.stopsBombExplosion() || (decor.stopsBombExplosion() && decor.stopsBombExplosion())) {
                        positionArrayList.add(initialPosition);
                    }
                    if (decor.stopsBombExplosion()) {
                        break;
                    }
                }
            }
        }
        return positionArrayList;
    }
}