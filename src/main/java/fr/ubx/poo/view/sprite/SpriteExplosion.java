package fr.ubx.poo.view.sprite;

import fr.ubx.poo.game.Position;
import fr.ubx.poo.model.go.BombObject;
import fr.ubx.poo.model.go.character.Player;
import fr.ubx.poo.view.image.ImageFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class SpriteExplosion extends Sprite {
    private final Position position;
    private boolean rendered = false;

    public boolean isRendering() {
        return isRendering;
    }

    public void setRendering(boolean rendering) {
        isRendering = rendering;
    }

    private boolean isRendering = false;


    public SpriteExplosion(Pane layer, Image explosionImage, Position position) {
        super(layer, explosionImage);
        this.position = position;
        updateImage();
    }
    public void setRendered(boolean rendered){
        this.rendered = rendered;
    }
    public boolean isRendered(){
        return rendered;
    }

    @Override
    public void updateImage() {
    }

    @Override
    public Position getPosition() {
        return position;
    }

}