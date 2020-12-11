package fr.ubx.poo.model;

import fr.ubx.poo.model.go.character.Player;

public abstract class Entity {
    public boolean isWalkable(){
        return false;
    }

    public void obtain(Player player){ }

    public boolean isToRemove(){
        return false;
    }
}
