package fr.ubx.poo.model.decor;

import fr.ubx.poo.model.go.character.Character;

public class Explosion extends Decor {

    @Override
    public boolean isDestroyable() {
        return false;
    }

    public boolean isWalkable(Character character) {
        return true;
    }

    public String toString() {
        return "Explosion";
    }

}
