package fr.ubx.poo.model.decor.Bonus.BombBonus;

import fr.ubx.poo.model.decor.Bonus.Bonus;
import fr.ubx.poo.model.go.character.Player;

public abstract class BombBonus extends Bonus {
    boolean increase;

    public boolean isIncrease() {
        return increase;
    }

    public BombBonus(boolean increase) {
        this.increase = increase;
    }

    public void obtainWithRunnable(Runnable runnableTrue, Runnable runnableFalse){
        if(increase){
            runnableTrue.run();
        } else {
            runnableFalse.run();
        }
    }

    @Override
    abstract public void obtain(Player player);
}
