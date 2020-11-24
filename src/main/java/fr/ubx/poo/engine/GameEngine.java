/*
 * Copyright (c) 2020. Laurent Réveillère
 */
package fr.ubx.poo.engine;

import fr.ubx.poo.game.*;
import fr.ubx.poo.model.decor.Bomb;
import fr.ubx.poo.model.decor.Door;
import fr.ubx.poo.model.go.BombObject;
import fr.ubx.poo.model.go.GameObject;
import fr.ubx.poo.model.go.character.Monster;
import fr.ubx.poo.view.sprite.*;
import fr.ubx.poo.model.go.character.Player;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.lang.reflect.Array;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


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
    private ArrayList<Sprite> spriteMonsters = new ArrayList<>();
    private ArrayList<Sprite> spriteBombs = new ArrayList<>();
    private ArrayList<SpriteExplosion> explosions = new ArrayList<>();
    ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();


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
        w.monsters = w.findMonsters(game);
        game.setMonsters(w.monsters);
        for (Monster m : w.monsters) {
            spriteMonsters.add(new SpriteMonster(layer, m));
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
            game.end();
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
                    BombObject bomb = new BombObject(game, player.getPosition());
                    game.getWorld().bombs.add(bomb);
                    spriteBombs.add(new SpriteBomb(layer, bomb));
                    game.getWorld().set(player.getPosition(), new Bomb());
                    game.getWorld().setChanged(true);
                    //game.createExplosions(explosions,layer,player);
                    bomb.startTimer();
                    player.removeBomb();
                }
            }
        }
        if(input.isKey()) {
            Position playerPos = player.getPosition();
            World world = game.getWorld();
            if((world.get(Direction.E.nextPosition(playerPos)) instanceof Door
                    && (((Door) world.get(Direction.E.nextPosition(playerPos))).isClosed()))
                    || (world.get(Direction.N.nextPosition(playerPos)) instanceof Door
                    && (((Door) world.get(Direction.N.nextPosition(playerPos))).isClosed()))
                    || (world.get(Direction.S.nextPosition(playerPos)) instanceof Door
                    && (((Door) world.get(Direction.S.nextPosition(playerPos))).isClosed()))
                    || (world.get(Direction.W.nextPosition(playerPos)) instanceof Door
                    && (((Door) world.get(Direction.W.nextPosition(playerPos))).isClosed()))){
                if(player.getNumberOfKeys() > 0){
                    player.substractKey();
                    game.stageNumber++;
                    game.changeWorld();
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
        for (Monster m : game.getWorld().monsters) {
            m.update(now);
            if (m.getPosition().equals(game.getPlayer().getPosition()))
                if(player.isPlayerVulnerable())
                    player.loseLife();
        }
        if (!player.isAlive()) {
            gameLoop.stop();
            showMessage("Perdu!", Color.RED);
        }
        if (player.isWinner()) {
            gameLoop.stop();
            showMessage("Gagné", Color.BLUE);
        }
    }

    private void render() {
        if(player.isOnBonus()) {
            //sprites.get(player.getPosition().x + (player.getPosition().y * game.getWorld().dimension.width)).remove();
            for(Sprite sprite : sprites){
                if(sprite instanceof SpriteDecor){
                    SpriteDecor decor = (SpriteDecor) sprite;
                    if(decor.updateImage(game)){
                        break;
                    }
                }
            }
            sprites.removeIf(self -> self.getImageView() == null);
            player.setOnBonus(false);
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

        for (Sprite bomb : spriteBombs) {
            bomb.render();
            if (bomb.isToRemove()) {
                player.addBomb();
            }
        }
        for (BombObject bomb : game.getWorld().bombs) {
            if( bomb.getBombPhase() == 5) {
                game.getWorld().clear(bomb.getPosition());
            }
        }
        game.getWorld().bombs.removeIf( self -> self.getBombPhase() == 5);

        /*System.out.println(""+spriteBombs.size());
        if(explosions.size()>0) {
            for (Sprite explosion : explosions) {
                SpriteExplosion explosion1 = (SpriteExplosion) explosion;
                if(!explosion1.isRendering()) {
                    executor.schedule(explosion::render, 1, TimeUnit.SECONDS);
                    executor.schedule(() -> explosion1.setRendered(true), 1500, TimeUnit.MILLISECONDS);
                    explosion1.setRendering(true);
                    executor.schedule(() -> executor.shutdown(), 2, TimeUnit.SECONDS);
                }
                //explosion.remove();
                //executor.schedule(() -> explosions.remove(explosion), 1, TimeUnit.SECONDS);
                if(explosion1.isRendered()) {
                    explosion.remove();
                    explosions.remove(explosion);
                }

            }
        }*/
        spriteBombs.removeIf(Sprite::isToRemove);
    }

    public void start() {
        gameLoop.start();
    }
}
