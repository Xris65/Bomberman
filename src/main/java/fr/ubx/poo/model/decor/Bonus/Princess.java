package fr.ubx.poo.model.decor.Bonus;

import fr.ubx.poo.model.go.character.Player;

public class Princess extends Bonus{

    @Override
    public boolean isToRemove() {
        return false;
    }

    @Override
    public void obtain(Player player) {
        player.wins();
    }
}
