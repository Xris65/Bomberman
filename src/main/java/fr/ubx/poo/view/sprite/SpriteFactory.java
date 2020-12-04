/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.view.sprite;

import static fr.ubx.poo.view.image.ImageResource.*;

import fr.ubx.poo.game.Position;
import fr.ubx.poo.model.decor.*;
import fr.ubx.poo.model.go.character.Monster;
import fr.ubx.poo.model.go.character.Player;
import fr.ubx.poo.view.image.ImageFactory;
import javafx.scene.layout.Pane;


public final class SpriteFactory {

    public static Sprite createDecor(Pane layer, Position position, Decor decor) {
        ImageFactory factory = ImageFactory.getInstance();
        if (decor.isStone())
            return new SpriteDecor(layer, factory.get(STONE), position);
        if (decor.isTree())
            return new SpriteDecor(layer, factory.get(TREE), position);
        if(decor.isBox())
            return new SpriteDecor(layer, factory.get(BOX), position);
        if(decor.isBonus())
            return new SpriteDecor(layer, factory.get( ((Bonus) decor).getImagePath()), position);
        if(decor.isPrincess())
            return new SpriteDecor(layer, factory.get(PRINCESS), position);
        if(decor.isDoor())
            return new SpriteDecor(layer,factory.getDoor(((Door) decor).isClosed()),position);
        if(decor.isBomb()){}
        throw new RuntimeException("Unsupported sprite for decor " + decor);
    }

    public static Sprite createPlayer(Pane layer, Player player) {
        return new SpritePlayer(layer, player);
    }
    public static Sprite createMonster(Pane layer, Monster monster) {
        return new SpriteMonster(layer, monster);
    }
}
