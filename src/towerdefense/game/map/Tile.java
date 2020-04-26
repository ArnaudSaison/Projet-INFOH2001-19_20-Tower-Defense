package towerdefense.game.map;

import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import towerdefense.gui.listeners.TileClickedListener;

import java.util.ArrayList;

public class Tile {
    // /!\ La position de la case est stockée sous forme de mètres et donne la position de son coin supérieur gauche
    private Position tilePosition; // stocke la position du coin supérieur gauche de la case
    private Rectangle tileShape;
    private Map map;

    private boolean[] connect;
    public ImageView angle;
    public ImageView straight;
    public ImageView TConnector;
    public ImageView XConnector;

    protected boolean isBlocked;

    //***** Contructeur *****
    public Tile(int x, int y, double tileMetricWidth) {
        tilePosition = new Position(x, y, tileMetricWidth);
        tileShape = new Rectangle();
        tileShape.getStyleClass().addAll("tile");
        tileShape.setOnMouseClicked(new TileClickedListener(this));
    }

    public void attachMap(Map map) {
        this.map = map;
        tilePosition.attachMap(map);
    }

    //***** Getters et setters *****
    public Position getPosition() {
        return tilePosition;
    }

    public void setPosition(Position position) {
        this.tilePosition = position;
    }

    public Position getCenterPosition() {
        double dx = map.getTileMetricWidth() / 2;
        double dy = dx;
        return tilePosition.getAdded(new Position(dx, dy, map));
    }

    public void setConnection(boolean[] connect) {
        this.connect = connect;

    }

    // Forme javafx associée
    public Node getTileShape() {
        return tileShape;
    }

    // attributs associés à la case
    public boolean getBlockedState() {
        return isBlocked;
    }

    public void setBlockedState(boolean blocked) {
        this.isBlocked = blocked;
    }

    //***** Représentation de la case par un élément javafx *****
    public void update() {
        double correctionAdd = 0;

        tileShape.setX(tilePosition.getPixelX());
        tileShape.setY(tilePosition.getPixelY());
        tileShape.setHeight(map.getTileMetricWidth() * map.getPixelsPerMeter() + correctionAdd);
        tileShape.setWidth(map.getTileMetricWidth() * map.getPixelsPerMeter() + correctionAdd);
    }

    public void updateConnection(){

    }

    @Override
    public String toString() {
        return "Tile" + tilePosition.getTileCoords();
    }
}
