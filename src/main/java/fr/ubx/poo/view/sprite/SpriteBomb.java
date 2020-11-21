package fr.ubx.poo.view.sprite;

import fr.ubx.poo.model.go.BombObject;
import fr.ubx.poo.model.go.GameObject;
import fr.ubx.poo.model.go.character.Player;
import fr.ubx.poo.view.image.ImageFactory;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.layout.Pane;

public class SpriteBomb extends SpriteGameObject{
    private final ColorAdjust effect = new ColorAdjust();


    public SpriteBomb(Pane layer, BombObject bomb) {
        super(layer, null, bomb);
        updateImage();
    }

    @Override
    public void updateImage() {
        BombObject bomb = (BombObject) go;
        if(bomb.getBombPhase() >= 6){
            System.out.println("In update Image, call to remove");
            remove();
            setImage(null);
            super.setToRemove(true);
        } else {
            setImage(ImageFactory.getInstance().getBomb(bomb.getBombPhase()));
        }
    }


}
