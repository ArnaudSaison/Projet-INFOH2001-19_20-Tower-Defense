package towerdefense.game.map;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tile {
    private Position tileMetricPosition; // stocke la position du coin supérieur gauche de la case
    private Rectangle tileShape;
    private Map map;

    private boolean isBlocked;

    //***** Contructeur *****
    public Tile(int x, int y, double tileMetricWidth){
        tileMetricPosition = new Position(x, y, tileMetricWidth);
        tileShape = new Rectangle();
        tileShape.getStyleClass().addAll("tile");
    }

    public void attachMap(Map map){
        this.map = map;
        tileMetricPosition.attachMap(map);
    }

    //***** Getters et setters *****
    public Position getTileMetricPosition(){
        return tileMetricPosition;
    }

    public void setTileMetricPosition(Position position){
        this.tileMetricPosition = position;
    }

    // Forme javafx associée
    public Node getTileShape(){
        return tileShape;
    }

    // attributs associés à la case
    public boolean getBlockedState(){
        return isBlocked;
    }

    public void setBlockedState(boolean blocked){
        this.isBlocked = blocked;
    }

    //***** Représentation de la case par un élément javafx *****
    public void update(){
        double correctionAdd = 0;

        tileShape.setX(tileMetricPosition.getPixelX());
        tileShape.setY(tileMetricPosition.getPixelY());
        tileShape.setHeight(map.getTileMetricWidth() * map.getPixelsPerMeter() + correctionAdd);
        tileShape.setWidth(map.getTileMetricWidth() * map.getPixelsPerMeter() + correctionAdd);
    }
}
