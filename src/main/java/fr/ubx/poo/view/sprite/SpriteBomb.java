package fr.ubx.poo.view.sprite;

import fr.ubx.poo.game.Position;
import fr.ubx.poo.model.go.BombObject;
import fr.ubx.poo.model.go.GameObject;
import fr.ubx.poo.model.go.character.Player;
import fr.ubx.poo.view.image.ImageFactory;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class SpriteBomb extends SpriteGameObject{
    private final ColorAdjust effect = new ColorAdjust();


    public SpriteBomb(Pane layer, BombObject bomb) {
        super(layer, null, bomb);
        updateImage();
    }


    @Override
    public void updateImage() {
        BombObject bomb = (BombObject) super.go;
        if(bomb.getBombPhase() >= 5){
            System.out.println("In update Image, call to remove");
            super.setToRemove(true);
        } else {
            setImage(ImageFactory.getInstance().getBomb(bomb.getBombPhase()));
        }
    }


}
