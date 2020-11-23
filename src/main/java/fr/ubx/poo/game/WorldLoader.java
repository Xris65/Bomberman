package fr.ubx.poo.game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Optional;
import java.util.Scanner;

public class WorldLoader {
    public World readFromFile(String filename){
        String path = getClass().getResource("/sample/" + filename).toExternalForm().split(":")[1];
        WorldEntity[][] read = null;
        try {
            File myObj = new File(path);
            System.out.println("File at: " + myObj);
            System.out.println("Exists: " + myObj.exists() + " readable: " + myObj.canRead());
            Scanner myReader = new Scanner(myObj);
            Scanner measuresReader = new Scanner(myObj);

            // Get map height and width
            int height = 0;
            int width = -1;
            for(; measuresReader.hasNextLine(); height++){
                var tmp = measuresReader.nextLine().length();
                if(width != -1){
                    if(tmp != width){
                        throw new RuntimeException("Map width is not consistent");
                    }
                }
                width = tmp;
            }
            if(height == 0){
                throw new RuntimeException("Can't read empty file");
            }
            measuresReader.close();

            System.out.println("Map height :" + height);
            System.out.println("Map width : " + width);

            read = new WorldEntity[width][height];
            for(int y = 0; myReader.hasNextLine(); y++){
                String line = myReader.nextLine();
                for(int x = 0; x < line.length(); x++){
                    Optional<WorldEntity> readInFileToWorldEntity = WorldEntity.fromCode(line.charAt(x));
                    if(readInFileToWorldEntity.isPresent()){
                        read[x][y] = readInFileToWorldEntity.get();
                    } else {
                        throw new RuntimeException(String.format("Character %c is invalid\n", line.charAt(x)));
                    }
                }

            }
            myReader.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(String.format("File %s not found, its path: %s\n", filename, path));
        }
        return new World(read);
    }
}
