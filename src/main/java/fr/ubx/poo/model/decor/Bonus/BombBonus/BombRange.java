package fr.ubx.poo.model.decor.Bonus.BombBonus;

import fr.ubx.poo.model.go.character.Player;

public class BombRange extends BombBonus {
    public BombRange(boolean increase) {
        super(increase);
    }

    @Override
    public void obtain(Player player) {
        super.obtainWithRunnable(player::addBombRange, player::removeBombRange);
    }
}
