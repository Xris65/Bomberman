package fr.ubx.poo.model.go;

import fr.ubx.poo.game.Direction;
import fr.ubx.poo.game.Game;
import fr.ubx.poo.game.Position;
import fr.ubx.poo.model.go.character.Monster;
import fr.ubx.poo.model.go.character.Player;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class BombObject extends GameObject {

    private int bombPhase = 1;
    public Timer timer = new Timer();


    // 5 images, 6 eme étape c'est la destruction de la bombe

    public void startTimer(Player player){
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(bombPhase < 6) {
                    bombPhase++;
                }
                else {
                    timer.cancel();
                }
            }
        }, 1000, 1000);
    }


    public int getBombPhase() {
        return bombPhase;
    }



    public BombObject(Game game, Position position) {
        super(game, position);
    }

}
