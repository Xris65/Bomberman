package fr.ubx.poo.view.sprite;

import fr.ubx.poo.game.Position;
import fr.ubx.poo.model.go.GameObject;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

/**
 * The type Sprite game object.
 */
public abstract class SpriteGameObject extends Sprite {
    /**
     * The game object.
     */
    protected final GameObject go;

    public void setToRemove(boolean toRemove) {
        super.setToRemove(toRemove);
    }


    /**
     * Instantiates a new game object sprite.
     *
     * @param layer the layer
     * @param image the image
     * @param go    the go
     */
    public SpriteGameObject(Pane layer, Image image, GameObject go) {
        super(layer, image);
        this.go = go;
    }

    @Override
    public Position getPosition() {
        return go.getPosition();
    }

}
