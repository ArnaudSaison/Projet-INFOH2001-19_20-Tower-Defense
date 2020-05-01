package towerdefense.game.map;

import towerdefense.view.ObstacleTileView;

import java.io.*;
import java.util.ArrayList;
import java.util.Properties;

public class MapFactory {
    public Map getMap(String mapFilePath) throws IOException {
        // Lecture des propriétés de la carte
        final Properties mapProperties = new Properties();
        InputStream mapPropertiesFile = new FileInputStream(mapFilePath + "/map.properties");
        mapProperties.load(mapPropertiesFile);

        String mapName = mapProperties.getProperty("mapName");
        double tileMetricWidth = Double.parseDouble(mapProperties.getProperty("tileMetricWidth"));
        double pixelsPerMeter = Integer.parseInt(mapProperties.getProperty("pixelsPerMeter"));

        // éléments qui constitueront la carte
        ArrayList<Tile> tiles = new ArrayList<>();

        // Nécessaire à la lecture du fichier représentant les cases de la carte
        BufferedReader mapFile = new BufferedReader(new FileReader(mapFilePath + "/map.txt"));

        // Lecture de chaque caractère du fichier et création des cases
        String line;
        int rowCounter = 0;
        int columnCounter = 0;
        while ((line = mapFile.readLine()) != null) {
            columnCounter = 0;
            for (char c : line.toCharArray()) {
                switch (c) {
                    case 'X': // vide
                        tiles.add((Tile) new EmptyTile(columnCounter, rowCounter, tileMetricWidth));
                        break;
                    case 'O': // obstacle : type par défaut (arbre)
                        tiles.add((Tile) new ObstacleTile(columnCounter, rowCounter, tileMetricWidth, ObstacleTileView.ObstacleType.TREE));
                        break;
                    case 'T': // obstacle : arbre
                        tiles.add((Tile) new ObstacleTile(columnCounter, rowCounter, tileMetricWidth, ObstacleTileView.ObstacleType.TREE));
                        break;
                    case 'R': // obstacle : rock
                        tiles.add((Tile) new ObstacleTile(columnCounter, rowCounter, tileMetricWidth, ObstacleTileView.ObstacleType.ROCK));
                        break;
                    case 'P': // chemin
                        tiles.add((Tile) new PathTile(columnCounter, rowCounter, tileMetricWidth));
                        break;
                    case 'G': // entrée (gate)
                        tiles.add((Tile) new GatePathTile(columnCounter, rowCounter, tileMetricWidth));
                        break;
                    case 'E': // sortie (exit)
                        tiles.add((Tile) new ExitPathTile(columnCounter, rowCounter, tileMetricWidth));
                        break;
                    default:
                        tiles.add((Tile) new EmptyTile(columnCounter, rowCounter, tileMetricWidth));
                        break;
                }
                columnCounter++;
            }
            rowCounter++;
        }
        mapFile.close(); // fermeture du fichier

        // Création et return de la carte créée
        return new Map(tiles, pixelsPerMeter, tileMetricWidth, columnCounter, rowCounter, mapName);
    }

    private void saveMap(Map map) {
        System.out.println("Not implemented");
        //TODO: implémenter la méthode saveMap pour l'éditeur de map
        /* Pour l'éditeur :
         * - Créer un fichier texte ne coomprtant que des cases vides,
         * - le passer dans le getMap*/
    }
}
