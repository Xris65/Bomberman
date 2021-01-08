package fr.ubx.poo.model.decor;

import fr.ubx.poo.game.Direction;
import fr.ubx.poo.game.Position;
import fr.ubx.poo.game.World;
import fr.ubx.poo.model.go.character.Monster;
import fr.ubx.poo.model.go.character.Player;

public class Box extends Decor {

    @Override
    public void move(Player player) {
        Direction direction = player.getDirection();
        Position boxAt = direction.nextPosition(player.getPosition());
        World world = player.getGame().getWorld();

        if (world.get(direction.nextPosition(boxAt)) == null && world.isInside(direction.nextPosition(boxAt))) {
            // can move
            for (Monster m : world.getMonsters()) {
                if (m.getPosition().equals(direction.nextPosition(boxAt))) {
                    return;
                }
            }
            world.set(direction.nextPosition(boxAt), new Box());
            world.clear(boxAt);
        }
    }

    @Override
    public boolean isDestroyable() {
        return true;
    }

    @Override
    public String toString() {
        return "Box";
    }
}
