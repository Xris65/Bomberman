package fr.ubx.poo.view.sprite;

import fr.ubx.poo.model.go.character.Monster;
import fr.ubx.poo.view.image.ImageFactory;
import javafx.scene.layout.Pane;

/**
 * The type Sprite monster.
 */
public class SpriteMonster extends SpriteGameObject {

    /**
     * Instantiates a new Sprite monster.
     *
     * @param layer   the layer
     * @param monster the monster
     */
    public SpriteMonster(Pane layer, Monster monster) {
        super(layer, null, monster);
        updateImage();
    }

    @Override
    public void updateImage() {
        Monster monster = (Monster) go;
        if (!monster.isAlive()) {
            setToRemove(true);
        }
        setImage(ImageFactory.getInstance().getMonster(monster.getDirection()));
    }
}
