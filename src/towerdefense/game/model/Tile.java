package towerdefense.game.model;

public class Tile {
    private int tileX;
    private int tileY;
    //private int size; Plutôt dans le module View ?

    public Tile (int tileX, int tileY){
        this.tileX = tileX;
        this.tileY = tileY;
    }

    public double getTileX(){
        return tileX;
    }

    public double getTileY(){
        return tileY;
    }

    public void setTileX(int tileX){
        this.tileX = tileX;
    }

    public void setTileY(int tileY){
        this.tileY = tileY;
    }
    }