/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.engine;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.BitSet;

import static javafx.scene.input.KeyCode.*;

/**
 * The type Input.
 */
public final class Input {

    /**
     * Bitset which registers if any {@link KeyCode} keeps being pressed or if it is
     * released.
     */
    private final BitSet keyboardBitSet = new BitSet();
    /**
     * "Key Released" handler for all input events: unregister released key in the bitset
     */
    private final EventHandler<KeyEvent> keyReleasedEventHandler = event -> {

        // register key up
        keyboardBitSet.set(event.getCode().ordinal(), false);
    };
    private final Scene scene;

    /**
     * Instantiates a new Input.
     *
     * @param scene the scene
     */
    public Input(Scene scene) {
        this.scene = scene;
        /**
         * "Key Pressed" handler for all input events: register pressed key in the bitset
         */
        // register key down
        EventHandler<KeyEvent> keyPressedEventHandler = event -> {

            // register key down
            keyboardBitSet.set(event.getCode().ordinal(), true);
        };
        scene.addEventFilter(KeyEvent.KEY_PRESSED, keyPressedEventHandler);
        scene.addEventFilter(KeyEvent.KEY_RELEASED, keyReleasedEventHandler);
    }

    /**
     * Clear.
     */
    public void clear() {
        keyboardBitSet.clear();
    }

    private boolean is(KeyCode key) {
        return keyboardBitSet.get(key.ordinal());
    }

    // -------------------------------------------------
    // Evaluate bitset of pressed keys and return the player input.
    // If direction and its opposite direction are pressed simultaneously, then the
    // direction isn't handled.
    // -------------------------------------------------

    /**
     * Is move up boolean.
     *
     * @return the boolean
     */
    public boolean isMoveUp() {
        return is(UP) && !is(DOWN);
    }

    /**
     * Is move down boolean.
     *
     * @return the boolean
     */
    public boolean isMoveDown() {
        return is(DOWN) && !is(UP);
    }

    /**
     * Is move left boolean.
     *
     * @return the boolean
     */
    public boolean isMoveLeft() {
        return is(LEFT) && !is(RIGHT);
    }

    /**
     * Is move right boolean.
     *
     * @return the boolean
     */
    public boolean isMoveRight() {
        return is(RIGHT) && !is(LEFT);
    }

    /**
     * Is bomb boolean.
     *
     * @return the boolean
     */
    public boolean isBomb() {
        return is(SPACE);
    }

    /**
     * Is key boolean.
     *
     * @return the boolean
     */
    public boolean isKey() {
        return is(ENTER);
    }

    /**
     * Is exit boolean.
     *
     * @return the boolean
     */
    public boolean isExit() {
        return is(ESCAPE);
    }
}
