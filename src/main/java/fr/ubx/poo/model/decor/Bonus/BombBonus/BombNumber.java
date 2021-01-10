package fr.ubx.poo.model.decor.Bonus.BombBonus;

import fr.ubx.poo.model.go.character.Player;

/**
 * The type Bomb number.
 */
public class BombNumber extends BombBonus {
    /**
     * Instantiates a new Bomb number.
     *
     * @param increase the increase
     */
    public BombNumber(boolean increase) {
        super(increase);
    }

    @Override
    public void obtain(Player player) {
        super.obtainWithRunnable(player::addBombCapacity, player::removeBombCapacity);
    }
}
