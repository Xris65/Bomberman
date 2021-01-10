package fr.ubx.poo.view.sprite;

import fr.ubx.poo.game.Position;
import fr.ubx.poo.game.World;
import fr.ubx.poo.view.image.ImageFactory;
import fr.ubx.poo.view.image.ImageResource;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * The type Sprite explosion.
 */
public class SpriteExplosion extends Sprite {
    private final Position position;
    private final World world;
    private double opacity = 1.0;

    /**
     * Gets world.
     *
     * @return the world
     */
    public World getWorld() {
        return world;
    }

    /**
     * Instantiates a new Sprite explosion.
     *
     * @param layer    the layer
     * @param position the position
     * @param world    the world
     */
    public SpriteExplosion(Pane layer, Position position, World world) {
        super(layer, ImageFactory.getInstance().get(ImageResource.EXPLOSION));
        this.position = position;
        this.world = world;
        updateImage();
    }

    @Override
    public void updateImage() {
        if (opacity <= 0) {
            setToRemove(true);
        }
    }

    /**
     * Adjust opacity.
     */
    public void adjustOpacity() {
        ImageView imageView = getImageView();
        opacity = opacity - 1.0 / 60;
        imageView.setOpacity(opacity);
    }

    @Override
    public Position getPosition() {
        return position;
    }

}