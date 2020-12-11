package fr.ubx.poo.model.decor.Bonus;

import fr.ubx.poo.model.go.character.Player;

public class Heart extends Bonus{
    @Override
    public void obtain(Player player) {
        player.addLife();
    }
}
