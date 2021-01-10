package fr.ubx.poo.model.decor.Bonus.BombBonus;

import fr.ubx.poo.model.decor.Bonus.Bonus;
import fr.ubx.poo.model.go.character.Player;

/**
 * The type Bomb bonus.
 */
public abstract class BombBonus extends Bonus {
    /**
     * If the bombBonus is a bonus or a malus.
     */
    boolean increase;

    /**
     * Returns true if the bomb is a bonus, else a malus
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
     * This functions executes runnableTrue if increase is true, else
     * executes runnableFalse.
     * It is very versatile, so it can be used in BombNumber and in BombRange.
     * @param runnableTrue  the runnable to execute if increase is true
     * @param runnableFalse the runnable to execute if increase is false
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
