package fr.ubx.poo.view.sprite;

import fr.ubx.poo.model.go.BombObject;
import fr.ubx.poo.view.image.ImageFactory;
import javafx.scene.layout.Pane;

public class SpriteBomb extends SpriteGameObject{

    public SpriteBomb(Pane layer, BombObject bomb) {
        super(layer, null, bomb);
        updateImage();
    }


    @Override
    public void updateImage() {
        BombObject bomb = (BombObject) super.go;
        if(bomb.getBombPhase() >= 5){
            super.setToRemove(true);
        } else {
            setImage(ImageFactory.getInstance().getBomb(bomb.getBombPhase()));
        }
    }


}
