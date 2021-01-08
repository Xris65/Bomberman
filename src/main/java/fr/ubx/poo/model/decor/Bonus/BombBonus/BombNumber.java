package fr.ubx.poo.model.decor.Bonus.BombBonus;

import fr.ubx.poo.model.go.character.Player;

public class BombNumber extends BombBonus {
    public BombNumber(boolean increase) {
        super(increase);
    }

    @Override
    public void obtain(Player player) {
        super.obtainWithRunnable(player::addBombCapacity, player::removeBombCapacity);
    }
}
