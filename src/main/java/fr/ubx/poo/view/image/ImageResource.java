package fr.ubx.poo.view.image;

/**
 * The enum Image resource.
 */
public enum ImageResource {
    /**
     * Digit 0 image resource.
     */
    DIGIT_0("banner_0.jpg"),
    /**
     * Digit 1 image resource.
     */
    DIGIT_1("banner_1.jpg"),
    /**
     * Digit 2 image resource.
     */
    DIGIT_2("banner_2.jpg"),
    /**
     * Digit 3 image resource.
     */
    DIGIT_3("banner_3.jpg"),
    /**
     * Digit 4 image resource.
     */
    DIGIT_4("banner_4.jpg"),
    /**
     * Digit 5 image resource.
     */
    DIGIT_5("banner_5.jpg"),
    /**
     * Digit 6 image resource.
     */
    DIGIT_6("banner_6.jpg"),
    /**
     * Digit 7 image resource.
     */
    DIGIT_7("banner_7.jpg"),
    /**
     * Digit 8 image resource.
     */
    DIGIT_8("banner_8.jpg"),
    /**
     * Digit 9 image resource.
     */
    DIGIT_9("banner_9.jpg"),
    /**
     * Banner bomb image resource.
     */
    BANNER_BOMB("banner_bomb.png"),
    /**
     * Banner range image resource.
     */
    BANNER_RANGE("banner_range.png"),
    /**
     * Heart image resource.
     */
    HEART("heart.png"),
    /**
     * Key image resource.
     */
    KEY("key.png"),
    /**
     * Player down image resource.
     */
    PLAYER_DOWN("player_down.png"),
    /**
     * Player left image resource.
     */
    PLAYER_LEFT("player_left.png"),
    /**
     * Player right image resource.
     */
    PLAYER_RIGHT("player_right.png"),
    /**
     * Player up image resource.
     */
    PLAYER_UP("player_up.png"),
    /**
     * Monster down image resource.
     */
    MONSTER_DOWN("monster_down.png"),
    /**
     * Monster left image resource.
     */
    MONSTER_LEFT("monster_left.png"),
    /**
     * Monster right image resource.
     */
    MONSTER_RIGHT("monster_right.png"),
    /**
     * Monster up image resource.
     */
    MONSTER_UP("monster_up.png"),
    /**
     * Stone image resource.
     */
    STONE("stone.png"),
    /**
     * Tree image resource.
     */
    TREE("tree.png"),
    /**
     * Box image resource.
     */
    BOX("box.png"),
    /**
     * Bonus bomb nb dec image resource.
     */
    BONUS_BOMB_NB_DEC("bonus_bomb_nb_dec.png"),
    /**
     * Bonus bomb nb inc image resource.
     */
    BONUS_BOMB_NB_INC("bonus_bomb_nb_inc.png"),
    /**
     * Bonus bomb range dec image resource.
     */
    BONUS_BOMB_RANGE_DEC("bonus_bomb_range_dec.png"),
    /**
     * Bonus bomb range inc image resource.
     */
    BONUS_BOMB_RANGE_INC("bonus_bomb_range_inc.png"),
    /**
     * Princess image resource.
     */
    PRINCESS("bomberwoman.png"),
    /**
     * Bomb 1 image resource.
     */
    BOMB_1("bomb4.png"),
    /**
     * Bomb 2 image resource.
     */
    BOMB_2("bomb3.png"),
    /**
     * Bomb 3 image resource.
     */
    BOMB_3("bomb2.png"),
    /**
     * Bomb 4 image resource.
     */
    BOMB_4("bomb1.png"),
    /**
     * Explosion image resource.
     */
    EXPLOSION("explosion.png"),
    /**
     * Door closed image resource.
     */
    DOOR_CLOSED("door_closed.png"),
    /**
     * Door opened image resource.
     */
    DOOR_OPENED("door_opened.png"),
    ;

    private final String FileName;

    ImageResource(String fileName) {
        this.FileName = fileName;
    }

    /**
     * Gets file name.
     *
     * @return the file name
     */
    public String getFileName() {
        return FileName;
    }
}
