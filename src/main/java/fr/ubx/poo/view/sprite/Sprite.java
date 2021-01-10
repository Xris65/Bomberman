/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.view.sprite;

import fr.ubx.poo.game.Position;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * The type Sprite.
 */
public abstract class Sprite {

    /**
     * The constant size.
     */
    public static final int size = 40;
    /**
     * The layer.
     */
    private final Pane layer;
    /**
     * The image view.
     */
    private ImageView imageView;
    /**
     * The image.
     */
    private Image image;
    /**
     * Confirms if a sprite is to remove or not.
     */
    private boolean toRemove = false;

    /**
     * Is to remove boolean.
     *
     * @return true if sprite is to be removed, false if not.
     */
    public boolean isToRemove() {
        return toRemove;
    }

    /**
     * Sets to remove boolean value.
     *
     * @param toRemove true if sprite should be removed, false if not.
     */
    public void setToRemove(boolean toRemove) {
        this.toRemove = toRemove;
    }


    /**
     * Gets the image view.
     *
     * @return the image view
     */
    public ImageView getImageView() {
        return imageView;
    }

    /**
     * Instantiates a new Sprite.
     *
     * @param layer the layer
     * @param image the image
     */
    public Sprite(Pane layer, Image image) {
        this.layer = layer;
        this.image = image;
    }


    /**
     * Sets image.
     *
     * @param image the image
     */
    public final void setImage(Image image) {
        if (this.image == null || this.image != image) {
            this.image = image;
        }
    }

    /**
     * Updates the image.
     */
    public abstract void updateImage();

    /**
     * Gets position of the sprite.
     *
     * @return the position
     */
    public abstract Position getPosition();

    /**
     * Renders the sprite.
     */
    public final void render() {
        if (imageView != null) {
            remove();
        }
        updateImage();
        imageView = new ImageView(this.image);
        imageView.setX(getPosition().x * size);
        imageView.setY(getPosition().y * size);
        layer.getChildren().add(imageView);
    }

    /**
     * Removes sprite image and sets it up for delete.
     */
    public final void remove() {
        layer.getChildren().remove(imageView);
        imageView = null;
    }

    @Override
    public String toString() {
        return "Sprite{" +
                "imageView=" + imageView +
                ", image=" + image +
                '}';
    }
}
