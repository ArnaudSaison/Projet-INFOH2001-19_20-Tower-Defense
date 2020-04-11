package towerdefense.game.map;

public class Tile {
    private Position tileMetricPosition; // stocke la position du coin supérieur gauche de la case


    //***** Contructeur *****
    public Tile(){
        tileMetricPosition = new Position();
    }

    //***** Getters et setters *****
    public Position getTileMetricPosition(){
        return tileMetricPosition;
    }

    public void setTileMetricPosition(Position position){
        this.tileMetricPosition = position;
    }


}
