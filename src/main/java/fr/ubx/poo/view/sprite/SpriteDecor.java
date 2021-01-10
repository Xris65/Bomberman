package fr.ubx.poo.view.sprite;

import fr.ubx.poo.game.Position;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;


/**
 * The type Sprite decor.
 */
public class SpriteDecor extends Sprite {
    private final Position position;

    /**
     * Instantiates a new Sprite decor.
     *
     * @param layer    the layer
     * @param image    the image
     * @param position the position
     */
    public SpriteDecor(Pane layer, Image image, Position position) {
        super(layer, image);
        this.position = position;
    }


    @Override
    public void updateImage() {

    }


    @Override
    public Position getPosition() {
        return position;
    }
}
