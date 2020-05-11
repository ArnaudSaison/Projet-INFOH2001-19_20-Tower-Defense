package towerdefense.game.map;

import towerdefense.view.map.ObstacleTileView;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

public class MapFactory {
    public enum TileType {TREE, ROCK, PATH, EXIT_PATH, GATE_PATH, EMPTY}

    private int defaultPixelsPerMeter = 20;
    private int defaultTileMetricWidth = 2;

    public Map getMap(String mapFilePath) throws IOException {
        // Lecture des propriétés de la carte
        final Properties mapProperties = new Properties();
        InputStream mapPropertiesFile = new FileInputStream(mapFilePath + "/map.properties");
        mapProperties.load(mapPropertiesFile);

        double tileMetricWidth = Double.parseDouble(mapProperties.getProperty("tileMetricWidth"));
        double pixelsPerMeter = Integer.parseInt(mapProperties.getProperty("pixelsPerMeter"));

        // Nécessaire à la lecture du fichier représentant les cases de la carte
        BufferedReader mapFile = new BufferedReader(new FileReader(mapFilePath + "/map.txt"));

        ArrayList<String> lines = new ArrayList<>();
        String line;
        while ((line = mapFile.readLine()) != null) {
            lines.add(line);
        }
        mapFile.close(); // fermeture du fichier

        return new Map(lines, pixelsPerMeter, tileMetricWidth);
    }

    public void saveMap(Map map, ArrayList<String> waves, String path) {
        // Générer la chaîne de caractères pour chaque ligne et l'ajouter à un output

    }

    /**
     * Crée une carte vide (que des EmptyTile)
     */
    public Map newMap(int columns, int rows) {
        ArrayList<String> resLines = new ArrayList<>();
        for (int i = 1; i <= rows; i++) {
            StringBuilder line = new StringBuilder();
            for (int j = 1; j <= columns; j++) {
                line.append("X");
            }
            resLines.add(line.toString());
        }

        return new Map(resLines, defaultPixelsPerMeter, defaultTileMetricWidth);
    }

    // ==================== Méthodes pour récupérer des informations sur les cases ====================
    public static ArrayList<TileType> getTileTypesList() {
        return new ArrayList<>(Arrays.asList(TileType.values()));
    }

    public static String getTileGraphics(TileType type) {
        String res = "";
        switch (type) {
            case EMPTY:
                res = "tiles/grass.png";
                break;
            case ROCK:
                res = "obstacles/rock.png";
                break;
            case TREE:
                res = "obstacles/tree.png";
                break;
            case EXIT_PATH:
            case GATE_PATH:
            case PATH:
                res = "tiles/grass —.png";
                break;
        }
        return res;
    }

    public static String getTileDescription(TileType type) {
        String res = "";
        switch (type) {
            case ROCK:
                res = "NPCs can't walk on tiles with rocks and the player can't place anything on it.";
                break;
            case TREE:
                res = "NPCs can't walk on tiles with trees and the player can't place anything on it.";
                break;
            case EXIT_PATH:
                res = "This tile allows NPCs to exit the map.";
                break;
            case GATE_PATH:
                res = "This tile allows NPCs to enter the map.";
                break;
            case PATH:
                res = "This tile allows NPCs to walk on the map.";
                break;
            case EMPTY:
                res = "This tile allows the player to place towers and goldmines, but NPCs can't walk on it.";
                break;
        }
        return res;
    }

    public static String getTileName(TileType type) {
        String res = "";
        switch (type) {
            case ROCK:
                res = "Rock Tile";
                break;
            case TREE:
                res = "Tree Tile";
                break;
            case EXIT_PATH:
                res = "Exit Path Tile";
                break;
            case GATE_PATH:
                res = "Gate Path Tile";
                break;
            case PATH:
                res = "Path Tile";
                break;
            case EMPTY:
                res = "Empty Tile";
                break;
        }
        return res;
    }

    public static Tile getTile(TileType type, int x, int y, Map map) {
        Tile res;
        switch (type) {
            case ROCK:
                res = new ObstacleTile(x, y, map, ObstacleTileView.ObstacleType.ROCK);
                break;
            case TREE:
                res = new ObstacleTile(x, y, map, ObstacleTileView.ObstacleType.TREE);
                break;
            case EXIT_PATH:
                res = new ExitPathTile(x, y, map);
                break;
            case GATE_PATH:
                res = new GatePathTile(x, y, map);
                break;
            case PATH:
                res = new PathTile(x, y, map);
                break;
            default:
            case EMPTY:
                res = new EmptyTile(x, y, map);
                break;
        }
        return res;
    }
}
