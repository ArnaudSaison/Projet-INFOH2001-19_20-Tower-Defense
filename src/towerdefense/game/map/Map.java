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
    private ArrayList<Tile> gates;
    private ArrayList<Path> availablePaths;

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

        gates = new ArrayList<>();
        availablePaths = new ArrayList<>();

        for (Tile t: tiles){
            // Initialisation de la forme
            t.attachMap(this);
            t.update();

            // création de liste des entrées de la carte
            if (t instanceof GatePathTile){
                gates.add(t);
            }

            // On ajoute la forme
            this.getChildren().add(t.getTileShape());
        }

        // Calcul des chemins valides
        PathFactory pathFactory = new PathFactory(this);
        for (Tile gate: gates){
            ArrayList<Path> computedPaths = pathFactory.getAllPaths(gate);
            availablePaths.addAll(computedPaths);
        }

        // test
        for (Path path: availablePaths){
            System.out.println(path);
        }

        // Stylesheet
        this.getStyleClass().add("map");
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

    public ArrayList<Tile> getGates(){
        return gates;
    }

    public Tile getTile(int x, int y){
        Tile res = null;
        if (x >= 0 && x < mapTileSizeX && y >= 0 && y < mapTileSizeY){
            res = tiles.get((y) * mapTileSizeX + (x));
        }
        return res;
    }

    public Tile getTile(Position pos){
        return getTile((int) pos.getX(), (int) pos.getY());
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

    public void updateZoomLevel(ScrollEvent event) {
        double zoomFact = 0.2; // facteur multiplicatif du déplacement de la molette de la souris
        double zoomExp = 2; // facteur qui détermine combien le zoom est exponentiel
        double minPixelsPerMeter = 1;
        double maxPixelsPerMeter = 300;

        // zoom mesuré par le ScrollEvent
        double zoom = event.getDeltaY();
        // double deltaPPM = zoom * zoomFact;
        double deltaPPM = zoom * Math.pow(zoomFact, settingsPixelsPerMeter/pixelsPerMeter * 2);
        double oldPPM = pixelsPerMeter;

        // Vérification de la validité du zoom et la cas échéant, mise à la valeur par défaut
        if (pixelsPerMeter + deltaPPM < minPixelsPerMeter) {
            deltaPPM = minPixelsPerMeter - pixelsPerMeter;
        } else if (pixelsPerMeter + deltaPPM  > maxPixelsPerMeter){
            deltaPPM = maxPixelsPerMeter - pixelsPerMeter;
        }

        // Calcul de la nouvelle échelle
        pixelsPerMeter += deltaPPM;

        // origine
        double x0 = this.getLayoutX();
        double y0 = this.getLayoutY();

        // position de la souris par rapport à l'origine
        Position pos1 = new Position(event.getX() - x0, event.getY() - y0, this);
        // Changement de position après le zoom
        Position deltaPos = pos1.getMultiplied((-1) * deltaPPM / oldPPM);

        // Mise à jour de toutes les Tiles
        for (Tile t: tiles){
            t.update();
        }

        // Translation de Map
        this.setLayoutX(this.getLayoutX() + deltaPos.getX());
        this.setLayoutY(this.getLayoutY() + deltaPos.getY());
    }
}
