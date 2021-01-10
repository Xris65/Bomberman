package fr.ubx.poo.model.go;

import fr.ubx.poo.game.Game;
import fr.ubx.poo.game.Position;
import fr.ubx.poo.game.World;
import fr.ubx.poo.model.decor.Decor;
import fr.ubx.poo.model.go.character.Monster;
import fr.ubx.poo.model.go.character.Player;

import java.util.ArrayList;

public class ExplosionObject extends GameObject {
    int range;


    public ExplosionObject(Game game, Position position, int range, long now, ArrayList<Position> bombZone, World bombWorld) {
        super(game, position);
        this.range = range;
        damageEntities(now, bombZone, bombWorld);
    }

    private void damageEntities(long now, ArrayList<Position> bombZone, World bombWorld) {

        // checks if player is on top of the bomb
        Player player = game.getPlayer();

        for (Position position : bombZone) {
            // Player case
            if (player.getPosition().equals(position) && player.isVulnerable()) {
                player.loseLife(now);
            }
            // Monster case
            for (Monster monster : bombWorld.getMonsters()) {
                if (monster.getPosition().equals(position)) {
                    monster.loseLife();
                }
            }
            // Decor case
            Decor decor = bombWorld.get(position);
            if (decor != null) {
                if (decor.isDestroyable()) {
                    bombWorld.clear(position);
                }
            }

            // Bomb case
            for (BombObject bomb : bombWorld.getBombs()) {
                if( bomb.getPosition().equals(position)) {
                    bomb.setBombPhase(5);
                }
            }


        }
    }
}
