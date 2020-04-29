package towerdefense.game.map;

import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import towerdefense.game.interfaces.Drawable;

import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Map {
    private Pane mapPane;

    private double pixelsPerMeter;
    private double settingsPixelsPerMeter;
    private double tileMetricWidth;

    private String mapName;
    private int mapTileSizeX;
    private int mapTileSizeY;

    private ArrayList<Tile> tiles;
    private ArrayList<ObstacleTile> obstacles;
    private ArrayList<Tile> gates;
    private ArrayList<Path> availablePaths;

    // Eléments qui se trouvent sur la carte
    private ArrayList<Drawable> elementsOnMap;
//    private double mapZoom = 1;

    // ==================== Constructeur ====================
    public Map(ArrayList<Tile> tiles, double pixelsPerMeter, double tileMetricWidth, int mapTileSizeX, int mapTileSizeY, String mapName) {
        // Initialisation de tous les attributs
        this.tiles = tiles;
        this.pixelsPerMeter = pixelsPerMeter;
        this.settingsPixelsPerMeter = pixelsPerMeter;
        this.tileMetricWidth = tileMetricWidth;
        this.mapTileSizeX = mapTileSizeX;
        this.mapTileSizeY = mapTileSizeY;
        this.mapName = mapName;

        elementsOnMap = new ArrayList<>();

        gates = new ArrayList<>();
        availablePaths = new ArrayList<>();

        obstacles = new ArrayList<>();

        mapPane = new Pane();

        for (Tile t : tiles) {
            // Initialisation de la forme
            t.attachMap(this);
            t.updateDrawing();

            // création de liste des entrées de la carte
            if (t instanceof GatePathTile) {
                gates.add(t);
            } else if (t instanceof ObstacleTile) {
                obstacles.add((ObstacleTile)t);
            }

            // On ajoute la forme
            mapPane.getChildren().add(t.getTileShapeContainer());
        }

        // Calcul des chemins valides
        PathFactory pathFactory = new PathFactory(this);
        for (Tile gate : gates) {
            ArrayList<Path> computedPaths = pathFactory.getAllPaths(gate);
            availablePaths.addAll(computedPaths);
        }

        for (ObstacleTile obstacle : obstacles) {
            obstacle.initObstacle();
        }

        // test
        for (Path path : availablePaths) {
            System.out.println(path);
        }

        // Stylesheet
        mapPane.getStyleClass().add("map");
    }

    // ==================== Getters et Setters ====================
    // Récupérer la taille de la carte
    public int getMapTileSizeX() {
        return mapTileSizeX;
    }

    public int getMapTileSizeY() {
        return mapTileSizeY;
    }

    // Récupérer les cases
    public ArrayList<Tile> getTiles() {
        return tiles;
    }

    public ArrayList<Tile> getGates() {
        return gates;
    }

    public Tile getTile(int x, int y) {
        Tile res = null;
        if (x >= 0 && x < mapTileSizeX && y >= 0 && y < mapTileSizeY) {
            res = tiles.get((y) * mapTileSizeX + (x));
        }
        return res;
    }

    public Tile getTile(Position pos) {
        return getTile((int) pos.getX(), (int) pos.getY());
    }

    // Récupérer les infos sur la carte
    public String getMapName() {
        return mapName;
    }

    // Niveau de zoom de la carte et échelle
    public double getPixelsPerMeter() {
        return pixelsPerMeter;
    }

    public void setPixelsPerMeter(int pixelsPerMeter) {
        this.pixelsPerMeter = pixelsPerMeter;
    }

    public void resetPixelsPerMeter() {
        this.pixelsPerMeter = settingsPixelsPerMeter;
        updateTiles();
    }

    public double getTileMetricWidth() {
        return tileMetricWidth;
    }

    public void setTileMetricWidth(double width) {
        tileMetricWidth = width;
    }

    // ==================== JavaFX ====================

    public void updateZoomLevel(ScrollEvent event) {
        double zoomFact = 0.2; // facteur multiplicatif du déplacement de la molette de la souris
        double zoomExp = 2; // facteur qui détermine combien le zoom est exponentiel
        double minPixelsPerMeter = 1;
        double maxPixelsPerMeter = 300;

        // zoom mesuré par le ScrollEvent
        double zoom = event.getDeltaY();
        // double deltaPPM = zoom * zoomFact;
        double deltaPPM = zoom * Math.pow(zoomFact, settingsPixelsPerMeter / pixelsPerMeter * 2);
        double oldPPM = pixelsPerMeter;

        // Vérification de la validité du zoom et la cas échéant, mise à la valeur par défaut
        if (pixelsPerMeter + deltaPPM < minPixelsPerMeter) {
            deltaPPM = minPixelsPerMeter - pixelsPerMeter;
        } else if (pixelsPerMeter + deltaPPM > maxPixelsPerMeter) {
            deltaPPM = maxPixelsPerMeter - pixelsPerMeter;
        }

        // Calcul de la nouvelle échelle
        pixelsPerMeter += deltaPPM;

        // origine
        double x0 = mapPane.getLayoutX();
        double y0 = mapPane.getLayoutY();

        // position de la souris par rapport à l'origine
        Position pos1 = new Position(event.getX() - x0, event.getY() - y0, this);
        // Changement de position après le zoom
        Position deltaPos = pos1.getMultiplied((-1) * deltaPPM / oldPPM);

        // Mise à jour des tiles
        updateTiles();

        // Translation de Map
        mapPane.setLayoutX(mapPane.getLayoutX() + deltaPos.getX());
        mapPane.setLayoutY(mapPane.getLayoutY() + deltaPos.getY());

//        double zoom = event.getDeltaY();
//        double zoomFact = 1.1;
//        if (zoom < 0) {
//            zoomFact = 0.9;
//        }
//        mapZoom *= zoomFact;
//
//        mapPane.setScaleX(mapZoom);
//        mapPane.setScaleY(mapZoom);
    }

    // Récupérer l'objet javafx qui définit la vue de la carte
    public Pane getDrawing() {
        return mapPane;
    }

    public void updateTiles() {
        // Mise à jour de toutes les Tiles
        for (Tile t : tiles) {
            t.updateDrawing();
        }
    }

    public void updateDrawings() {
        for (Drawable d : elementsOnMap) {
            d.updateDrawing();
        }
    }

    public void attachDrawable(Drawable drawing) {
        elementsOnMap.add(drawing);
    }
}
