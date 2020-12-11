/*
 * Copyright (c) 2020. Laurent Réveillère
 */
package fr.ubx.poo.engine;

import fr.ubx.poo.game.*;
import fr.ubx.poo.model.decor.Bomb;
import fr.ubx.poo.model.decor.Door;
import fr.ubx.poo.model.go.BombObject;
import fr.ubx.poo.model.go.Explosion;
import fr.ubx.poo.model.go.character.Monster;
import fr.ubx.poo.model.go.character.Player;
import fr.ubx.poo.view.sprite.*;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;


public final class GameEngine {

    private static AnimationTimer gameLoop;
    private final String windowTitle;
    private final Game game;
    private final Player player;
    private List<Sprite> sprites = new ArrayList<>();
    private StatusBar statusBar;
    private Pane layer;
    private Input input;
    private Stage stage;
    private Sprite spritePlayer;
    private final ArrayList<Sprite> spriteMonsters = new ArrayList<>();
    private final ArrayList<Sprite> spriteBombs = new ArrayList<>();
    private final ArrayList<SpriteExplosion> explosions = new ArrayList<>();


    public GameEngine(final String windowTitle, Game game, final Stage stage) {
        this.windowTitle = windowTitle;
        this.game = game;
        this.player = game.getPlayer();
        initialize(stage, game);
        buildAndSetGameLoop();
    }

    private void initialize(Stage stage, Game game) {
        this.stage = stage;
        Group root = new Group();
        layer = new Pane();

        int height = game.getWorld().dimension.height;
        int width = game.getWorld().dimension.width;
        int sceneWidth = width * Sprite.size;
        int sceneHeight = height * Sprite.size;
        Scene scene = new Scene(root, sceneWidth, sceneHeight + StatusBar.height);
        scene.getStylesheets().add(getClass().getResource("/css/application.css").toExternalForm());

        stage.setTitle(windowTitle);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        input = new Input(scene);
        root.getChildren().add(layer);
        statusBar = new StatusBar(root, sceneWidth, sceneHeight, game);
        // Create decor sprites
        game.getWorld().forEach( (pos,d) -> sprites.add(SpriteFactory.createDecor(layer, pos, d)));
        spritePlayer = SpriteFactory.createPlayer(layer, player);
        World w = game.getWorld();
        for (Monster m : w.getMonsters()) {
            spriteMonsters.add(SpriteFactory.createMonster(layer, m));
        }
    }

    protected final void buildAndSetGameLoop() {
        gameLoop = new AnimationTimer() {
            public void handle(long now) {
                // Check keyboard actions
                processInput(now);

                // Do actions
                update(now);

                // Graphic update
                render();
                statusBar.update(game);
            }
        };
    }

    private void processInput(long now) {
        if (input.isExit()) {
            gameLoop.stop();
            Platform.exit();
            System.exit(0);
        }
        if (input.isMoveDown()) {
            player.requestMove(Direction.S);
            player.moveBoxIfAble(game.getWorld());
        }
        if (input.isMoveLeft()) {
            player.requestMove(Direction.W);
            player.moveBoxIfAble(game.getWorld());
        }
        if (input.isMoveRight()) {
            player.requestMove(Direction.E);
            player.moveBoxIfAble(game.getWorld());
        }
        if (input.isMoveUp()) {
            player.requestMove(Direction.N);
            player.moveBoxIfAble(game.getWorld());
        }
        if (input.isBomb()) {
            if(!(game.getWorld().get(player.getPosition()) instanceof Bomb)) {
                if (player.getNumberOfBombs() < player.getBombCapacity()) {

                    BombObject bomb = new BombObject(game, player.getPosition(), player.getRange(), now);
                    game.getWorld().getBombs().add(bomb);
                    spriteBombs.add(new SpriteBomb(layer, bomb));
                    game.getWorld().set(player.getPosition(), new Bomb());
                    game.getWorld().setChanged(true);
                    //game.createExplosions(explosions,layer,player);
                    player.removeBomb();
                }
            }
        }
        if(input.isKey()) {
            Position playerPos = player.getPosition();
            World world = game.getWorld();
            for(Direction d : Direction.values()){
                if(world.get(d.nextPosition(playerPos)) instanceof Door
                  &&(((Door) world.get(d.nextPosition(playerPos))).isClosed())){
                    if(player.getNumberOfKeys() > 0){
                        player.substractKey();
                        game.setToChange(true);
                        world.set(d.nextPosition(playerPos),new Door(false,false));
                        game.changeWorld(true);
                        break;
                    }
                }
            }
        }
        input.clear();
    }



