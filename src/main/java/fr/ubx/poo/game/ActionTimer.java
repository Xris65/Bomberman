package fr.ubx.poo.game;

import fr.ubx.poo.model.go.character.Monster;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class ActionTimer {
    public Timer timer = new Timer();
    public void startTimer(int seconds, ArrayList<Monster> monsters, Stage stage){
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("" + stage.isShowing());
                if(!stage.isShowing())
                    stopTimer();
                for (Monster m : monsters) {
                    m.requestMove();
                }
            }
        }, 0, seconds*1000);
    }

    public void stopTimer(){
        timer.cancel();
    }

}
