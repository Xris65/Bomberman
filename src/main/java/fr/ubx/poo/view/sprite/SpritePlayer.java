/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.view.sprite;

import fr.ubx.poo.model.go.character.Player;
import fr.ubx.poo.view.image.ImageFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * The type Sprite player.
 */
public class SpritePlayer extends SpriteGameObject {

    /**
     * Instantiates a new player sprite.
     *
     * @param layer  the layer
     * @param player the player
     */
    public SpritePlayer(Pane layer, Player player) {
        super(layer, null, player);
        updateImage();
    }

    @Override
    public void updateImage() {
        Player player = (Player) go;
        setImage(ImageFactory.getInstance().getPlayer(player.getDirection()));
    }

    /**
     * Updates player transparency.
     */
    public void updatePlayerTransparency() {
        Player player = (Player) go;
        ImageView imageView = getImageView();
        if (!player.isVulnerable()) {
            imageView.setOpacity(0.5);
        } else if (player.isVulnerable()) {
            imageView.setOpacity(1.0);
        }
    }
}
