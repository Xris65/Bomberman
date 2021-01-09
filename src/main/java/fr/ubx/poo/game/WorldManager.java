package fr.ubx.poo.game;

import fr.ubx.poo.model.go.character.Monster;
import fr.ubx.poo.model.go.character.Player;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

public class WorldManager {
    private final ArrayList<World> worlds = new ArrayList<>();
    private int worldMaxReached = 0;
    private int currentWorldIndex = -1;
    private final String worldPath;
    private String prefix;
    private int maxLevel;

    public ArrayList<World> getWorlds() {
        return worlds;
    }

    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public WorldManager(String worldPath) {
        this.worldPath = worldPath;
    }

    public void addWorld(World world) {
        worlds.add(world);
        worldMaxReached++;
    }

    public World getNextWorld(Game game) {
        // load next world and give it to user
        // and add it to arraylist
        currentWorldIndex++;
        if (currentWorldIndex < worldMaxReached) {
            return worlds.get(currentWorldIndex);
        } else if (currentWorldIndex >= maxLevel) {
            currentWorldIndex--;
        } else {
            World nextWorld;
            nextWorld = readFromFile(String.format("%s%d.txt", prefix, worldMaxReached + 1));
            nextWorld.setMonsters(nextWorld.findMonsters(game));
            addWorld(nextWorld);
            return nextWorld;
        }
        return null;
    }

    public World getPreviousWorld() {
        currentWorldIndex--;
        return worlds.get(currentWorldIndex);
    }

    public void updateMonstersOnWorlds(long now) {
        for (World w : worlds) {
            w.updateMonsters(now);
        }
    }

    public void verifyMonsterCollisionsWithPlayer(long now) {
        World w = worlds.get(currentWorldIndex);
        for (Monster m : w.getMonsters()) {
            Game game = m.getGame();
            Player player = game.getPlayer();
            if (m.getPosition().equals(game.getPlayer().getPosition()))
                if (player.isVulnerable())
                    player.loseLife(now);
        }
    }

    public int getCurrentWorldIndex() {
        return currentWorldIndex;
    }

    private World readFromFile(String filename) {
        WorldEntity[][] read;
        try {
            File myObj = new File(worldPath, filename);
            Scanner measuresReader = new Scanner(myObj);

            // Get map height and width from file
            int height = 0;
            int width = -1;
            for (; measuresReader.hasNextLine(); height++) {
                int tmp = measuresReader.nextLine().length();
                if (width != -1) {
                    if (tmp != width) {
                        throw new RuntimeException("Map width is not consistent");
                    }
                }
                width = tmp;
            }
            if (height == 0) {
                throw new RuntimeException("Can't read empty file");
            }
            measuresReader.close();

            // Get map content
            Scanner myReader = new Scanner(myObj);

            read = new WorldEntity[height][width];
            String line;
            for (int y = 0; myReader.hasNextLine(); y++) {
                line = myReader.nextLine();
                for (int x = 0; x < line.length(); x++) {
                    Optional<WorldEntity> readInFileToWorldEntity = WorldEntity.fromCode(line.charAt(x));
                    if (readInFileToWorldEntity.isPresent()) {
                        read[y][x] = readInFileToWorldEntity.get();
                    } else {
                        throw new RuntimeException(String.format("Character %c is invalid\n", line.charAt(x)));
                    }
                }

            }
            myReader.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(String.format("File %s not found, its path: %s\n", filename, worldPath + '/' + filename));
        }
        return new World(read);
    }

}

