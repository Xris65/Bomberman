package fr.ubx.poo.model.decor;

import fr.ubx.poo.game.World;
import fr.ubx.poo.game.WorldEntity;
import fr.ubx.poo.view.image.ImageFactory;
import fr.ubx.poo.view.image.ImageResource;
import fr.ubx.poo.view.sprite.SpriteDecor;

import java.io.File;

public class Bonus extends Decor {
    private WorldEntity type;
    private int index;

    public Bonus(WorldEntity type){
        this.type = type;
    }

    public WorldEntity getType() {
        return type;
    }

    public void setType(WorldEntity type) {
        this.type = type;
    }

    public ImageResource getImagePath() {
        ImageFactory f = ImageFactory.getInstance();
        String filename;
        if(WorldEntity.BombNumberDec == type)
            return ImageResource.BONUS_BOMB_NB_DEC;
        else if (WorldEntity.BombNumberInc == type)
            return ImageResource.BONUS_BOMB_NB_INC;
        else if (WorldEntity.BombRangeDec == type)
            return ImageResource.BONUS_BOMB_RANGE_DEC;
        else if (WorldEntity.BombRangeInc == type)
            return ImageResource.BONUS_BOMB_RANGE_INC;
        else if(WorldEntity.Heart == type)
            return ImageResource.HEART;
        else if(WorldEntity.Key == type)
            return ImageResource.KEY;
        return null;
    }

    @Override
    public boolean isWalkable() {
        return true;
    }

    @Override
    public String toString() {
        return "" + getImagePath();
    }
}
