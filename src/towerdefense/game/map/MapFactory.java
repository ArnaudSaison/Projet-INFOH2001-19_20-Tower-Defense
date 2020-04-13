package towerdefense.game.map;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MapFactory {
    private Map getMap(String mapFilePath) throws IOException {
        // Lecture des propriétés de la carte
        Properties settings = new Properties();
        try {

        } catch (IOException) {
            ex.printStackTrace();
        }

        // éléments qui constitueront la carte
        ArrayList<Tile> tiles = new ArrayList();

        // Nécessaire à la lecture du fichier
        BufferedReader mapFile = new BufferedReader(new FileReader(mapFilePath + "/map.txt"));
        String line;

        double sum = 0;
        while ((line = mapFile.readLine()) != null) {
            for (char c: line.toCharArray()){

            }
        }
        mapFile.close();

        double pixelsPerMeter;
        int mapTileSizeX;
        int mapTileSizeY;

        Map map = new Map(tiles, pixelsPerMeter, mapTileSizeX, mapTileSizeY);
        return map;
    }

    private void saveMap(Map map){

    }
}
