package fr.ubx.poo.view.sprite;

import fr.ubx.poo.game.Position;
import fr.ubx.poo.game.World;
import fr.ubx.poo.view.image.ImageFactory;
import fr.ubx.poo.view.image.ImageResource;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class SpriteExplosion extends Sprite {
    private final Position position;
    private final World world;
    private double opacity = 1.0;

    public World getWorld() {
        return world;
    }

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