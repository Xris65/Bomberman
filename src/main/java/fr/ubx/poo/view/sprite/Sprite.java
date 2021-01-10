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
    private final Pane layer;
    private ImageView imageView;
    private Image image;
    private boolean toRemove = false;

    /**
     * Is to remove boolean.
     *
     * @return the boolean
     */
    public boolean isToRemove() {
        return toRemove;
    }

    /**
     * Sets to remove.
     *
     * @param toRemove the to remove
     */
    public void setToRemove(boolean toRemove) {
        this.toRemove = toRemove;
    }


    /**
     * Gets image view.
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
     * Update image.
     */
    public abstract void updateImage();

    /**
     * Gets position.
     *
     * @return the position
     */
    public abstract Position getPosition();

    /**
     * Render.
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
     * Remove.
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
