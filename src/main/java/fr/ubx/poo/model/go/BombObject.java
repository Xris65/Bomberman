package fr.ubx.poo.model.go;

import fr.ubx.poo.game.Direction;
import fr.ubx.poo.game.Game;
import fr.ubx.poo.game.Position;
import fr.ubx.poo.model.go.character.Monster;
import fr.ubx.poo.model.go.character.Player;
import fr.ubx.poo.view.image.ImageFactory;
import fr.ubx.poo.view.sprite.Sprite;
import fr.ubx.poo.view.sprite.SpriteExplosion;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class BombObject extends GameObject {
    int range;

    private int bombPhase = 1;

    @Override
    public void update(long now) {
        super.actionIfTime(now, () -> {
            if(bombPhase <= 4){
                bombPhase++;
            }
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
        this.range = range;
        super.setTimeToAct(1000); // bomb changes phase every 1000 ms
        super.setLastActionTime(now);
    }

}
