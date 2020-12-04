package fr.ubx.poo.view.sprite;

import fr.ubx.poo.game.Game;
import fr.ubx.poo.model.go.character.Monster;
import fr.ubx.poo.view.image.ImageFactory;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.layout.Pane;

public class SpriteMonster extends SpriteGameObject{
    private final ColorAdjust effect = new ColorAdjust();

    public SpriteMonster(Pane layer, Monster monster) {
        super(layer, null, monster);
        updateImage();
    }

    @Override
    public void updateImage() {
        Monster monster = (Monster) go;
        if(!monster.isAlive()){
            setToRemove(true);
            System.out.println("In updateImage, is dead");
        }
        setImage(ImageFactory.getInstance().getMonster(monster.getDirection()));
    }
}
