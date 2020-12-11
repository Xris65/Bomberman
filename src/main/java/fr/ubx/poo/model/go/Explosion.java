package fr.ubx.poo.model.go;

import fr.ubx.poo.game.*;
import fr.ubx.poo.model.Entity;
import fr.ubx.poo.model.decor.Bomb;
import fr.ubx.poo.model.decor.Bonus.Bonus;
import fr.ubx.poo.model.decor.Bonus.Key;
import fr.ubx.poo.model.decor.Decor;
import fr.ubx.poo.model.go.character.Player;

import java.util.concurrent.atomic.AtomicBoolean;

public class Explosion extends GameObject {
    int range;

    public Explosion(Game game, Position position, int range, long now) {
        super(game, position);
        this.range = range;
        damageEntities(now);
    }

    private void damageEntities(long now) {
        World w = game.getWorld();

        // checks if player is on top of the bomb
        Player player = game.getPlayer();
        if(player.getPosition().equals(getPosition())){
            player.loseLife();
        }

        for (Direction d : Direction.values()) {  // for each direction
            Position initialPosition = getPosition();
            for (int i = 0; i < range; i++) { // a la distance i
                initialPosition = d.nextPosition(initialPosition);

                Decor decor = w.get(initialPosition);
                if (decor != null) {
                    if (decor.isDestroyable()) {
                        w.clear(initialPosition);
                    }
                    if(decor.stopsBombExplosion()) {
                        break;
                    }
                }
                if(player.getPosition().equals(getPosition())){
                    player.loseLife();
                }

                Position finalInitialPosition = initialPosition; // needed for lambda expression
                AtomicBoolean leave = new AtomicBoolean(false);

                w.getMonsters().forEach(self -> {
                    if (self.getPosition().equals(finalInitialPosition)) {
                        self.loseLife();
                        leave.set(true);

                    }
                });
                if (leave.get()) {
                    break;
                }

            }
        }


    }
}
