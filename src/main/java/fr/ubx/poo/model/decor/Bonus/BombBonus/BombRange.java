package fr.ubx.poo.model.decor.Bonus.BombBonus;

import fr.ubx.poo.model.go.character.Player;

/**
 * The type Bomb range.
 */
public class BombRange extends BombBonus {
    /**
     * Instantiates a new Bomb range.
     *
     * @param increase the increase
     */
    public BombRange(boolean increase) {
        super(increase);
    }

    /**
     * addBombRange if BombRange is a bonus, else removeBombRange.
     * @param player the player affected by the bonus.
     */
    @Override
    public void obtain(Player player) {
        super.obtainWithRunnable(player::addBombRange, player::removeBombRange);
    }
}
