package fr.ubx.poo.game;

import fr.ubx.poo.model.go.character.Monster;
import fr.ubx.poo.model.go.character.Player;

import java.util.ArrayList;

public class WorldManager {
    private ArrayList<World> worlds = new ArrayList<>();
    private int worldMaxAtteint = 0;
    private int currentWorldIndex = -1;




    private boolean isDiscovered(int worldNumber) {
        return worlds.get(worldNumber - 1) != null;
    }

    public void addWorld(World world) {
        worlds.add(world);
        worldMaxAtteint++;
    }

    public World getNextWorld(Game game) {
        // load next world and give it to user
        // and add it to arraylist
        currentWorldIndex++;
        if (currentWorldIndex < worldMaxAtteint) {
            return worlds.get(currentWorldIndex);
        } else {
            World nextWorld;
            WorldLoader wl = new WorldLoader();
            nextWorld = wl.readFromFile(String.format("level%d.txt", worldMaxAtteint + 1));
            nextWorld.monsters = nextWorld.findMonsters(game);
            addWorld(nextWorld);
            return nextWorld;
        }
    }

    public World getPreviousWorld() {
        currentWorldIndex--;
        return worlds.get(currentWorldIndex);
    }

    public void updateMonstersOnWorlds(long now) {
        for (World w : worlds) {
                w.updateMonsters(now);
            for (Monster m : w.monsters) {
                m.update(now);
            }
        }
    }

    public void verifyMonsterCollisionsWithPlayer() {
        World w = worlds.get(currentWorldIndex);
        for (Monster m : w.monsters) {
            Game game = m.getGame();
            Player player = game.getPlayer();
            if (m.getPosition().equals(game.getPlayer().getPosition()))
                if (player.isPlayerVulnerable())
                    player.loseLife();
        }
    }
    public int getCurrentWorldIndex() {
        return currentWorldIndex;
    }

}

