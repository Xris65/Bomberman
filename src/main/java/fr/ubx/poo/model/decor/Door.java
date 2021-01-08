package fr.ubx.poo.model.decor;

import fr.ubx.poo.model.go.character.Character;
import fr.ubx.poo.model.go.character.Monster;
import fr.ubx.poo.model.go.character.Player;

public class Door extends Decor {
    private final boolean closed;
    private final boolean prev;

    public boolean isClosed() {
        return closed;
    }

    public boolean isPrev() {
        return prev;
    }


    public Door(boolean closed, boolean prev) {
        this.closed = closed;
        this.prev = prev;
    }

    @Override
    public void obtain(Player player) {
    }

    @Override
    public boolean isWalkable(Character character) {
        return !closed && !(character instanceof Monster);
    }

}
