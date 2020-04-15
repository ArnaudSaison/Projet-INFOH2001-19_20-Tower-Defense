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
        //System.out.println(tileMetricPosition.getMetricPositionX() + " " + tileMetricPosition.getMetricPositionY());
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
    public void setShapeFill(Color color){
        tileShape.setFill(color);
    }

    public Node getTileShape(){
        return tileShape;
    }

    // attributs associés à la case
    public boolean getBlockedState(){
        return isBlocked;
    }

    public void setBlocked(boolean blocked){
        this.isBlocked = blocked;
    }

    //***** Représentation de la case par un élément javafx
    public void update(){
        tileShape.setX(tileMetricPosition.getPixelPositionX());
        tileShape.setY(tileMetricPosition.getPixelPositionY());
        tileShape.setHeight(map.getTileMetricWidth() * map.getPixelsPerMeter());
        tileShape.setWidth(map.getTileMetricWidth() * map.getPixelsPerMeter());
        //System.out.println(tileShape);
        //System.out.println(tileMetricPosition.getPixelPositionX() + " " + tileMetricPosition.getPixelPositionY());
    }
}
