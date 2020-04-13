package towerdefense.game.map;

import java.util.ArrayList;

public class Map {
    private String mapName;
    private static double pixelsPerMeter;
    private int mapTileSizeX;
    private int mapTileSizeY;

    private ArrayList<Tile> tiles;

    //***** Constructeur *****
    public Map(ArrayList<Tile> tiles, double pixelsPerMeter, int mapTileSizeX, int mapTileSizeY){
        this.tiles = tiles;
        this.pixelsPerMeter = pixelsPerMeter;
        this.mapTileSizeX = mapTileSizeX;
        this.mapTileSizeY = mapTileSizeY;
        this.mapName = mapName;
    }

    //***** Getters et Setters *****
    // Récupérer la taille de la carte
    public int getMapTileSizeX() {
        return mapTileSizeX;
    }

    public int getMapTileSizeY() {
        return mapTileSizeY;
    }

    //***** Niveau de zoom de la carte *****
    public static double getPixelsPerMeter() {
        return pixelsPerMeter;
    }

    public static void updateZoomLevel(double pixelsPerMeter){
        Map.pixelsPerMeter = pixelsPerMeter;
    }
}