    private void showMessage(String msg, Color color) {
        Text waitingForKey = new Text(msg);
        waitingForKey.setTextAlignment(TextAlignment.CENTER);
        waitingForKey.setFont(new Font(60));
        waitingForKey.setFill(color);
        StackPane root = new StackPane();
        root.getChildren().add(waitingForKey);
        Scene scene = new Scene(root, 400, 200, Color.WHITE);
        stage.setTitle(windowTitle);
        stage.setScene(scene);
        input = new Input(scene);
        stage.show();
        new AnimationTimer() {
            public void handle(long now) {
                processInput(now);
            }
        }.start();
    }

    private void update(long now) {
        player.update(now);
        game.getWorldManager().updateMonstersOnWorlds(now);
        game.getWorldManager().verifyMonsterCollisionsWithPlayer();
        if (!player.isAlive()) {
            gameLoop.stop();
            showMessage("Perdu!", Color.RED);
        }
        Iterator monsterIterator = game.getWorld().getMonsters().iterator();
        while(monsterIterator.hasNext()){
            Monster m = (Monster) monsterIterator.next();
            if(!m.isAlive()){
                Iterator spriteMonstersIterator = spriteMonsters.iterator();
                while(spriteMonstersIterator.hasNext()){
                    SpriteMonster spriteMonster = (SpriteMonster) spriteMonstersIterator.next();
                    if(spriteMonster.isToRemove()){
                        spriteMonster.remove();
                        spriteMonstersIterator.remove();
                    }
                }
                //spriteMonsters.removeIf(Sprite::isToRemove);
                monsterIterator.remove();
            }
        }
        if (player.isWinner()) {
            gameLoop.stop();
            showMessage("Gagné", Color.BLUE);
        }
        if(game.isToChange()){
            game.setToChange(false);
            spriteMonsters.removeIf(Objects::nonNull);
            initialize(stage,game);
        }
        for(BombObject b : game.getWorld().getBombs()){
            b.update(now);
        }

        Iterator bombObjectIterator = game.getWorld().getBombs().iterator();
        while(bombObjectIterator.hasNext()){
            BombObject b = (BombObject) bombObjectIterator.next();
            if((b.getBombPhase() == 5)){
                bombObjectIterator.remove();
                Position p = b.getPosition();
                game.getWorld().clear(p);
                game.getWorld().getExplosions().add(new Explosion(game, p, b.getRange()));
                // add explosion sprites
            }
        }

    }

    private void render() {
        if(player.isOnBonus()) {
            //sprites.get(player.getPosition().x + (player.getPosition().y * game.getWorld().dimension.width)).remove();
            game.getWorld().clear(player.getPosition());
            player.setOnBonus(false);
            game.getWorld().setChanged(true);
        }
        if( game.getWorld().isChanged()){

            game.getWorld().setChanged(false);
            sprites.forEach(Sprite::remove);
            sprites.removeIf(self->self.getImageView() == null);
            game.getWorld().forEach( (pos,d ) -> {if (!(d instanceof Bomb)){ sprites.add(SpriteFactory.createDecor(layer, pos, d)); }} );
        }
        sprites.forEach(Sprite::render);
        // last rendering to have player in the foreground
        spritePlayer.render();

        for (Sprite monster : spriteMonsters) {
            monster.render();
        }
        spriteMonsters.forEach(self -> {
            if(self.isToRemove()){
            self.remove();
        }});
        spriteMonsters.removeIf(Sprite::isToRemove);


        for (Sprite bomb : spriteBombs) {
            bomb.render();
            if (bomb.isToRemove()) {
                player.addBomb();
            }
        }
        for (BombObject bomb : game.getWorld().getBombs()) {
            if( bomb.getBombPhase() == 5) {
                game.getWorld().clear(bomb.getPosition());

            }
        }
        spriteBombs.forEach(self -> {
            if (self.isToRemove()) {
                self.remove();
            }});
        spriteBombs.removeIf(Sprite::isToRemove);
    }

    public void start() {
        gameLoop.start();
    }
}
