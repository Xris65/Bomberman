/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.view.image;

import fr.ubx.poo.game.Direction;
import javafx.scene.image.Image;

import static fr.ubx.poo.view.image.ImageResource.*;

/**
 * The type Image factory.
 */
public final class ImageFactory {
    private final Image[] images;

    private final ImageResource[] directions = new ImageResource[]{
            // Direction { N, E, S, W }
            PLAYER_UP, PLAYER_RIGHT, PLAYER_DOWN, PLAYER_LEFT, MONSTER_UP, MONSTER_RIGHT, MONSTER_DOWN, MONSTER_LEFT,
    };
    private final ImageResource[] bombAnimations = new ImageResource[]{
            // Bomb animations
            BOMB_1, BOMB_2, BOMB_3, BOMB_4,
    };
    private final ImageResource[] doors = new ImageResource[]{
            // Door images
            DOOR_CLOSED, DOOR_OPENED,
    };

    private final ImageResource[] digits = new ImageResource[]{
            DIGIT_0, DIGIT_1, DIGIT_2, DIGIT_3, DIGIT_4,
            DIGIT_5, DIGIT_6, DIGIT_7, DIGIT_8, DIGIT_9,
    };

    private ImageFactory() {
        images = new Image[ImageResource.values().length];
    }

    /**
     * Point d'accès pour l'instance unique du singleton
     *
     * @return the instance
     */
    public static ImageFactory getInstance() {
        return Holder.instance;
    }

    private Image loadImage(String file) {
        return new Image(getClass().getResource("/images/" + file).toExternalForm());
    }

    /**
     * Load.
     */
    public void load() {
        for (ImageResource img : ImageResource.values()) {
            images[img.ordinal()] = loadImage(img.getFileName());
        }
    }

    /**
     * Get image.
     *
     * @param img the img
     * @return the image
     */
    public Image get(ImageResource img) {
        return images[img.ordinal()];
    }

    /**
     * Gets digit.
     *
     * @param i the
     * @return the digit
     */
    public Image getDigit(int i) {
        if (i < 0 || i > 9)
            throw new IllegalArgumentException();
        return get(digits[i]);
    }

    /**
     * Gets player.
     *
     * @param direction the direction
     * @return the player
     */
    public Image getPlayer(Direction direction) {
        return get(directions[direction.ordinal()]);
    }

    /**
     * Gets monster.
     *
     * @param direction the direction
     * @return the monster
     */
    public Image getMonster(Direction direction) {
        return get(directions[direction.ordinal() + 4]);
    }

    /**
     * Gets boomb Image.
     *
     * @param phase the phase
     * @return the bomb Image corresponding to the phase it's in
     */
    public Image getBomb(int phase) {
        return get(bombAnimations[phase - 1]);
    }

    /**
     * Gets door.
     *
     * @param closed the closed
     * @return the door
     */
    public Image getDoor(boolean closed) {
        if (closed)
            return get(doors[0]);
        return get(doors[1]);
    }


    /**
     * Holder
     */
    private static class Holder {
        /**
         * Instance unique non préinitialisée
         */
        private final static ImageFactory instance = new ImageFactory();
    }

}
