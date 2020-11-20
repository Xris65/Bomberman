package fr.ubx.poo.game;

import fr.ubx.poo.model.go.character.Monster;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class ActionTimer {
    public static Timer timer = new Timer();
    public static void startTimer(int seconds, ArrayList<Monster> monsters){
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                for (Monster m : monsters) {
                    System.out.println("Request monster move");
                    m.requestMove();
                }
            }
        }, 0, seconds*1000);
        System.out.println("Timer started");
    }

    public static void stopTimer(){
        System.out.println("Timer stopped");
        timer.cancel();
    }

}
