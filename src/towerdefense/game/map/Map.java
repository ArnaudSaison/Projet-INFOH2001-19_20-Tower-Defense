package towerdefense.game.map;

import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Map extends Pane {
    private double pixelsPerMeter;
    private double settingsPixelsPerMeter;
    private double tileMetricWidth;

    private String mapName;
    private int mapTileSizeX;
    private int mapTileSizeY;

    private ArrayList<Tile> tiles;
    private ArrayList<PathTile> gates;

    //***** Constructeur *****
    public Map(ArrayList<Tile> tiles, double pixelsPerMeter, double tileMetricWidth, int mapTileSizeX, int mapTileSizeY, String mapName){
        super();

        // Initialisation de tous les attributs
        this.tiles = tiles;
        this.pixelsPerMeter = pixelsPerMeter;
        this.settingsPixelsPerMeter = pixelsPerMeter;
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
    public double getPixelsPerMeter() {
        return pixelsPerMeter;
    }

    public void setPixelsPerMeter(int pixelsPerMeter){
        this.pixelsPerMeter = pixelsPerMeter;
    }

    public void resetPixelsPerMeter(){
        this.pixelsPerMeter = settingsPixelsPerMeter;
        for (Tile t: tiles){
            t.update();
        }
    }

    public double getTileMetricWidth(){
        return  tileMetricWidth;
    }

    public void setTileMetricWidth(double width){
        tileMetricWidth = width;
    }

    public void updateZoomLevel(ScrollEvent event){
        double zoomFact = 0.2;

        // zoom mesuré par le ScrollEvent
        double zoom = event.getDeltaY();
        double deltaPPM = zoom * zoomFact;
        double oldPPM = pixelsPerMeter;

        // Modification du niveau de zoom
        pixelsPerMeter += deltaPPM; // 0.2 correspond à la vitesse de zoom
        if (pixelsPerMeter < 1){
            pixelsPerMeter = 1;
        }

        // origine
        double x0 = this.getLayoutX();
        double y0 = this.getLayoutY();

        // distance originale entre l'origne de map et la souris
        double distanceX = event.getX() - x0;
        double distanceY = event.getY() - y0;
        System.out.println("\np0: " + x0 + " " + y0);
        System.out.println("event: " + event.getX() + " " + event.getY());
        System.out.println("distance: " + distanceX + " " + distanceY);

        // position de la souris par rapport à l'origine
        Position pos1 = new Position(distanceX, distanceY, this);
        Position deltaPos = pos1.getMultiplied((-1) * deltaPPM / oldPPM);
        System.out.println(deltaPos);

        // Mise à jour de toutes les Tiles
        for (Tile t: tiles){
            t.update();
        }

        // Translation de Map
        this.setLayoutX(this.getLayoutX() + deltaPos.getX());
        this.setLayoutY(this.getLayoutY() + deltaPos.getY());

//        if (this.getScaleX() + zoom * 0.01 > 0){
//            this.setScaleX(this.getScaleX() + zoom * 0.01);
//            this.setScaleY(this.getScaleY() + zoom * 0.01);
//        }
//        for (Tile t: tiles){
//            t.update();
//        }
    }
}
