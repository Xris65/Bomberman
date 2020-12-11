package fr.ubx.poo.model.decor.Bonus;

import fr.ubx.poo.game.World;
import fr.ubx.poo.game.WorldEntity;
import fr.ubx.poo.model.decor.Decor;
import fr.ubx.poo.view.image.ImageFactory;
import fr.ubx.poo.view.image.ImageResource;
import fr.ubx.poo.view.sprite.SpriteDecor;

import java.io.File;

abstract public class Bonus extends Decor {
    @Override
    public boolean isWalkable() {
        return true;
    }

    @Override
    public boolean isToRemove() {
        return true;
    }

    @Override
    public String toString() {
        return "Bonus";
    }
}
