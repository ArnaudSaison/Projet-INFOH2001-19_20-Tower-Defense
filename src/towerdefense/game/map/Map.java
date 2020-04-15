package towerdefense.game.map;

import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;

public class Map extends Pane {
    private int pixelsPerMeter;
    private double tileMetricWidth;

    private String mapName;
    private int mapTileSizeX;
    private int mapTileSizeY;

    private double originX;
    private double originY;

    private ArrayList<Tile> tiles;
    private ArrayList<PathTile> gates;

    //***** Constructeur *****
    public Map(ArrayList<Tile> tiles, int pixelsPerMeter, double tileMetricWidth, int mapTileSizeX, int mapTileSizeY, String mapName){
        super();

        // Initialisation de tous les attributs
        this.tiles = tiles;
        this.pixelsPerMeter = pixelsPerMeter;
        this.tileMetricWidth = tileMetricWidth;
        this.mapTileSizeX = mapTileSizeX;
        this.mapTileSizeY = mapTileSizeY;
        this.mapName = mapName;

        //System.out.println(tileMetricWidth);

        for (Tile t: tiles){
            // Initialisation de la forme
            t.attachMap(this);
            t.update();

            // On ajoute la forme
            this.getChildren().add(t.getTileShape());
        }
    }

    //***** Getters et Setters *****
    // Récupérer la taille de la carte
    public int getMapTileSizeX() {
        return mapTileSizeX;
    }

    public int getMapTileSizeY() {
        return mapTileSizeY;
    }

    // Récupérer les cases
    public ArrayList<Tile> getTiles(){
        return tiles;
    }

    public Tile getTile(int x, int y){
        // calcul permettant de retrouver une case dans l'index à partir de ses coordonnées
        return tiles.get((y-1) * mapTileSizeX + (x-1));
    }

    // Récupérer les infos sur la carte
    public String getMapName(){
        return mapName;
    }

    //***** Niveau de zoom de la carte et échelle *****
    public int getPixelsPerMeter() {
        return pixelsPerMeter;
    }

    public void setPixelsPerMeter(int pixelsPerMeter){
        this.pixelsPerMeter = pixelsPerMeter;
    }

    public double getTileMetricWidth(){
        return  tileMetricWidth;
    }

    public void setTileMetricWidth(double width){
        tileMetricWidth = width;
    }

    public void updateZoomLevel(double zoom){
        pixelsPerMeter += zoom * 0.2;
        for (Tile t: tiles){
            t.update();
        }
    }
}
