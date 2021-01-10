/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.view.sprite;

import fr.ubx.poo.game.Position;
import fr.ubx.poo.model.decor.*;
import fr.ubx.poo.model.decor.Bonus.BombBonus.BombNumber;
import fr.ubx.poo.model.decor.Bonus.BombBonus.BombRange;
import fr.ubx.poo.model.decor.Bonus.Heart;
import fr.ubx.poo.model.decor.Bonus.Key;
import fr.ubx.poo.model.decor.Bonus.Princess;
import fr.ubx.poo.model.go.character.Monster;
import fr.ubx.poo.model.go.character.Player;
import fr.ubx.poo.view.image.ImageFactory;
import javafx.scene.layout.Pane;

import static fr.ubx.poo.view.image.ImageResource.*;


/**
 * The type Sprite factory.
 */
public final class SpriteFactory {

    /**
     * Create decor sprite.
     *
     * @param layer    the layer
     * @param position the position
     * @param decor    the decor
     * @return the sprite
     */
    public static Sprite createDecor(Pane layer, Position position, Decor decor) {
        ImageFactory factory = ImageFactory.getInstance();
        if (decor instanceof Stone)
            return new SpriteDecor(layer, factory.get(STONE), position);
        if (decor instanceof Tree)
            return new SpriteDecor(layer, factory.get(TREE), position);
        if (decor instanceof Box)
            return new SpriteDecor(layer, factory.get(BOX), position);
        // BONUS
        if (decor instanceof Heart)
            return new SpriteDecor(layer, factory.get(HEART), position);
        if (decor instanceof BombNumber) {
            BombNumber bombNumber = (BombNumber) decor;
            return new SpriteDecor(layer, factory.get(((bombNumber.isIncrease()) ? BONUS_BOMB_NB_INC : BONUS_BOMB_NB_DEC)), position);
        }
        if (decor instanceof BombRange) {
            BombRange bombRange = (BombRange) decor;
            return new SpriteDecor(layer, factory.get(((bombRange.isIncrease()) ? BONUS_BOMB_RANGE_INC : BONUS_BOMB_RANGE_DEC)), position);
        }
        if (decor instanceof Princess)
            return new SpriteDecor(layer, factory.get(PRINCESS), position);
        if (decor instanceof Key) {
            return new SpriteDecor(layer, factory.get(KEY), position);
        }
        if (decor instanceof Bomb) {
            return new SpriteDecor(layer, factory.get(DIGIT_0), position);
        }
        if (decor instanceof Door)
            return new SpriteDecor(layer, factory.getDoor(((Door) decor).isClosed()), position);
        throw new RuntimeException("Unsupported sprite for decor " + decor);
    }

    /**
     * Creates player sprite.
     *
     * @param layer  the layer
     * @param player the player
     * @return the sprite
     */
    public static Sprite createPlayer(Pane layer, Player player) {
        return new SpritePlayer(layer, player);
    }

    /**
     * Creates monster sprite.
     *
     * @param layer   the layer
     * @param monster the monster
     * @return the sprite
     */
    public static Sprite createMonster(Pane layer, Monster monster) {
        return new SpriteMonster(layer, monster);
    }
}
