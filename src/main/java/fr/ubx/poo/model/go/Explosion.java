package fr.ubx.poo.model.go;

import fr.ubx.poo.game.*;
import fr.ubx.poo.model.Entity;
import fr.ubx.poo.model.decor.Bonus;

import java.util.concurrent.atomic.AtomicBoolean;

public class Explosion extends GameObject{
    int range;
    public Explosion(Game game, Position position, int range) {
        super(game, position);
        this.range = range;
        damageEntities();
    }

    private boolean isKey(Entity e){
        Bonus b = (Bonus) e;
        return b.getType() == WorldEntity.Key;
    }

    private void damageEntities(){
        World w = game.getWorld();
        for(Direction d : Direction.values()){  // for each direction
            Position initialPosition = getPosition();
            for (int i = 0; i < range; i++){ // a la distance i
                AtomicBoolean leave = new AtomicBoolean(false);
                initialPosition = d.nextPosition(initialPosition);

                Position finalInitialPosition = initialPosition; // needed for lambda expression

                Entity e = w.get(initialPosition);
                if(e != null){
                    if(e.isBox() || (e.isBonus() && !isKey(e))){
                        w.clear(initialPosition);
                        w.setChanged(true);
                    }

                    break;
                }

                w.getMonsters().forEach(self -> {
                    if(self.getPosition().equals(finalInitialPosition)){
                        self.removeHP(1);
                        leave.set(true);

                    }
                });
                if(leave.get()){
                    break;
                }
                if(game.getPlayer().getPosition().equals(initialPosition)){
                    game.getPlayer().loseLife();
                    break;
                }
            }
        }




    }
}
