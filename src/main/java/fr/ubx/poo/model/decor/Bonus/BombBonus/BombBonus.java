package fr.ubx.poo.model.decor.Bonus.BombBonus;

import fr.ubx.poo.model.decor.Bonus.Bonus;
import fr.ubx.poo.model.go.character.Player;

/**
 * The type Bomb bonus.
 */
public abstract class BombBonus extends Bonus {
    /**
     * The Increase.
     */
    boolean increase;

    /**
     * Is increase boolean.
     *
     * @return the boolean
     */
    public boolean isIncrease() {
        return increase;
    }

    /**
     * Instantiates a new Bomb bonus.
     *
     * @param increase the increase
     */
    public BombBonus(boolean increase) {
        this.increase = increase;
    }

    /**
     * Obtain with runnable.
     *
     * @param runnableTrue  the runnable true
     * @param runnableFalse the runnable false
     */
    public void obtainWithRunnable(Runnable runnableTrue, Runnable runnableFalse) {
        if (increase) {
            runnableTrue.run();
        } else {
            runnableFalse.run();
        }
    }

    @Override
    abstract public void obtain(Player player);
}
