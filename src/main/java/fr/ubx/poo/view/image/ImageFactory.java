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
    /**
     * Array containing all possible direction images for both player and monsters.
     */
    private final ImageResource[] directions = new ImageResource[]{
            // Direction { N, E, S, W }
            PLAYER_UP, PLAYER_RIGHT, PLAYER_DOWN, PLAYER_LEFT, MONSTER_UP, MONSTER_RIGHT, MONSTER_DOWN, MONSTER_LEFT,
    };
    /**
     * Array containing all possible Bomb animation images.
     */
    private final ImageResource[] bombAnimations = new ImageResource[]{
            // Bomb animations
            BOMB_1, BOMB_2, BOMB_3, BOMB_4,
    };
    /**
     * Array containing all possible door state images.
     */
    private final ImageResource[] doors = new ImageResource[]{
            // Door images
            DOOR_CLOSED, DOOR_OPENED,
    };
    /**
     * Array containing all possible level digit images.
     */
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

    /**
     * Loads the image contained by the given filename.
     * @param file the filename.
     * @return the image.
     */
    private Image loadImage(String file) {
        return new Image(getClass().getResource("/images/" + file).toExternalForm());
    }

    /**
     * Loads an image.
     */
    public void load() {
        for (ImageResource img : ImageResource.values()) {
            images[img.ordinal()] = loadImage(img.getFileName());
        }
    }

    /**
     * Gets an image.
     *
     * @param img the image resource name
     * @return the image
     */
    public Image get(ImageResource img) {
        return images[img.ordinal()];
    }

    /**
     * Gets a digit image.
     *
     * @param i the digit number
     * @return the digit image
     */
    public Image getDigit(int i) {
        if (i < 0 || i > 9)
            throw new IllegalArgumentException();
        return get(digits[i]);
    }

    /**
     * Gets player direction image.
     *
     * @param direction the direction resource name
     * @return the player image
     */
    public Image getPlayer(Direction direction) {
        return get(directions[direction.ordinal()]);
    }

    /**
     * Gets monster direction image.
     *
     * @param direction the direction resource name
     * @return the monster image
     */
    public Image getMonster(Direction direction) {
        return get(directions[direction.ordinal() + 4]);
    }

    /**
     * Gets bomb Image.
     *
     * @param phase the phase of the bomb
     * @return the bomb Image corresponding to the phase it's in
     */
    public Image getBomb(int phase) {
        return get(bombAnimations[phase - 1]);
    }

    /**
     * Gets door image.
     *
     * @param closed boolean, true if door is closed, false if open.
     * @return the door image
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
